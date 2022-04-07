package org.vasvari.gradebook.viewmodel.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebook.dto.UserDto;
import org.vasvari.gradebook.dto.UserRole;
import org.vasvari.gradebook.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserViewModelMapper {

    public UserViewModel map(UserDto userDto) {
        return UserViewModel.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .role(userDto.getRole().getLocalizedName())
                .enabled(userDto.isEnabled() ? "igen" : "nem")
                .build();
    }

    public List<UserViewModel> mapAll(List<UserDto> userDTOs) {
        return userDTOs.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

}
