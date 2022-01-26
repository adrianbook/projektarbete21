package com.jasb.toiletproject.service.toilet;

import com.jasb.entities.Toilet;
import com.jasb.toiletproject.exceptions.ToCloseToAnotherToiletException;
import com.jasb.toiletproject.exceptions.ToiletNotFoundException;
import com.jasb.toiletproject.repo.ToiletRepository;
import com.jasb.toiletproject.service.rating.RatingService;
import com.jasb.toiletproject.util.Proximity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for ToiletRepository
 * Written by JASB
 */
@Service @Slf4j @RequiredArgsConstructor
public class ToiletServiceImpl implements ToiletService {

    private final ToiletRepository toiletDao;
    private final RatingService ratingService;

    /**
     * Method to get a toilet by id. Throws exception if toilet is not found
     * @param id toiletID
     * @return toilet if found
     * @throws ToiletNotFoundException
     */
    @Override
    public Toilet getToiletById(long id) throws ToiletNotFoundException {
        log.info("Finding toilet with id {}", id);
        Optional<Toilet> optionalToilet = toiletDao.findById(id);
        if (optionalToilet.isEmpty()) {
            throw new ToiletNotFoundException(id);
        }
        optionalToilet.ifPresent(toilet -> toilet.setAvgRating(ratingService.getUpdatedAvgRating(toilet.getId())));
        return optionalToilet.get();
    }

    /**
     * Method to get all toilets
     * @return a list of all Toilets
     */
    @Override
    public List<Toilet> getAllToilets() {
        log.info("Returning all toilets");
        List<Toilet> toiletList = toiletDao.findAll();
        for (Toilet t :
                toiletList) {
            t.setAvgRating(ratingService.getUpdatedAvgRating(t.getId()));
        }
        return toiletList;
    }

    /**
     * Method to add a toilet. Uses util Proximity to check if the toilet
     * is to close to another toilet, if it is it throws an Exception
     * @param newToilet
     * @return the added toilet
     * @throws ToCloseToAnotherToiletException
     */
    @Override
    public Toilet addToilet(Toilet newToilet) throws ToCloseToAnotherToiletException {
        List<Toilet> toilets = this.getAllToilets();
        if (Proximity.tooClose(newToilet, toilets)) {
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
