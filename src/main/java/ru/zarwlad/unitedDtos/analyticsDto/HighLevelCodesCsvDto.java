package ru.zarwlad.unitedDtos.analyticsDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import lombok.*;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.BatchSgtinQuantityDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.CodeTreeRootDto;
import ru.zarwlad.unitedDtos.utraceDto.entityDtos.briefs.CodeBriefDto;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class HighLevelCodesCsvDto {

    @CsvRecurse
    CodeBriefDto codeBriefDto;

    @CsvBindByName(column = "codeTreeRootId")
    String id;

    @CsvBindByName
    String acceptStatus;

    @CsvBindByName
    String baseStatus;

    @CsvBindByName
    String regulatorStatus;

    @CsvBindByName
    boolean reserved;

    @CsvBindByName
    String legalEntityName;

    @CsvBindByName
    String legalEntityId;

    @CsvBindByName
    String locationName;

    @CsvBindByName
    String locationId;

    @CsvBindByName
    String ownerName;

    @CsvBindByName
    String ownerId;

    @CsvBindByName
    String receiverName;

    @CsvBindByName
    String receiverId;

    @CsvBindByName
    String receiverLocationName;

    @CsvBindByName
    String receiverLocationId;

    @CsvBindAndSplitByName(elementType = String.class, column = "batchOrLot")
    List<String> batchOrLots;

    @CsvBindByName
    String orderDetails;


    public static HighLevelCodesCsvDto mapperToHighLevelCodeCsv(CodeTreeRootDto ctr,
                                                                List<BatchSgtinQuantityDto> batches){

        List<String> batchStrings = new ArrayList<>();

        for (BatchSgtinQuantityDto batch : batches) {
            batchStrings.add(batch.getBatchBriefDto().getBatchOrLot());
        }

        String xml = ctr.getCodeBriefDto().isSscc()
                ? "<sscc>" + ctr.getCodeBriefDto().getSgtinOrSscc() + "</sscc>" :
                "<sgtin>" + ctr.getCodeBriefDto().getSgtinOrSscc() + "</sgtin>";

        return new HighLevelCodesCsvDto(
                ctr.getCodeBriefDto(),
                ctr.getId(),
                ctr.getAcceptStatus(),
                ctr.getBaseStatus(),
                ctr.getRegulatorStatus(),
                ctr.isReserved(),
                ctr.getLegalEntityBriefDto().getBusinessPartnerBriefDto().getName(),
                ctr.getLegalEntityBriefDto().getBusinessPartnerBriefDto().getId(),
                ctr.getLocationBriefDto().getName(),
                ctr.getLocationBriefDto().getId(),
                ctr.getOwnerBpBriefDto().getName(),
                ctr.getOwnerBpBriefDto().getId(),
                ctr.getReceiverBpBriefDto() == null ? null : ctr.getReceiverBpBriefDto().getName(),
                ctr.getReceiverBpBriefDto() == null ? null : ctr.getReceiverBpBriefDto().getId(),
                ctr.getReceiverLocBriefDto() == null ? null : ctr.getReceiverLocBriefDto().getName(),
                ctr.getReceiverLocBriefDto() == null ? null : ctr.getReceiverLocBriefDto().getId(),
                batchStrings,
                xml
        );
    }
}
