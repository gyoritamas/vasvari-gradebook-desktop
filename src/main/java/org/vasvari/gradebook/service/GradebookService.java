package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.GradebookInput;
import org.vasvari.gradebook.dto.GradebookOutput;
import org.vasvari.gradebook.model.request.GradebookRequest;
import org.vasvari.gradebook.service.gateway.GradebookGateway;
import org.vasvari.gradebook.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final UserUtil userUtil;
    private final GradebookGateway gateway;

    public GradebookOutput findGradebookEntryById(Long id) {
        return gateway.findGradebookEntryById(id);
    }

    public List<GradebookOutput> findGradebookEntriesForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllGradebookEntries();
            case TEACHER:
                return findGradebookEntriesOfTeacherUser();
            case STUDENT:
                return findGradebookEntriesOfStudentUser();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<GradebookOutput> findGradebookEntriesForUser(GradebookRequest request) {
        switch (userUtil.userRole()) {
            case ADMIN:
                return searchGradebookEntries(request);
            case TEACHER:
                return findGradebookEntriesOfTeacherUser(request);
            case STUDENT:
                return findGradebookEntriesOfStudentUser(request);
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<GradebookOutput> findAllGradebookEntries() {
        return new ArrayList<>(gateway.findAllGradebookEntries());
    }

    public List<GradebookOutput> searchGradebookEntries(GradebookRequest request) {
        return new ArrayList<>(gateway.searchGradebookEntries(request));
    }

    private List<GradebookOutput> findGradebookEntriesOfTeacherUser() {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsTeacher());
    }

    private List<GradebookOutput> findGradebookEntriesOfTeacherUser(GradebookRequest request) {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsTeacher(request));
    }

    private List<GradebookOutput> findGradebookEntriesOfStudentUser() {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsStudent());
    }

    private List<GradebookOutput> findGradebookEntriesOfStudentUser(GradebookRequest request) {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsStudent(request));
    }

    public List<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        return new ArrayList<>(gateway.findGradebookEntriesOfStudent(studentId));
    }

    public List<GradebookOutput> findGradebookEntriesOfSubject(Long subjectId) {
        return new ArrayList<>(gateway.findGradebookEntriesOfSubject(subjectId));
    }

    public void saveGradebookEntry(GradebookInput gradebookEntry) {
        gateway.saveGradebookEntry(gradebookEntry);
    }

    public void updateEntry(Long id, GradebookInput update) {
        gateway.updateGradebookEntry(id, update);
    }

    public void deleteGradebookEntry(Long id) {
        gateway.deleteGradebookEntry(id);
    }
}
