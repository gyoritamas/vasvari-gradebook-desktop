package org.vasvari.gradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.TeacherDto;
import org.vasvari.gradebook.model.request.TeacherRequest;
import org.vasvari.gradebook.service.gateway.TeacherGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherGateway gateway;

    public TeacherDto findTeacherById(Long id) {
        return gateway.findTeacherById(id);
    }

    public List<TeacherDto> findAllTeachers() {
        return new ArrayList<>(gateway.findAllTeachers());
    }

    public List<TeacherDto> searchTeachers(TeacherRequest request){
        return new ArrayList<>(gateway.searchTeachers(request));
    }

    public void saveTeacher(TeacherDto teacher) {
        gateway.saveTeacher(teacher);
    }

    public void updateTeacher(Long id, TeacherDto teacher) {
        gateway.updateTeacher(id, teacher);
    }

    public void deleteTeacher(Long id) {
        gateway.deleteTeacher(id);
    }
}
