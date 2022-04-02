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
public class TeacherRequest {
    private String name;

    public String getFilter() {
        if (name == null || name.isEmpty()) return "";

        List<String> filters = new ArrayList<>();
        if (name != null && !name.isEmpty())
            filters.add("teacherName=" + URLEncoder.encode(name, StandardCharsets.UTF_8));

        return "?" + Strings.join(filters, '&');
    }
}
