package com.jasb.toiletproject.rest;



import com.jasb.toiletproject.data.ToiletRepository;
import com.jasb.toiletproject.domain.Toilet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin
    @GetMapping("/toilets")
    public ToiletList allToilets() {
        return new ToiletList(data.findAll());
    }

    @CrossOrigin
    @PostMapping(value="/toilets",
                consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType. APPLICATION_XHTML_XML_VALUE},
                produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XHTML_XML_VALUE })
    public ResponseEntity createNewToilet(@RequestBody Toilet toilet) {
        data.save(toilet);
        return new ResponseEntity<Toilet>(toilet, HttpStatus.CREATED);
    }
}
