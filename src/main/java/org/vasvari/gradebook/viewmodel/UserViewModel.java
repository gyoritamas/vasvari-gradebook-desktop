package org.vasvari.gradebook.viewmodel;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserViewModel {
    private Long id;
    private String username;
    private String role;
    private String enabled;
}
