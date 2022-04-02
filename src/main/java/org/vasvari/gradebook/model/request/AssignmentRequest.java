package org.vasvari.gradebook.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.vasvari.gradebook.dto.AssignmentType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignmentRequest {
    private String title;
    private AssignmentType type;
    private Long subjectId;

    public String getFilter() {
        if ((title == null || title.isEmpty()) && type == null && subjectId == null) return "";

        List<String> filters = new ArrayList<>();
        if (title != null && !title.isEmpty())
            filters.add("title=" + URLEncoder.encode(title, StandardCharsets.UTF_8));
        if (type != null)
            filters.add("type=" + type);
        if (subjectId != null)
            filters.add("subjectId=" + subjectId);

        return "?" + Strings.join(filters, '&');
    }
}
