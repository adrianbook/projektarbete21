package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Rating;
import com.jasb.entities.Toilet;
import com.jasb.entities.ToiletUser;
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

    @Override
    public List<Toilet> getAllToilets() {
        log.info("Returning all toilets");
        return toiletDao.findAll();
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

    @Override
    public Optional<Toilet> getToiletById(long id) {
        log.info("Finding toilet with id {}", id);
        return toiletDao.findById(id);
    }

    @Override
    public void addRating(Long toiletId, Rating rating/*, ToiletUser toiletUser*/) {
        /*toiletDao.addRating(toiletId, rating);*/
        Optional<Toilet> t = toiletDao.findById(toiletId);
        Toilet toilet = t.get();
        /*toilet.setRatings(Collections.singleton(rating));*/
        Collection<Rating> ratings = toilet.getRatings();
        ratings.add(rating);
        toilet.setRatings(ratings);
        toiletDao.save(toilet);
    }
}
