package uy.maly.wishlist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.domain.model.Wishlist;


/**
 * @author JMaly
 * @project wishlist
 */
public interface WishlistRepo extends JpaRepository<Wishlist, Long> {

    Page<Wishlist> findAllByUser(User user, Pageable page);

}
