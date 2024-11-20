package peaksoft.services;

import peaksoft.entity.Card;
import peaksoft.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void registerUser(User user);
    User authenticate(String email, String password);


    void saveCard(Card card);

    User findById(Long userId);
}
