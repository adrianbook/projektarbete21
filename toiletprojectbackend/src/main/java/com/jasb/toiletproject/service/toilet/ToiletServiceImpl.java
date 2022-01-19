package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.util.Proximity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j @RequiredArgsConstructor
public class ToiletServiceImpl implements ToiletService {
    @Autowired
    ToiletRepository toiletDao;
    @Autowired
    RatingRepository ratingDao;

/*    @Override
    public Optional<Toilet> getAvgRating(long id) {
        log.info("Fining toilet with id {}", id);
        Optional<Toilet> t = toiletDao.findById(id);
        double avgRating = ratingDao.avgRating(t.get().getId());
        t.get().setAvgRating(avgRating);
        return t;
    }*/

    @Override
    public Optional<Toilet> getToiletById(long id) {
        log.info("Finding toilet with id {}", id);
        Optional<Toilet> optionalToilet = toiletDao.findById(id);
        try {
            /*double avgRating = ratingDao.avgRating(optionalToilet.get().getId());
            optionalToilet.get().setAvgRating(avgRating);*/
            /**//*optionalToilet.get().setAvgRating(ratingDao.getAvgRating(optionalToilet.get().getId()));*/
            Toilet toilet = optionalToilet.get();
            toilet.setAvgRating(ratingDao.getAvgRating(optionalToilet.get()/*.getId()*/));
            log.info("Trying to get avg {}", ratingDao.getAvgRating(toilet/*.getId()*/));
            log.info("Toilet avg rating is {}", toilet.getAvgRating());
            /*log.info("Setting rating {} for toilet {]", avgRating, id);*/
            Optional<Toilet> t = Optional.of(toilet);
            return t;
        } catch (NullPointerException e) {
            System.out.println("Här är felet igen!");
        }
        /* return optionalToilet*/;
       return optionalToilet;
    }

    @Override
    public List<Toilet> getAllToilets() {
        log.info("Returning all toilets");
        List<Toilet> toiletList = toiletDao.findAll();
        double avgRating;
        for (Toilet t :
                toiletList) {
            try {
                avgRating = ratingDao.getAvgRating(t/*.getId()*/);
                t.setAvgRating(avgRating);

            } catch (NullPointerException e) {
                System.out.println("Detta är felet");
            }
        }
        return toiletList;
    }

    @Override
    public void addToilet(Toilet newToilet) throws ToCloseToAnotherToiletException {
        List<Toilet> toilets = this.getAllToilets();
        if (Proximity.toClose(newToilet, toilets)) {
            log.info("Did not add toilet at longitude: {} latitude: {}",
                    newToilet.getLongitude(), newToilet.getLatitude() + " it's to " +
                            "close to another toilet");
            throw new ToCloseToAnotherToiletException();
        }
        log.info("Adding new toilet at longitude: {} latitude:  {}",
                newToilet.getLongitude(), newToilet.getLatitude());
        toiletDao.save(newToilet);
    }

/*    @Override
    public  Rating addRating(Long toiletId, Rating rating*//*, ToiletUser toiletUser*//*) {
        *//*toiletDao.addRating(toiletId, rating);*//*
        Optional<Toilet> t = toiletDao.findById(toiletId);
        Toilet toilet = t.get();
        *//*toilet.setRatings(Collections.singleton(rating));*//*
        Collection<Rating> ratings = toilet.getRatings();
        ratings.add(rating);
        toilet.setRatings(ratings);
        toiletDao.save(toilet);
    }*/
}
