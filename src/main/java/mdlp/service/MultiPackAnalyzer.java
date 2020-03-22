package mdlp.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import mdlp.dto.messages.Documents;
import mdlp.dto.messages.multiPackDto.DetailDTO;
import mdlp.dto.messages.ticketDto.ErrorDTO;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MultiPackAnalyzer {
    public static void main(String[] args) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //TODO: переделать логику с DTO на сущности

        System.out.println("Введи путь к файлу, отправленному в МДЛП");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        String mdlpRequest = consoleReader.readLine();
        Path mdlpReqPath = Paths.get(mdlpRequest);
        Documents mdlpRequestDoc = xmlMapper.readValue(Files.readAllBytes(mdlpReqPath), Documents.class);

        System.out.println("Введи путь к ответу, полученному из МДЛП");
        String mdlpResponse = consoleReader.readLine();
        Path mdlpRespPath = Paths.get(mdlpResponse);
        Documents mdlpRespTicket = xmlMapper.readValue(Files.readAllBytes(mdlpRespPath), Documents.class);

        Set<String> problemSgtins = new HashSet<>();

        for (ErrorDTO errorDTO: mdlpRespTicket.getResultDTO().getErrors()) {
            problemSgtins.add(errorDTO.getObjectId());
        }

        HashMap<String, HashMap<String, Boolean>> problemSscc = getProblemSsccList(mdlpRequestDoc, problemSgtins);

        Report(problemSscc);
    }

    static HashMap<String, HashMap<String, Boolean>> getProblemSsccList (Documents multiPackReq, Set<String> problemSgtins) {
        HashMap<String, HashMap<String, Boolean>> problemSsccListWithSgtins = new HashMap<>();

        for (DetailDTO detailDTO : multiPackReq.getMultiPackDTO().getDetail()) { //проход по списку SSCC
            boolean isSsccProblem = false;
            HashMap<String, Boolean> sgtinsInProblemSscc = new HashMap<>(); //sgtin, isProblem

            for (String s : detailDTO.getSgtin()){ //проход по содержимому SSCC
                if (problemSgtins.contains(s)) {
                    isSsccProblem = true;
                    sgtinsInProblemSscc.put(s, true);
                }
                else {
                    sgtinsInProblemSscc.put(s, false);
                }
            }

            try {
                if (isSsccProblem)
                    problemSsccListWithSgtins.put(detailDTO.getSscc(), sgtinsInProblemSscc);
            }
            catch (NullPointerException e){
                System.out.println("Возникла проблема: detailDTO.getSscc() - " + detailDTO.getSscc());
                for (String s : detailDTO.getSgtin()) {
                    System.out.println(s);
                }
                e.printStackTrace();
            }
        }

        return problemSsccListWithSgtins;
    }

    static void Report (HashMap<String, HashMap<String, Boolean>> problemSscc) throws IOException {
        //TODO: Метод Report должен только выводить на печать, все данные должны быть переведены в классы
        String filename = "operationResult" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss"));
        Path newReport = Paths.get("C:\\Users\\Vladimir\\Desktop\\" + filename + ".txt");
        if (!Files.notExists(newReport))
                Files.createFile(newReport);

        FileWriter fileWriter = new FileWriter(newReport.toFile());

        fileWriter.write("Всего проблемных SSCC: " + problemSscc.size() + "\n");
        fileWriter.flush();

        for (Map.Entry<String, HashMap<String, Boolean>> stringHashMapEntry : problemSscc.entrySet()) { // анализ SSCC

            if (stringHashMapEntry.getValue().containsValue(false)){ //в списке SGTIN содержится хотя бы один непроблемный
                fileWriter.write("Код SSCC: " + stringHashMapEntry.getKey() + " частично обработан в МДЛП" + "\n");
                fileWriter.write("Подробный анализ кода " + stringHashMapEntry.getKey() + ":" + "\n");

                for (Map.Entry<String, Boolean> sgtinList : stringHashMapEntry.getValue().entrySet()) {
                    if (sgtinList.getValue().equals(true))
                        fileWriter.write("\t" + "Код SGTIN " + sgtinList.getKey() + "успешно обработан в МДЛП" + "\n");
                    else
                        fileWriter.write("\t" + "Код SGTIN " + sgtinList.getKey() + "не принят в МДЛП" + "\n");
                }
            }
            else {
                fileWriter.write("Код SSCC: " + stringHashMapEntry.getKey() + " полностью не принят в МДЛП. Список кодов: " + "\n");
                for (Map.Entry<String, Boolean> sgtinList : stringHashMapEntry.getValue().entrySet()) {
                    fileWriter.write("\t" + sgtinList.getKey() + "\n");
                }
            }
            fileWriter.flush();
        }
        fileWriter.close();
    }
}
