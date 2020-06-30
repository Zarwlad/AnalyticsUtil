package ru.zarwlad.sgtinsFromMdlpAnalitics;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zarwlad.unitedDtos.analyticsDto.HighLevelCodesCsvDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.BatchSgtinQuantityDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.CodeTreeRootDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageBatchSgtinQuantityDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageCodeDto;
import ru.zarwlad.unitedDtos.utraceDto.pagedDtos.PageDtoOfCodeTreeRootDto;
import ru.zarwlad.util.client.UtraceClient;
import ru.zarwlad.utrace.service.AuthService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AspenCTRDownloading {
    static Logger log = LoggerFactory.getLogger(AspenCTRDownloading.class);
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");


    public static void main(String[] args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        log.info("Аутенфикация");
        try {
            AuthService.Auth();
        } catch (IOException e) {
            log.error(e.toString());
        }

        List<PageDtoOfCodeTreeRootDto> codeTreeRootDtoPages = downloadCTRs();

        List<CodeTreeRootDto> ctrs = new ArrayList<>();

        for (PageDtoOfCodeTreeRootDto codeTreeRootDtoPage : codeTreeRootDtoPages) {
            ctrs.addAll(codeTreeRootDtoPage.getCodeTreeRootDtos());
        }

        List<HighLevelCodesCsvDto> highLevelCodes = new ArrayList<>();

        for (CodeTreeRootDto ctr : ctrs) {
            if (!ctr.getCodeBriefDto().isSscc()) {
                String filter = "id=" + ctr.getCodeBriefDto().getId();
                PageCodeDto pageCodeDto = UtraceClient.getPagedCodesByFilter(filter,0, 1);

                BatchSgtinQuantityDto batchSgtinQuantityDto = new BatchSgtinQuantityDto();
                batchSgtinQuantityDto.setQuantity(1);
                batchSgtinQuantityDto.setBatchBriefDto(pageCodeDto.getCodeDtos().get(0).getBatchBriefDto());

                ArrayList<BatchSgtinQuantityDto> b = new ArrayList<>();
                b.add(batchSgtinQuantityDto);

                HighLevelCodesCsvDto highLevelCode = HighLevelCodesCsvDto.mapperToHighLevelCodeCsv(ctr, b);
                highLevelCodes.add(highLevelCode);
            }
            else {
                PageBatchSgtinQuantityDto pageBatchSgtinQuantityDto = UtraceClient.getPagedBatchesInSscc(
                        ctr.getCodeBriefDto().getSgtinOrSscc(),
                        0,
                        100);
                HighLevelCodesCsvDto highLevelCode = HighLevelCodesCsvDto.mapperToHighLevelCodeCsv(ctr,
                        pageBatchSgtinQuantityDto.getBatchSgtinQuantityDtos());
                highLevelCodes.add(highLevelCode);
            }
        }

        Path csvs = Files.createFile(
                Paths.get("src\\reports\\"
                        + "aspen"
                        + LocalDateTime.now().format(dateTimeFormatter) + ".csv"));

        Writer writer = new FileWriter(csvs.toFile());
        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(';')
                .withApplyQuotesToAll(false)
                .build();
        sbc.write(highLevelCodes);
        writer.close();
    }

    static List<PageDtoOfCodeTreeRootDto> downloadCTRs () {
        PageDtoOfCodeTreeRootDto firstCtr = UtraceClient.getPagedCodeTreeRootsByFilter("NO", 0, 100);

        List<PageDtoOfCodeTreeRootDto> codeTreeRootDtoPages = new ArrayList<>();

        for (int i = 0; i < firstCtr.getPage().getTotalPages(); i++) {
            codeTreeRootDtoPages.add(UtraceClient.getPagedCodeTreeRootsByFilter("NO", i, 100));
        }

        return codeTreeRootDtoPages;
    }
}
