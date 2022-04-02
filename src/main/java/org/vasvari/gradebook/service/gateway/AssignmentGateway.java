package org.vasvari.gradebook.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.AssignmentInput;
import org.vasvari.gradebook.dto.AssignmentOutput;
import org.vasvari.gradebook.model.AssignmentOutputModel;
import org.vasvari.gradebook.model.request.AssignmentRequest;
import org.vasvari.gradebook.util.TraversonUtil;

import java.util.Collection;

@Service
@ComponentScan
@RequiredArgsConstructor
public class AssignmentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public AssignmentOutput findAssignmentById(Long id) {
        ResponseEntity<AssignmentOutputModel> response =
                template.getForEntity(baseUrl + "/assignments/{id}", AssignmentOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find assignment with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<AssignmentOutput> findAllAssignments() {
        String url = baseUrl + "/assignments";
        String linkTo = "self";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public Collection<AssignmentOutput> searchAssignments(AssignmentRequest request) {
        String url = baseUrl + "/assignments/search" + request.getFilter();
        String linkTo = "assignments-filtered";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public Collection<AssignmentOutput> findAssignmentsOfCurrentUserAsTeacher() {
        String url = baseUrl + "/teacher-user/assignments";
        String linkTo = "assignments-of-teacher";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public Collection<AssignmentOutput> findAssignmentsOfCurrentUserAsTeacher(AssignmentRequest request) {
        String url = baseUrl + "/teacher-user/assignments" + request.getFilter();
        String linkTo = "assignments-of-teacher";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public Collection<AssignmentOutput> findAssignmentsOfCurrentUserAsStudent() {
        String url = baseUrl + "/student-user/assignments";
        String linkTo = "assignments-of-student";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public Collection<AssignmentOutput> findAssignmentsOfCurrentUserAsStudent(AssignmentRequest request) {
        String url = baseUrl + "/student-user/assignments" + request.getFilter();
        String linkTo = "assignments-of-student";

        return traversonUtil.getAssignmentOutputCollection(url, linkTo);
    }

    public void save(AssignmentInput assignment) {
        ResponseEntity<?> response =
                template.postForEntity(baseUrl + "/assignments", assignment, EntityModel.class);
        if (response.getStatusCodeValue() != 201)
            throw new RuntimeException("Failed to save assignment");
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        template.put(baseUrl + "/assignments/{id}", update, id);
    }

    public void deleteAssignment(Long id) {
        template.delete(baseUrl + "/assignments/{id}", id);
    }

}
