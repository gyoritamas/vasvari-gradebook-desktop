package org.vasvari.gradebook.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.vasvari.gradebook.dto.UserRole;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String username;
    private UserRole role;
    private Boolean enabled;

    public String getFilter() {
        if ((username == null || username.isEmpty()) && role == null && enabled == null) return "";

        List<String> filters = new ArrayList<>();
        if (username != null && !username.isEmpty())
            filters.add("username=" + URLEncoder.encode(username, StandardCharsets.UTF_8));
        if (role != null)
            filters.add("role=" + role);
        if (enabled != null)
            filters.add("enabled=" + enabled);

        return "?" + Strings.join(filters, '&');
    }
}
