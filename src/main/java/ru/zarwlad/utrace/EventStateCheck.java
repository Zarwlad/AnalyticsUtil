package ru.zarwlad.utrace;

import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventLineDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.EventStateDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageCodeDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfEventLineDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageEventStateDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class EventStateCheck {
    private static Logger log = LoggerFactory.getLogger(EventStateCheck.class);

    public static void main(String[] args) throws IOException {

        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }
        readCurrentStateByEventLines();
    }

    private static void readCurrentStateByEventLines() throws IOException{
        PageDtoOfEventLineDto eventLineDto = UtraceClient
                .getPageEventLinesFromEvent("28b4483e-e234-41a1-af57-39cdf79e1b4a", 0);

        List<Sscc> ssccListWithFullHierarchy = new ArrayList<>();
        List<SgtinCurrState> totalSgtins = new ArrayList<>();

        for (EventLineDto lineDto : eventLineDto.getEventLineDtos()) {
            Sscc ssccHighLevel = new Sscc();
            ssccHighLevel.setCode(lineDto.getCode());

            List<String> filterForHieDown = new ArrayList<>();
            filterForHieDown.add("&size=2000");

            PageCodeDto ssccMiddleLevel =
                    UtraceClient.getPageCodeDtoOfHierarchyDown(ssccHighLevel.getCode(), filterForHieDown);

            List<Sscc> middleSscc = new ArrayList<>();
            ssccMiddleLevel.getCodeDtos().forEach(x -> middleSscc.add(
                    new Sscc(x.getSgtinOrSscc(), null, null)));

            ssccHighLevel.setSsccs(middleSscc);

            for (Sscc s : ssccHighLevel.getSsccs()) {
                List<SgtinCurrState> sgtinList = new ArrayList<>();
                PageCodeDto sgPage =
                        UtraceClient.getPageCodeDtoOfHierarchyDown(s.getCode(), filterForHieDown);
                sgPage.getCodeDtos().forEach(x -> {
                    SgtinCurrState sgtinCurrState = new SgtinCurrState(
                            x.getSgtinOrSscc(),
                            s.getCode(),
                            ssccHighLevel.getCode());

                    sgtinList.add(sgtinCurrState);
                    totalSgtins.add(sgtinCurrState);
                });
            }
            ssccListWithFullHierarchy.add(ssccHighLevel);
        }

        Path path = Paths.get("src/reports/sgtinsFromCurrentState.csv");
        if (!Files.exists(path))
            Files.createFile(path);

        totalSgtins
                .stream()
                .sorted(Comparator.comparing(SgtinCurrState::getCode))
                .forEach(x -> {
                    try {
                        Files.writeString(
                                path,
                                String.format("%s;%s;%s\n",
                                        x.getCode(),
                                        x.getMiddleLevelSscc(),
                                        x.getHighLevelSscc()),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                    }
                });


    }

    private static void readFromEventState() throws IOException{
        List<String> firstFilter = new ArrayList<>();
        firstFilter.add("&size=2000");
        firstFilter.add("&sort=code,desc");
        PageEventStateDto pageEventStateDto = UtraceClient.getPagedEventState(
                "28b4483e-e234-41a1-af57-39cdf79e1b4a",
                firstFilter);

        int limit = pageEventStateDto.getPage().getTotalPages();

        List<EventStateDto> dtos = new ArrayList<>();

        List<PageEventStateDto> pageList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            List<String> secFilter = new ArrayList(firstFilter);
            secFilter.add("&page=" + i);
            pageEventStateDto = UtraceClient.getPagedEventState(
                    "28b4483e-e234-41a1-af57-39cdf79e1b4a",
                    secFilter);
            dtos.addAll(pageEventStateDto.getEventStateDtos());
        }

        List<SgtinCurrState> sgtinsFromEventState = new ArrayList<>();
        for (EventStateDto dto : dtos) {
            if (dto.getCode().length() == 27){
                SgtinCurrState sgtin = new SgtinCurrState();
                sgtin.setCode(dto.getCode());
                sgtin.setMiddleLevelSscc(dto.getParentCode());

                String highSscc = null;
                for (EventStateDto stateDto : dtos) {
                    if (stateDto.getCode().equals(sgtin.getMiddleLevelSscc()))
                        highSscc = stateDto.getParentCode();
                }
                if (highSscc != null)
                    sgtin.setHighLevelSscc(highSscc);
                sgtinsFromEventState.add(sgtin);
            }
        }

        Path path = Paths.get("src/reports/sgtinsFromEventState.csv");
        if (!Files.exists(path)){
            Files.createFile(path);
        }
        sgtinsFromEventState
                .stream()
                .sorted(Comparator.comparing(SgtinCurrState::getCode))
                .forEach(x -> {
                    try {
                        Files.writeString(
                                path,
                                String.format("%s;%s;%s\n",
                                        x.getCode(),
                                        x.getMiddleLevelSscc(),
                                        x.getHighLevelSscc()),
                                StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                    }
                });
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "code")
    static class SgtinCurrState{
        private String code;
        private String middleLevelSscc;
        private String highLevelSscc;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(of="code")
    static class Sscc{
        private String code;
        private List<Sscc> ssccs;
        private List<SgtinCurrState> sgtins;
    }

}
