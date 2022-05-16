package uy.maly.wishlist.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uy.maly.wishlist.domain.dto.WishlistDTO;
import uy.maly.wishlist.domain.dto.auth.MessageResponse;
import uy.maly.wishlist.exception.Wishlist400Exception;
import uy.maly.wishlist.exception.Wishlist404Exception;
import uy.maly.wishlist.exception.Wishlist500Exception;
import uy.maly.wishlist.service.WishlistService;

import java.security.Principal;
import java.util.List;

/**
 * @author JMaly
 * @project wishlist
 */
@RestController
@Slf4j
@AllArgsConstructor
public class WishlistApiImpl implements WishlistApi {

    private WishlistService wishlistService;

    @Override
    public ResponseEntity<?> createWishlist(Principal principal, WishlistDTO dto) {
        try {
            wishlistService.createWishList(dto.getName(), principal);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> getAllMyWishlists(Principal principal, Pageable page) {
        try {
            return new ResponseEntity<>(wishlistService.getAllMyWishlists(principal, page), HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteWishlist(Principal principal, Long id) {
        try {
            wishlistService.deleteWishList(id, principal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> addBooksToWishlist(Principal principal, Long wishlistId, List<Long> booksSet) {
        try {
            wishlistService.addBookToWishlist(principal, wishlistId, booksSet);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> deleteBooksFromWishlist(Principal principal, Long wishlistId, List<Long> booksSet) {
        try {
            wishlistService.deleteBooksFromWishlist(principal, wishlistId, booksSet);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> listMyWishlist(Principal principal, Long wishlistId) {
        try {
            return new ResponseEntity<>(wishlistService.listMyWishlist(principal,wishlistId), HttpStatus.OK);
        } catch (Wishlist404Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Wishlist400Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Wishlist500Exception ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
