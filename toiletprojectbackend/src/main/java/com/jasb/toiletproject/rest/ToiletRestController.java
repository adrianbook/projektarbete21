package com.jasb.toiletproject.rest;



import com.jasb.toiletproject.data.ToiletRepository;
import com.jasb.toiletproject.domain.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ToiletRestController {
    /*
    @RequestMapping(value = "/onetoilet", method = RequestMethod.GET)
    public String oneToilet() {
        //List<Toilet> allToilets = data.findAll();
        //List<Toilet> allToilets = List.of(new Toilet(1,2.1,2.1));
        Toilet toilet = new Toilet(1,2.2,2.1);
        return toilet.toString();
    }
    */

    @Autowired
    ToiletRepository data;

    @RequestMapping(value = "/toilets", method = RequestMethod.GET)
    public ToiletList allToilets() {
        return new ToiletList(data.findAll());
    }

    @RequestMapping(value = "/toilets", method = RequestMethod.POST)
    public ResponseEntity addToilet(@RequestBody Toilet t) {
        data.save(t);
        return new ResponseEntity<Toilet>(t, HttpStatus.CREATED);
    }
    @RequestMapping(value ="/toilets/{id}",method = RequestMethod.GET)
    public Optional<Toilet> getToiletById(@PathVariable("id") long id) {
        return data.findById(id);
    }
}
