package org.vasvari.gradebook.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
@Setter
public class PasswordChangeRequest {
    private String oldPassword;

    @Size(min = 8, max = 20, message = "a méretnek 8 és 20 közötti értéknek kell lennie")
    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{8,20}$",
            message = "a jelszónak legalább egy számot, egy kis- és egy nagybetűt kell tartalmaznia")
    private String newPassword;
}
