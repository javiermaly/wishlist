package uy.maly.wishlist.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uy.maly.wishlist.domain.dto.auth.MessageResponse;
import uy.maly.wishlist.exception.Wishlist400Exception;
import uy.maly.wishlist.exception.Wishlist404Exception;
import uy.maly.wishlist.exception.Wishlist500Exception;
import uy.maly.wishlist.service.BooksService;


/**
 * @author JMaly
 * @project wishlist
 */
@RestController
@Slf4j
@AllArgsConstructor
public class BookApiImpl implements BookApi {
    private BooksService booksService;

    @Override
    public ResponseEntity<?> getAllBooks(Pageable page) {
        return new ResponseEntity<>(booksService.getAllBooks(page), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchBook(String key, String inauthor, String intitle,
                                        String inpublisher, Pageable pageable) {
        try {
            return new ResponseEntity<>(booksService.searchBook(key, inauthor, intitle, inpublisher, pageable),
                    HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (
                Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (
                Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
