package org.vasvari.gradebook.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebook.dto.ClassInput;
import org.vasvari.gradebook.dto.ClassOutput;
import org.vasvari.gradebook.model.ClassOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@Slf4j
@ComponentScan
public class ClassGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public ClassGateway(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<ClassOutputModel> findClassById(Long id) {
        return restTemplate.getForEntity(baseUrl + "/classes/{id}", ClassOutputModel.class, id);
    }

    public Collection<ClassOutput> findAllClasses() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/classes"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<ClassOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        CollectionModel<ClassOutput> classResource = traverson
                .follow("$._links.self.href")
                .toObject(collectionModelType);

        if (classResource != null)
            return classResource.getContent();
        else
            return Collections.emptyList();
    }

    public ResponseEntity<?> save(ClassInput clazz) {
        return restTemplate.postForEntity(baseUrl + "/classes", clazz, EntityModel.class);
    }

    public void updateClass(Long id, ClassInput update) {
        restTemplate.put(baseUrl + "/classes/{id}", update, id);
    }

    public void deleteClass(Long id) {
        restTemplate.delete(baseUrl + "/classes/{id}", id);
    }
}
