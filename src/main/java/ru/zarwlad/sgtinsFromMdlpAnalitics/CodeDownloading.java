package ru.zarwlad.sgtinsFromMdlpAnalitics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.mdlpDto.HierarchySsccMdlpDto;
import ru.zarwlad.unitedDtos.mdlpDto.SsccDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dao.SgtinDao;
import ru.zarwlad.sgtinsFromMdlpAnalitics.dao.SsccDao;
import ru.zarwlad.unitedDtos.mdlpDto.FilterForRequestDto;
import ru.zarwlad.unitedDtos.mdlpDto.ResponseSgtinFilterPageDto;
import ru.zarwlad.unitedDtos.mdlpDto.SgtinFilterMdlpDto;
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

import static ru.zarwlad.util.client.MdlpClient.getHierarchySsccRequest;
import static ru.zarwlad.util.client.MdlpClient.postSgtinFilterRequest;

public class CodeDownloading {
    static Logger log = LoggerFactory.getLogger(CodeDownloading.class);
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

           /*
        фильтр для аспена
         */


        ArrayList<FilterForRequestDto> forRequestDtos = new ArrayList<>();

        List<String> batches = new ArrayList<>();
        Collections.addAll(batches, "8111A", "8122",
                "8120", "8107A", "8116", "8119", "8122А", "8128А",
                "5324A", "8115", "9367", "8089A", "8101C");


        for (String batch : batches) {
            FilterForRequestDto filter = new FilterForRequestDto();
            filter.setBatch(batch);
            forRequestDtos.add(filter);
        }

        for (FilterForRequestDto forRequestDto : forRequestDtos) {
            log.info("Обработка " + forRequestDto.getBatch());

            getAndSaveSgtinsAndSsccByFilter(forRequestDto);
        }
        /*
        фильтр для кьези
         */
//        List<String> statuses = new ArrayList<>();
//        statuses.add("released_foreign");
//        statuses.add("arrived");
//        statuses.add("change_owner");
//        filterMdlpDto.setStatus(statuses);
//
//        List<String> emissionTypes = new ArrayList<>();
//        emissionTypes.add("3");
//        filterMdlpDto.setEmissionTypes(emissionTypes);


    }

    public static void getAndSaveSgtinsAndSsccByFilter(FilterForRequestDto filterForRequestDto){
        Integer startFrom = 0;
        Integer totalRecordsDownloaded = 0;
        if (filterForRequestDto.getBatch().equals("8111A")){
            startFrom = 20300;
            totalRecordsDownloaded = 20300;
        }

        Integer count = 10;

        ResponseSgtinFilterPageDto responseSgtinFilterPageDto =
                postSgtinFilterRequest(startFrom, count, filterForRequestDto);

        getAndSaveCodesFromMdlpResponse(responseSgtinFilterPageDto);
        totalRecordsDownloaded = totalRecordsDownloaded + responseSgtinFilterPageDto.getEntries().size();

        while (totalRecordsDownloaded < responseSgtinFilterPageDto.getTotal()){
            startFrom = startFrom + count;

            responseSgtinFilterPageDto =
                    postSgtinFilterRequest(startFrom, count, filterForRequestDto);
            getAndSaveCodesFromMdlpResponse(responseSgtinFilterPageDto);

            totalRecordsDownloaded = totalRecordsDownloaded + responseSgtinFilterPageDto.getEntries().size();
            log.info("totalRecordsDownloaded = " + totalRecordsDownloaded);
        }
    }

    public static void getAndSaveCodesFromMdlpResponse(ResponseSgtinFilterPageDto responseSgtinFilterPageDto){
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
            if (parentSsccCode != null) {
                Sscc primParentSscc = null;

                for (Sscc s : parentSscc) {
                    if (s.getSscc().equals(parentSsccCode.getSscc())) {
                        primParentSscc = s;
                    }
                }
                caseSsccCode.setParentSscc(primParentSscc);
            }
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

        log.info("Всего кодов SGTIN скачено " + responseSgtinFilterPageDto.getEntries().size());
        log.info("Всего кодов SGTIN " + sgtins.size());

        SgtinDao sgtinDao = new SgtinDao();
        SsccDao ssccDao = new SsccDao();

        for (Sscc sscc : parentSscc) {
            Sscc ssccFromDb = ssccDao.create(sessionFactory, sscc);
            log.info("Сохраняю родителя " + sscc.getSscc());

            sscc.setId(ssccFromDb.getId());
        }

        for (Sscc sscc : casesSscc) {

            if (sscc.getParentSscc() != null){
                for (Sscc sscc1 : parentSscc) {
                    if (sscc1.getSscc().equals(sscc.getParentSscc()))
                        sscc.getParentSscc().setId(sscc1.getId());
                }
            }

            Sscc ssccFromDb = ssccDao.create(sessionFactory, sscc);
            log.info("Сохраняю " + sscc.getSscc());

            sscc.setId(ssccFromDb.getId());
        }

        for (Sgtin sgtin : sgtins) {
            log.info("Сохраняю " + sgtin.getSgtin());
            sgtin = sgtinDao.create(sessionFactory, sgtin);
        }
    }
}
