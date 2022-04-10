package org.vasvari.gradebook.service.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.UserDto;
import org.vasvari.gradebook.dto.UserRole;
import org.vasvari.gradebook.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebook.dto.dataTypes.UsernameInput;
import org.vasvari.gradebook.model.InitialCredentialsModel;
import org.vasvari.gradebook.model.UserOutputModel;
import org.vasvari.gradebook.model.request.PasswordChangeRequest;
import org.vasvari.gradebook.model.request.UserRequest;
import org.vasvari.gradebook.util.Problem;
import org.vasvari.gradebook.util.TraversonUtil;

import java.util.Collection;
import java.util.Optional;

import static org.vasvari.gradebook.dto.UserRole.STUDENT;
import static org.vasvari.gradebook.dto.UserRole.TEACHER;

@Service
@RequiredArgsConstructor
public class UserGateway {
    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public UserDto findUserById(Long id) {
        ResponseEntity<UserOutputModel> response = template.getForEntity(baseUrl + "/users/{id}", UserOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find user with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<UserDto> findAllUsers() {
        String url = baseUrl + "/users";
        String linkTo = "self";

        return traversonUtil.getUserDtoCollection(url, linkTo);
    }

    public Collection<UserDto> searchUsers(UserRequest request) {
        String url = baseUrl + "/users/search" + request.getFilter();
        String linkTo = "users-filtered";

        return traversonUtil.getUserDtoCollection(url, linkTo);
    }

    public Optional<UserDto> findStudentUser(Long studentId) {
        return findSchoolActor(STUDENT, studentId);
    }

    public Optional<UserDto> findTeacherUser(Long teacherId) {
        return findSchoolActor(TEACHER, teacherId);
    }

    private Optional<UserDto> findSchoolActor(UserRole role, Long schoolActorId) {
        String schoolActorType;
        switch (role) {
            case TEACHER:
                schoolActorType = "teachers";
                break;
            case STUDENT:
                schoolActorType = "students";
                break;
            default:
                throw new RuntimeException("Unrecognised user role");
        }
        String url = String.format("%s/users/%s/{id}", baseUrl, schoolActorType);

        UserDto user = null;
        try {
            ResponseEntity<UserOutputModel> response =
                    template.getForEntity(url, UserOutputModel.class, schoolActorId);
            if (response.getBody() == null) throw new RuntimeException("Unauthorized");
            user = response.getBody().getContent();
        } catch (HttpClientErrorException ex) {
            Problem problem = getProblemFromException(ex);
            if (!problem.getType().equals("users/not-found"))
                throw new RuntimeException(problem.getDetail());
        }

        return Optional.ofNullable(user);
    }

    private Problem getProblemFromException(HttpClientErrorException ex) {
        try {
            return Problem.fromJson(ex.getResponseBodyAsString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveUser(UserDto user) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/users", user, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Failed to save user");
    }

    public void updateUser(Long id, UserDto update) {
        template.put(baseUrl + "/users/{id}", update, id);
    }

    public boolean changePassword(PasswordChangeRequest passwordChangeRequest) {
        ResponseEntity<?> response =
                template.postForEntity(baseUrl + "/users/password-change", passwordChangeRequest, EntityModel.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public void enableUser(Long userId) {
        template.postForEntity(baseUrl + "/users/{id}/enable", null, EntityModel.class, userId);
    }

    public void disableUser(Long userId) {
        template.postForEntity(baseUrl + "/users/{id}/disable", null, EntityModel.class, userId);
    }

    public InitialCredentials createStudentUser(Long studentId) {
        ResponseEntity<InitialCredentialsModel> response =
                template.postForEntity(baseUrl + "/users/create-student-user?studentId={id}", null, InitialCredentialsModel.class, studentId);
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Failed to create student user related to student " + studentId);

        return response.getBody().getContent();
    }

    public InitialCredentials createTeacherUser(Long teacherId) {
        ResponseEntity<InitialCredentialsModel> response =
                template.postForEntity(baseUrl + "/users/create-teacher-user?teacherId={id}", null, InitialCredentialsModel.class, teacherId);
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Failed to create teacher user related to teacher " + teacherId);

        return response.getBody().getContent();
    }

    public InitialCredentials createAdminUser(UsernameInput usernameInput) {
        ResponseEntity<InitialCredentialsModel> response =
                template.postForEntity(baseUrl + "/users/create-admin-user", usernameInput, InitialCredentialsModel.class);
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Failed to create admin user with username " + usernameInput.getUsername());

        return response.getBody().getContent();
    }

    public void deleteUser(Long id) {
        template.delete(baseUrl + "/users/{id}", id);
    }
}
