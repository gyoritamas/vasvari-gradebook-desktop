package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.UserDto;
import org.vasvari.gradebook.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebook.dto.dataTypes.UsernameInput;
import org.vasvari.gradebook.model.request.PasswordChangeRequest;
import org.vasvari.gradebook.model.request.UserRequest;
import org.vasvari.gradebook.service.gateway.UserGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserGateway gateway;

    public UserDto findUserById(Long id) {
        return gateway.findUserById(id);
    }

    public List<UserDto> findAllUsers() {
        return new ArrayList<>(gateway.findAllUsers());
    }

    public List<UserDto> searchUsers(UserRequest request) {
        return new ArrayList<>(gateway.searchUsers(request));
    }

    public Optional<UserDto> findStudentUser(Long studentId) {
        return gateway.findStudentUser(studentId);
    }

    public Optional<UserDto> findTeacherUser(Long teacherId) {
        return gateway.findTeacherUser(teacherId);
    }

    public void saveUser(UserDto user) {
        gateway.saveUser(user);
    }

    public void updateUser(Long id, UserDto update) {
        gateway.updateUser(id, update);
    }

    public boolean changePassword(PasswordChangeRequest passwordChangeRequest) {
        return gateway.changePassword(passwordChangeRequest);
    }

    public void enableUser(Long userId) {
        gateway.enableUser(userId);
    }

    public void disableUser(Long userId) {
        gateway.disableUser(userId);
    }

    public InitialCredentials createStudentUser(Long studentId) {
        return gateway.createStudentUser(studentId);
    }

    public InitialCredentials createTeacherUser(Long teacherId) {
        return gateway.createTeacherUser(teacherId);
    }

    public InitialCredentials createAdminUser(UsernameInput username) {
        return gateway.createAdminUser(username);
    }

    public void deleteUser(Long id) {
        gateway.deleteUser(id);
    }
}
