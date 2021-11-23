package com.jasb.toiletproject.rest;

import com.jasb.toiletproject.data.ToiletRepository;
import com.jasb.toiletproject.domain.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("admin/api/v1/toilets")
@CrossOrigin("localhost:5500")
public class AdminToiletRestController {


    @Autowired
    ToiletRepository data;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ToiletList allToilets() {
        return new ToiletList(data.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        data.save(t);
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }
    @GetMapping (path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        return data.findById(id);
    }

    @DeleteMapping (path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteToilet(@PathVariable("id") long id) {data.deleteById(id);}

    @PutMapping (path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Toilet> updateStudent(@PathVariable("id") long id, @RequestBody Toilet newToilet) {
        return data.findById(id)
                .map(toilet -> {
                    toilet.setLongitude(newToilet.getLongitude());
                    toilet.setLatitude(newToilet.getLatitude());
                    return data.save(toilet);
                });
    }

}
