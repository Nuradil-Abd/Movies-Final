package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Card;
import peaksoft.repo.CardRepo;

import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class CardRepoImpl implements CardRepo {
    @PersistenceContext
    EntityManager entityManager;


    @Override
    public void save(Card card) {
        entityManager.persist(card);

    }

    public Card findById(Long id) {
        return entityManager.find(Card.class, id);
    }
}
