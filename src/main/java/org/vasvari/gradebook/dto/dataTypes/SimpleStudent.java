package org.vasvari.gradebook.dto.dataTypes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SimpleStudent {
    private Long id;
    private String firstname;
    private String lastname;

    public String getName(){
        return lastname + " " + firstname;
    }

    @Override
    public String toString() {
        return getName();
    }
}

