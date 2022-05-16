package uy.maly.wishlist.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.repo.UserRepo;

import java.util.Optional;

/**
 * @author JMaly
 * @project wishlist
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
