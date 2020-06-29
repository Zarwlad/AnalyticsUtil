package ru.zarwlad.sgtinsFromMdlpAnalitics.util;

import ru.zarwlad.unitedDtos.mdlpDto.SsccDto;
import ru.zarwlad.unitedDtos.mdlpDto.SgtinFilterMdlpDto;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sgtin;
import ru.zarwlad.sgtinsFromMdlpAnalitics.mdlpCodeModel.Sscc;

public class FromDtoToEntitiesMapper {
    public static Sgtin fromSgtinFilterDtoToSgtin(SgtinFilterMdlpDto sgtinFilterMdlpDto){
        Sgtin sgtin = new Sgtin();
        sgtin.setBatch(sgtinFilterMdlpDto.getBatch());
        sgtin.setEmissionOperationDate(sgtinFilterMdlpDto.getEmissionOperationDate());
        sgtin.setGtin(sgtinFilterMdlpDto.getGtin());
        sgtin.setSgtin(sgtinFilterMdlpDto.getSgtin());
        sgtin.setLastStatusOpDate(sgtinFilterMdlpDto.getLastTracingOpDate());
        sgtin.setStatus(sgtinFilterMdlpDto.getStatus());
        sgtin.setStatusDate(sgtinFilterMdlpDto.getStatusDate());

        return sgtin;
    }

    public static Sscc fromSsccDtoToSscc(SsccDto ssccDto){
        Sscc sscc = new Sscc();
        sscc.setSscc(ssccDto.getSscc());

        return sscc;
    }
}
