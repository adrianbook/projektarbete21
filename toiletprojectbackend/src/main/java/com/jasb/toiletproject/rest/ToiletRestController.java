package com.jasb.toiletproject.rest;


import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.domain.Toilet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * RestController class intended for uses by users.
 * Contains endpoints that a basic user should have
 * access to. Also contains open endpoint that return
 * all the toilets in the database
 *
 * Written by JASB
 */
@Slf4j
@RestController
@RequestMapping("api/v1/toilets")
@CrossOrigin
public class ToiletRestController {
    /**
     * Dependencyinjection of JPArepostitory
     */
    @Autowired
    ToiletRepository data;

    /**
     * Open GET endpoint that returns all the toilets in the database
     * @return list of all the toilets in the database
     */
    @GetMapping("/getalltoilets")
    public ToiletList allToilets() {
        log.info("Returning all toilets");
        return new ToiletList(data.findAll());
    }

    /**
     * POST endpoint for adding a new toilet. Open to anyone with
     * the ROLE_APPUSER credentiols.
     * @param t a JSON representation of a toilet in the request body
     * @return a JSON representation of the created toilet and a responsecode
     */
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        log.info("Add ing new toilet at longitude: {} latitude:  {}", t.getLongitude(), t.getLatitude());
        data.save(t);
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }

    /**
     * GET endpont for getting a toilet by its assigned id Open to anyone with
     * the ROLE_APPUSER credentiols.
     * @param id id from the request URI
     * @return A toilet
     */
    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        log.info("Finding toilet with id {}", id);
        return data.findById(id);
    }
}
