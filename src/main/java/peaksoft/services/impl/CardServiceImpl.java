package peaksoft.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Card;
import peaksoft.repo.CardRepo;
import peaksoft.services.CardService;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepo cardRepo;

    @Override
    public void saveCard(Card card) {
        cardRepo.save(card);
    }


}
