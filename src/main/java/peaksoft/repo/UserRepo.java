package peaksoft.repo;

import peaksoft.entity.User;

import java.util.List;

public interface UserRepo {
    List<User> getAllUsers();

    User save(User user);

    User findByEmail(String email);

    User findById(Long userId);
}
