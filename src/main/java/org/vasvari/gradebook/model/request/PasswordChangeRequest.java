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
    private String newPassword;
}
