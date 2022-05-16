package uy.maly.wishlist.service;

import uy.maly.wishlist.domain.model.User;

import java.util.Optional;

/**
 * @author JMaly
 * @project wishlist
 */
public interface UserService {
    Optional<User> findUserByUsername(String username);
}
