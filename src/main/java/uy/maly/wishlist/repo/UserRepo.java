package uy.maly.wishlist.repo;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uy.maly.wishlist.domain.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * @author JMaly
 * @project wishlist
 */
@Repository
public interface UserRepo extends CrudRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

}