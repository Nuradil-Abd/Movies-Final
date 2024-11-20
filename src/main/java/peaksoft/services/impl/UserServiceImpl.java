package peaksoft.services.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Card;
import peaksoft.entity.User;
import peaksoft.repo.CardRepo;
import peaksoft.repo.UserRepo;
import peaksoft.services.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private CardRepo cardRepo;

//    @Override
//    public void registerUser(User user) {
//        userRepo.save(user);
//    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepo.findByEmail(email);
        if( user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void registerUser(User user) {

        if (user.getCard() != null) {
            cardRepo.save(user.getCard());
        }
        userRepo.save(user);
    }

    public void saveCard(Card card) {

        cardRepo.save(card);
    }

    @Override
    public User findById(Long userId) {
        return userRepo.findById(userId);
    }
}
