package uy.maly.wishlist.service;

import org.springframework.data.domain.Pageable;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.dto.WishlistDTO;

import java.security.Principal;
import java.util.List;

/**
 * @author JMaly
 * @project wishlist
 */

public interface WishlistService {
    void deleteWishList(Long id, Principal principal);
    void createWishList(String name, Principal principal);
    List<WishlistDTO> getAllMyWishlists(Principal principal, Pageable pageable);
    void addBookToWishlist(Principal principal, Long wishlistId, List<Long> booksSet);
    void deleteBooksFromWishlist(Principal principal, Long wishlistId, List<Long> booksSet);
    List<BooksApiDTO> listMyWishlist(Principal principal, Long wishlistId);
}
