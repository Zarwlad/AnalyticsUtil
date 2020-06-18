package ru.zarwlad.sgtinsFromMdlpAnalitics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.HierarchySsccMdlpDto;
import ru.zarwlad.mdlp.downloadMdlpPartners.dto.mdlpDto.SsccDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dao.SgtinDao;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dao.SsccDao;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dto.FilterForRequestDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dto.RequestSgtinFilterDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dto.ResponseSgtinFilterPageDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dto.SgtinFilterMdlpDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sgtin;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sscc;
import ru.zarwlad.sgtinsFromMdlpAnalitics.util.FromDtoToEntitiesMapper;
import ru.zarwlad.sgtinsFromMdlpAnalitics.util.PostgresUtil;
import ru.zarwlad.utrace.service.AuthService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static ru.zarwlad.utrace.service.DownloadEventsService.getMdlpProxyRequestWithAuthGetType;
import static ru.zarwlad.utrace.service.DownloadEventsService.postMdlpProxyRequestWithAuthGetType;

public class ChiesiCodeDownloading {
    static Logger log = LoggerFactory.getLogger(ChiesiCodeDownloading.class);
    static PostgresUtil postgresUtil = PostgresUtil.getInstance();
    static SessionFactory sessionFactory = postgresUtil.getSessionFactory();
    static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.findAndRegisterModules();
    }
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(600_000, TimeUnit.MILLISECONDS)
            .build();

    static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileReader(
                    new File("src\\main\\resources\\application.properties")));
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void main(String[] args) {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        Integer startFrom = 40001;
        Integer count = 100;

        FilterForRequestDto filterMdlpDto = new FilterForRequestDto();
        List<String> statuses = new ArrayList<>();
        statuses.add("released_foreign");
        statuses.add("arrived");
        statuses.add("change_owner");
        filterMdlpDto.setStatus(statuses);

        List<String> emissionTypes = new ArrayList<>();
        emissionTypes.add("3");
        filterMdlpDto.setEmissionTypes(emissionTypes);

        ResponseSgtinFilterPageDto responseSgtinFilterPageDto =
                postSgtinFilterRequest(startFrom, count, filterMdlpDto);

        Set<SsccDto> ssccDtoSet = new HashSet<>();
        for (SgtinFilterMdlpDto entry : responseSgtinFilterPageDto.getEntries()) {
            if (entry.getSsccCase() != null){
                SsccDto ssccDto = new SsccDto();
                ssccDto.setSscc(entry.getSsccCase());

                ssccDtoSet.add(ssccDto);
            }
        }

        Map<SsccDto, HierarchySsccMdlpDto> ssccMdlpDtoHashMap = new HashMap<>();
        for (SsccDto ssccDto : ssccDtoSet) {
            HierarchySsccMdlpDto hierarchySsccMdlpDto = getHierarchySsccRequest(ssccDto);
            ssccMdlpDtoHashMap.put(ssccDto, hierarchySsccMdlpDto);
        }

        Set<Sscc> parentSscc = new HashSet<>(); //pallets
        Set<Sscc> casesSscc = new HashSet<>(); //cases
        Set<Sgtin> sgtins = new HashSet<>();

        for (Map.Entry<SsccDto, HierarchySsccMdlpDto> ssccDtoHierarchySsccMdlpDtoEntry : ssccMdlpDtoHashMap.entrySet()) {
            Sscc parentSsccCode = null;
            //записываем паллеты в набор родителей
            for (SsccDto upSsccDto : ssccDtoHierarchySsccMdlpDtoEntry.getValue().getUp()) {
                if (!upSsccDto.getSscc().equals(ssccDtoHierarchySsccMdlpDtoEntry.getKey().getSscc())){
                    parentSsccCode = FromDtoToEntitiesMapper.fromSsccDtoToSscc(upSsccDto);
                    parentSscc.add(parentSsccCode);
                }
            }

            Sscc caseSsccCode = FromDtoToEntitiesMapper.fromSsccDtoToSscc(
                    ssccDtoHierarchySsccMdlpDtoEntry.getKey());
            if (parentSsccCode != null)
                caseSsccCode.setParentSscc(parentSsccCode);
            casesSscc.add(caseSsccCode);
        }

        for (SgtinFilterMdlpDto entry : responseSgtinFilterPageDto.getEntries()) {
            Sgtin sgtin = FromDtoToEntitiesMapper.fromSgtinFilterDtoToSgtin(entry);
            if (entry.getSsccCase() != null) {
                Sscc ssccForSgtin = null;

                for (Sscc sscc : casesSscc) {
                    if (sscc.getSscc().equals(entry.getSsccCase()))
                        ssccForSgtin = sscc;
                }
                sgtin.setSscc(ssccForSgtin);
            }

            sgtins.add(sgtin);
        }

        System.out.println("Всего кодов SGTIN скачено " + responseSgtinFilterPageDto.getEntries().size());
        System.out.println("Всего кодов SGTIN " + sgtins.size());

        SgtinDao sgtinDao = new SgtinDao();
        SsccDao ssccDao = new SsccDao();

        for (Sscc sscc : parentSscc) {
            Sscc ssccFromDb = ssccDao.create(sessionFactory, sscc);
            sscc.setId(ssccFromDb.getId());

            System.out.println("Сохраняю родителя " + sscc.getSscc());
        }

        for (Sscc sscc : casesSscc) {
            Sscc ssccFromDb = ssccDao.create(sessionFactory, sscc);
            sscc.setId(ssccFromDb.getId());

            System.out.println("Сохраняю " + sscc.getSscc());
        }

        for (Sgtin sgtin : sgtins) {
            sgtin = sgtinDao.create(sessionFactory, sgtin);

            System.out.println("Сохраняю " + sgtin.getSgtin());
        }

    }

    public static ResponseSgtinFilterPageDto postSgtinFilterRequest(Integer startFrom,
                                                                    Integer count,
                                                                    FilterForRequestDto filterForRequestDto){
        RequestSgtinFilterDto requestSgtinFilterDto = new RequestSgtinFilterDto(
                filterForRequestDto,
                count,
                startFrom,
                "NO_SORT"
        );

        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                    objectMapper.writeValueAsString(requestSgtinFilterDto));

            System.out.println(objectMapper.writeValueAsString(requestSgtinFilterDto));

            String urlPath = properties.getProperty("host")
                    + "mdlp/api/reestr/"
                    + properties.getProperty("routeId")
                    + "/sgtin/filter";

            return objectMapper.readValue(
                    postGuaranteedRequestSending(urlPath, requestBody),
                    ResponseSgtinFilterPageDto.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    public static HierarchySsccMdlpDto getHierarchySsccRequest(SsccDto ssccDto){
        String urlPath = properties.getProperty("host")
                + "mdlp/api/sscc/"
                + ssccDto.getSscc()
                + "/hierarchy";

        try {
            HierarchySsccMdlpDto hierarchySsccMdlpDto = objectMapper.readValue(
                    getGuaranteedRequestSending(urlPath),
                    HierarchySsccMdlpDto.class);
            if (hierarchySsccMdlpDto.getUp() != null) {
                return hierarchySsccMdlpDto;
            }
            else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String postGuaranteedRequestSending(String urlPath,
                                                      RequestBody requestBody) throws IOException {
        Request request = postMdlpProxyRequestWithAuthGetType(urlPath, requestBody);

        return guaranteedSending(request);
    }

    public static String getGuaranteedRequestSending(String urlPath) throws IOException {
        Request request = getMdlpProxyRequestWithAuthGetType(urlPath);

        return guaranteedSending(request);
    }

    private static String guaranteedSending(Request request) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String responseBody = null;

        Response response = okHttpClient.newCall(request).execute();
        while (!response.isSuccessful()){
            log.error(request.toString());
            log.error(response.toString());
            log.error(response.body().string());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            response = okHttpClient.newCall(request).execute();
        }
        responseBody = response.body().string();
        log.info(responseBody);
        response.close();

        return responseBody;
    }
}
