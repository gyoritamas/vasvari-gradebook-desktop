package org.vasvari.gradebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebook.dto.ClassInput;
import org.vasvari.gradebook.dto.ClassOutput;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService {
    @Autowired
    private ClassGateway gateway;

    public ClassOutput findClassById(Long id) {
        return gateway.findClassById(id).getBody().getContent();
    }

    public List<ClassOutput> findAllClasses() {
        return new ArrayList<>(gateway.findAllClasses());
    }

    public void save(ClassInput clazz) {
        gateway.save(clazz);
    }

    public void updateClass(Long id, ClassInput update) {
        gateway.updateClass(id, update);
    }

    public void deleteClass(Long id) {
        gateway.deleteClass(id);
    }
}
