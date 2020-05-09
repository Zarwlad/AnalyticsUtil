package ru.zarwlad.mdlp.downloadMdlpPartners.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class WorkDescription {
    private UUID id;
    private RznAddress rznAddress;
    private String workDescription;
}
