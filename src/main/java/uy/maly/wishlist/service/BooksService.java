package uy.maly.wishlist.service;

import org.springframework.data.domain.Pageable;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.model.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author JMaly
 * @project wishlist
 */
public interface BooksService {
     List<BooksApiDTO> getAllBooks(Pageable page);
     Optional<Book> findById(Long bookId);
     List<BooksApiDTO> searchBook(String key, String intitle, String inauthor, String inpublisher, Pageable pageable);
}
