package org.vasvari.gradebook.dto.dataTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernameInput {

    @Size(min = 4, max = 20, message = "a méretnek 4 és 20 közötti értéknek kell lennie")
    @Pattern(regexp = "^[a-zA-Z]([0-9a-zA-Z]){3,20}",
            message = "a felhasználónév csak betűket és számokat tartalmazhat és betűvel kell kezdődnie")
    private String username;
}
