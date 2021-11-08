package com.jasb.toiletproject.rest;

//import com.jasb.toiletproject.data.ToiletRepository;
import com.jasb.toiletproject.domain.Toilet;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToiletRestController {

    //@Autowired
    //private ToiletRepository data;

    @RequestMapping(value = "/alltoilets", method = RequestMethod.GET)
    public ToiletList allToilets() {
        //List<Toilet> allToilets = data.findAll();
        List<Toilet> allToilets = List.of(new Toilet(1,2.1,2.1));
        return new ToiletList(allToilets);
    }
    @RequestMapping(value = "/onetoilet", method = RequestMethod.GET)
    public String oneToilets() {
        //List<Toilet> allToilets = data.findAll();
        //List<Toilet> allToilets = List.of(new Toilet(1,2.1,2.1));
        Toilet toilet = new Toilet(1,2.2,2.1);
        return toilet.toString();
    }
}
