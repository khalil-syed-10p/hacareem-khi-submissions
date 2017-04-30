package com.hacareem.finaldestination.repository.impl;

import com.hacareem.finaldestination.domain.RideSuggestion;
import com.hacareem.finaldestination.domain.TimeShift;
import com.hacareem.finaldestination.repository.base.RideSuggestionRepository;
import com.hacareem.finaldestination.search.params.RideSuggestionParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * Created by waqas on 4/30/17.
 */
@Repository
public class RideSuggestionRepositoryImpl implements RideSuggestionRepository {
    @Autowired
    private EntityManager em;

    @Override
    public RideSuggestion get(final RideSuggestionParams params) {
        final TimeShift shift = TimeShift.decideShift(params.getTimestamp());
        final List list = em.createQuery("from RideSuggestion where userId = ?1 AND pickupLatitude = ?2 AND pickupLongitude = ?3")
                .setParameter(1, params.getUserId())
                .setParameter(2, params.getLatitude())
                .setParameter(3, params.getLongitude())
                //.setParameter(4, shift.getValue())
                .getResultList();
        if (!list.isEmpty()) {
            return (RideSuggestion) list.get(0);
        }
        return null;
    }

    @Override
    public RideSuggestion save(final RideSuggestion rideSuggestion) {
        em.persist(rideSuggestion);
        return rideSuggestion;
    }

    @Override
    public RideSuggestion update(final RideSuggestion rideSuggestion) {
        em.persist(rideSuggestion);
        return rideSuggestion;
    }
}
