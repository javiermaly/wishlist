package uy.maly.wishlist.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uy.maly.wishlist.domain.dto.WishlistDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author JMaly
 * @project wishlist
 */
@RequestMapping("/v1/whishlists")
public interface WishlistApi {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createWishlist(Principal principal, @RequestBody WishlistDTO dto);

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> getAllMyWishlists(Principal principal, Pageable pageable);

    @RequestMapping(value = "/{wishlistId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteWishlist(Principal principal, @PathVariable("wishlistId") Long wishlistId);

    @RequestMapping(value = "/{wishlistId}/books", method = RequestMethod.POST)
    ResponseEntity<?> addBooksToWishlist(Principal principal,
                                        @PathVariable("wishlistId") Long wishlistId,
                                        @RequestBody List<Long> booksSet);

    @RequestMapping(value = "/{wishlistId}/books", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBooksFromWishlist(Principal principal,
                                        @PathVariable("wishlistId") Long wishlistId,
                                        @RequestBody List<Long> booksSet);

    @RequestMapping(value = "/{wishlistId}", method = RequestMethod.GET)
    ResponseEntity<?> listMyWishlist(Principal principal, @PathVariable("wishlistId") Long wishlistId);
}
