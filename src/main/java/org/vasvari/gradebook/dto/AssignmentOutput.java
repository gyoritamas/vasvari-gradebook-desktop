package org.vasvari.gradebook.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.vasvari.gradebook.dto.dataTypes.SimpleData;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AssignmentOutput {

    private Long id;
    private String name;
    private AssignmentType type;
    private String description;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private SimpleData subject;

    public Boolean isExpired(){
        return LocalDate.now().isAfter(deadline);
    }

}
