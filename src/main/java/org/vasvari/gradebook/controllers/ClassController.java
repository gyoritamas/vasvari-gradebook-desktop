package org.vasvari.gradebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebook.dto.ClassInput;
import org.vasvari.gradebook.dto.ClassOutput;
import org.vasvari.gradebook.service.ClassGateway;

import java.util.Collection;

@Controller
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassGateway classGateway;

    @GetMapping
    @ResponseBody
    public Collection<ClassOutput> findAllClasses() {
        return classGateway.findAllClasses();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> findClassById(@PathVariable("id") Long id) {
        return classGateway.findClassById(id);
    }

    @PostMapping
    @ResponseBody
    public void createClass(@RequestBody ClassInput clazz) {
        classGateway.save(clazz);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateClassById(@PathVariable("id") Long id, @RequestBody ClassInput update) {
        classGateway.updateClass(id, update);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteById(@PathVariable("id") Long id) {
        classGateway.deleteClass(id);
    }

}
