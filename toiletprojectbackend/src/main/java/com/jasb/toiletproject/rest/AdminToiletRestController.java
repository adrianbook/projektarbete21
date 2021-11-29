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
@RequestMapping("admin/api/v1/toilets")
public class AdminToiletRestController {


    @Autowired
    ToiletRepository data;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ToiletList allToilets() {
        log.info("Returning all toilets");
        return new ToiletList(data.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        data.save(t);
        log.info("Adding new toilet at longitude: {} latitude:  {}", t.getLongitude(), t.getLatitude());
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        log.info("Finding toilet with id {}", id);
        return data.findById(id);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteToilet(@PathVariable("id") long id) {
        log.info("Deleting toilet with {}", id);
        data.deleteById(id);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> updateToilet(@PathVariable("id") long id, @RequestBody Toilet newToilet) {
        log.info("Updating toilet with id {} with info {}", id, newToilet.toString());
        return data.findById(id)
                .map(toilet -> {
                    toilet.setLongitude(newToilet.getLongitude());
                    toilet.setLatitude(newToilet.getLatitude());
                    return data.save(toilet);
                });
    }
}
