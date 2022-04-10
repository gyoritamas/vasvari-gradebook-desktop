package org.vasvari.gradebook.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentRequest {
    private String name;
    private Integer gradeLevel;
    private Long subjectId;

    public String getFilter() {
        if ((name == null || name.isEmpty()) && gradeLevel == null && subjectId == null) return "";

        List<String> filters = new ArrayList<>();
        if (name != null && !name.isEmpty())
            filters.add("studentName=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
        if (gradeLevel != null)
            filters.add("gradeLevel=" + gradeLevel);
        if (subjectId != null)
            filters.add("subjectId=" + subjectId);

        return "?" + Strings.join(filters, '&');
    }
}
