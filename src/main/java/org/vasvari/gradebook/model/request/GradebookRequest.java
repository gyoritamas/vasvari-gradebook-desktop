package org.vasvari.gradebook.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradebookRequest {
    private Long studentId;
    private Long subjectId;
    private Long assignmentId;

    public String getFilter() {
        if (studentId == null && subjectId == null && assignmentId == null) return "";

        List<String> filters = new ArrayList<>();
        if (studentId != null)
            filters.add("studentId=" + studentId);
        if (subjectId != null)
            filters.add("subjectId=" + subjectId);
        if (assignmentId != null)
            filters.add("assignmentId=" + assignmentId);

        return "?" + Strings.join(filters, '&');
    }
}
