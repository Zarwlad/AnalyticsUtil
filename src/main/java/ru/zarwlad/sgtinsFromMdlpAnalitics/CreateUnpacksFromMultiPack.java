package ru.zarwlad.sgtinsFromMdlpAnalitics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.EventLinePostDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.UnitUnpackEventDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.UnitUnpackEventPostDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.BusinessPartnerBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LegalEntityBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.LocationBriefDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.util.client.MdlpClient;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CreateUnpacksFromMultiPack {
    static Logger log = LoggerFactory.getLogger(CreateUnpacksFromMultiPack.class);
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");


    public static void main(String[] args) throws IOException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<EventLineDto> eventLineDtos = getAllEventLinesFromEvent();

        List<UnitUnpackEventDto> unitUnpackEventDtos = new ArrayList<>();

        UUID groupId = UUID.fromString("8ba82523-ca92-4444-b8f9-08033fe52878");
        Integer priority = 12;

        LegalEntityBriefDto legalEntityBriefDto = new LegalEntityBriefDto();
        BusinessPartnerBriefDto businessPartnerBriefDto = new BusinessPartnerBriefDto();

        businessPartnerBriefDto.setId("5f907cac-1c5a-4302-a9a3-ae239d23e482");// de8ff270-1196-4870-8181-32269695cf6d
        businessPartnerBriefDto.setName("Кьези Фармацевтичи S.p.A., Италия");
        legalEntityBriefDto.setBusinessPartnerBriefDto(businessPartnerBriefDto);
        legalEntityBriefDto.setId(businessPartnerBriefDto.getId());

        LocationBriefDto locationBriefDto = new LocationBriefDto();
        locationBriefDto.setId("f688c402-0f1e-4b01-880e-d4ce07012bd1"); // ae775f11-69f3-454e-891a-5e6b8f502a9b
        locationBriefDto.setName("Chiesi Farmaceutici S.p.A. Italy");

        List<String> codes = new ArrayList<>();
        codes.add("580251530111298410");
        codes.add("580251530111298427");
        codes.add("580251530111298434");
        codes.add("580251530111298441");
        codes.add("580251530111298458");
        codes.add("580251530111298465");
        codes.add("580251530111298472");
        codes.add("580251530111298489");
        codes.add("580251530111298496");
        codes.add("580251530111298502");
        codes.add("580251530111298564");
        codes.add("580251530111298571");

        for (EventLineDto eventLineDto : eventLineDtos) {
            if (codes.contains(eventLineDto.getCode())){
                continue;
            }

            UnitUnpackEventPostDto unitUnpackEventPostDto = new UnitUnpackEventPostDto(
                    null,
                    null,
                    false,
                    null,
                    groupId.toString(),
                    false,
                    true,
                    true,
                    legalEntityBriefDto,
                    locationBriefDto,
                    "2020-06-18T05:26:39Z",
                    priority,
                    false,
                    "NOT_REQUIRED",
                    false,
                    false
            );

            List<EventLinePostDto> eventLinePostDtos = new ArrayList<>();
            eventLinePostDtos.add(new EventLinePostDto(
                    eventLineDto.getCode(),
                    eventLineDto.isSscc(),
                    null
            ));

            UnitUnpackEventDto unitUnpackEventDto = UtraceClient.postUnitUnpackEvent(unitUnpackEventPostDto);

            UtraceClient.postUnitUnpackEventLine(eventLinePostDtos, unitUnpackEventDto);
            UtraceClient.putUnitUnpackCreatedStatus(unitUnpackEventDto);

            log.info("Успешно сохранен" + unitUnpackEventDto.toString());
            unitUnpackEventDtos.add(unitUnpackEventDto);
            priority ++;
        }

        log.info("groupId: " + groupId);
        for (UnitUnpackEventDto unitUnpackEventDto : unitUnpackEventDtos) {
            log.info("id: " + unitUnpackEventDto.getId());
        }
    }

    static List<EventLineDto> getAllEventLinesFromEvent () throws IOException {
        PageDtoOfEventLineDto pageDtoOfEventLineDto = UtraceClient.getPageEventLinesFromEvent("0e2626c9-912b-4ff2-a0e2-b4907348a370", 0);

        return new ArrayList<>(pageDtoOfEventLineDto.getEventLineDtos());
    }
}
