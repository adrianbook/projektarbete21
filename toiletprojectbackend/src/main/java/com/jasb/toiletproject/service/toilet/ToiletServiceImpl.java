package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
import com.jasb.toiletproject.repo.RatingRepository;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.util.Proximity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j @RequiredArgsConstructor
public class ToiletServiceImpl implements ToiletService {

    private final ToiletRepository toiletDao;
    private final RatingService ratingService;


    @Override
    public Optional<Toilet> getToiletById(long id) {
        log.info("Finding toilet with id {}", id);
        Optional<Toilet> optionalToilet = toiletDao.findById(id);
        optionalToilet.ifPresent(toilet -> toilet.setAvgRating(ratingService.getUpdatedAvgRating(toilet.getId())));
        return optionalToilet;
    }

    @Override
    public List<Toilet> getAllToilets() {
        log.info("Returning all toilets");
        List<Toilet> toiletList = toiletDao.findAll();
        double avgRating;
        for (Toilet t :
                toiletList) {
            //avgRating = ratingDao.findAvgRating(t.getId());
            avgRating = ratingService.getUpdatedAvgRating(t.getId());
            t.setAvgRating(avgRating);
        }
        return toiletList;
    }

    @Override
    public Toilet addToilet(Toilet newToilet) throws ToCloseToAnotherToiletException {
        List<Toilet> toilets = this.getAllToilets();
        if (Proximity.toClose(newToilet, toilets)) {
            log.info("Did not add toilet at longitude: {} latitude: {}",
                    newToilet.getLongitude(), newToilet.getLatitude() + " it's to " +
                            "close to another toilet");
            throw new ToCloseToAnotherToiletException();
        }
        log.info("Adding new toilet at longitude: {} latitude:  {}",
                newToilet.getLongitude(), newToilet.getLatitude());
        return toiletDao.save(newToilet);
    }


}
