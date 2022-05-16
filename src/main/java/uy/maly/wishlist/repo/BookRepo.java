package uy.maly.wishlist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uy.maly.wishlist.domain.model.Book;

import java.util.Optional;

/**
 * @author JMaly
 * @project wishlist
 */
public interface BookRepo extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable page);
    Optional<Book> findByGbid(String id);

}
