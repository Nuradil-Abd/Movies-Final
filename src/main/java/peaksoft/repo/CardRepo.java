package peaksoft.repo;


import peaksoft.entity.Card;

public interface CardRepo {
    void save(Card card);
    Card findById(Long id);
}
