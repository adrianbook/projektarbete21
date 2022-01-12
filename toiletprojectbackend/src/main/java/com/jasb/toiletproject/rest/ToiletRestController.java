package com.jasb.toiletproject.rest;


import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.service.toilet.ToCloseToAnotherToiletException;
import com.jasb.toiletproject.service.toilet.ToiletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.*;


/**
 * RestController class intended for uses by users.
 * Contains endpoints that a basic user should have
 * access to. Also contains open endpoint that return
 * all the toilets in the database
 *
 * Written by JASB
 */

@RestController
@RequestMapping("api/v1/toilets")
@CrossOrigin
@RequiredArgsConstructor
public class ToiletRestController {
    /**
     * Dependencyinjection of JPArepostitory
     */
   /* @Autowired
    ToiletRepository data;
    @Autowired
    RatingRepository ratingData;*/

    private final ToiletService toiletService;
    private final RatingService ratingService;

    /**
     * Open GET endpoint that returns all the toilets in the database
     * @return list of all the toilets in the database
     */
    @GetMapping("/getalltoilets")
    public List<Toilet> allToilets() {
        return toiletService.getAllToilets();
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
        try {
            toiletService.addToilet(t);
        } catch (ToCloseToAnotherToiletException e) {
            return new ResponseEntity<>("Toilet to close to another " +
                    "toilet", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    @PostMapping("/createrating/{id}")
    @PreAuthorize("hasAnyRole('ROLE_APPUSER', 'ROLE_ADMIN')")
    public ResponseEntity addRating(@PathVariable long id,
                                    @RequestBody Rating r) {
        ratingService.addRating(id, r);
        // Todo: returnerar alltid created just nu...
        return new ResponseEntity<>(r, HttpStatus.CREATED);
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
        return toiletService.getToiletById(id);
    }
}
