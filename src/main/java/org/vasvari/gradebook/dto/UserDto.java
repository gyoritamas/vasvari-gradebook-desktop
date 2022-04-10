package org.vasvari.gradebook.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private boolean enabled;
}
