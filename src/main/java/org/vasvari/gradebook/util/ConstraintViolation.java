package org.vasvari.gradebook.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintViolation {

    private String type;
    private Integer status;
    private Violation[] violations;
    private String title;

    @JsonCreator
    public static ConstraintViolation fromJson(String jsonValue) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonValue, ConstraintViolation.class);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Violation {
        private String field;
        private String message;
    }

    public String violationsString(){
        return Arrays.stream(this.getViolations())
                .map(Violation::getMessage)
                .collect(Collectors.joining("\n"));
    }
}
