package org.vasvari.gradebook.dto.dataTypes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SimpleData {
    private Long id;
    private String name;
}
