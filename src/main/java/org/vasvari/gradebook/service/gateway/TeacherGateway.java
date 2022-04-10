package org.vasvari.gradebook.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.model.TeacherOutputModel;
import org.vasvari.gradebook.model.request.TeacherRequest;
import org.vasvari.gradebook.util.TraversonUtil;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TeacherGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public TeacherDto findTeacherById(Long id) {
        ResponseEntity<TeacherOutputModel> response = template.getForEntity(baseUrl + "/teachers/{id}", TeacherOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find teacher with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<TeacherDto> findAllTeachers() {
        String url = baseUrl + "/teachers";
        String linkTo = "self";

        return traversonUtil.getTeacherDtoCollection(url, linkTo);
    }

    public Collection<TeacherDto> searchTeachers(TeacherRequest request) {
        String url = baseUrl + "/teachers/search" + request.getFilter();
        String linkTo = "teachers-filtered";

        return traversonUtil.getTeacherDtoCollection(url, linkTo);
    }

    public void saveTeacher(TeacherDto teacher) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/teachers", teacher, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Failed to save teacher");
    }

    public void updateTeacher(Long id, TeacherDto update) {
        template.put(baseUrl + "/teachers/{id}", update, id);
    }

    public void deleteTeacher(Long id) {
        template.delete(baseUrl + "/teachers/{id}", id);
    }
}
