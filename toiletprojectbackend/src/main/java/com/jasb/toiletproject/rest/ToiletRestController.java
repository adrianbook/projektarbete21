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

@Slf4j
@RestController
@RequestMapping("api/v1/toilets")
@CrossOrigin
public class ToiletRestController {

    @Autowired
    ToiletRepository data;


    @GetMapping("/getalltoilets")
    public ToiletList allToilets() {
        log.info("Returning all toilets");
        return new ToiletList(data.findAll());
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        log.info("Add ing new toilet at longitude: {} latitude:  {}", t.getLongitude(), t.getLatitude());
        data.save(t);
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        log.info("Finding toilet with id {}", id);
        return data.findById(id);
    }
}
