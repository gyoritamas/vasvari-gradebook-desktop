package org.vasvari.gradebook.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebook.dto.SubjectInput;
import org.vasvari.gradebook.dto.SubjectOutput;
import org.vasvari.gradebook.service.SubjectService;

import java.util.Collection;

@Controller
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @GetMapping
    @ResponseBody
    public Collection<SubjectOutput> findAllSubjects() {
        return service.findAllSubjects();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public SubjectOutput findSubjectById(@PathVariable("id") Long id) {
        return service.findSubjectById(id);
    }

    @PostMapping
    @ResponseBody
    public void saveSubject(@RequestBody SubjectInput subject) {
        service.saveSubject(subject);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateSubject(@PathVariable("id") Long id, @RequestBody SubjectInput update) {
        service.updateSubject(id, update);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteSubject(@PathVariable("id") Long id) {
        service.deleteSubject(id);
    }

}
