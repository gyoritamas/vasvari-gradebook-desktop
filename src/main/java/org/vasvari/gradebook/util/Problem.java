package org.vasvari.gradebook.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Problem  {

    private String type;
    private String title;
    private Integer status;
    private String detail;

    @JsonCreator
    public static Problem fromJson(String jsonValue) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonValue, Problem.class);
    }
}
