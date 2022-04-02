package org.vasvari.gradebook.dto.dataTypes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SimpleTeacher {
    private Long id;
    private String firstname;
    private String lastname;

    public String getName(){
        return lastname + " " + firstname;
    }
}
