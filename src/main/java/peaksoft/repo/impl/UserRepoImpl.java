package peaksoft.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.entity.User;
import peaksoft.repo.UserRepo;

import java.util.List;

@Repository
@Transactional

public class UserRepoImpl implements UserRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        try{
            return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
        }catch(Exception e){
            return null;
        }
    }


    @Override
    public User findById(Long userId) {
        return entityManager.find(User.class, userId);
    }
}
