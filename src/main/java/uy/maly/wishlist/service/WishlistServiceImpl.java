package uy.maly.wishlist.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.dto.WishlistDTO;
import uy.maly.wishlist.domain.model.Book;
import uy.maly.wishlist.domain.model.Wishlist;
import uy.maly.wishlist.exception.Wishlist400Exception;
import uy.maly.wishlist.exception.Wishlist404Exception;
import uy.maly.wishlist.exception.Wishlist500Exception;
import uy.maly.wishlist.mapper.BookMapper;
import uy.maly.wishlist.mapper.WishlistMapper;
import uy.maly.wishlist.repo.WishlistRepo;
import uy.maly.wishlist.util.UserUtils;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author JMaly
 * @project wishlist
 */
@Service
@AllArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepo wishlistRepo;
    private final UserUtils userUtils;
    private final BooksService booksService;

    @Override
    @Transactional
    public void deleteWishList(Long id, Principal principal) {
        log.info("deleteWishList - id: {} - user: {}", id, principal.getName());
        String errorMessage;
        Optional<Wishlist> wl = wishlistRepo.findById(id);
        if (wl.isEmpty()) {
            errorMessage = "Wishlist not found.";
            log.warn(errorMessage);
            throw new Wishlist404Exception(errorMessage);
        }
        if (wl.get().getUser().getUserId().equals(userUtils.getUserFromPrincipal(principal).getUserId())) {
            wishlistRepo.delete(wl.get());
        } else {
            errorMessage = "Wishlist does not correspond to the user";
            log.warn(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
    }

    @Override
    @Transactional
    public void createWishList(String name, Principal principal) {
        log.info("createWishList - name: {} - user: {}", name, principal.getName());
        String errorMessage;
        try {
            Wishlist wl = Wishlist.builder().name(name).user(userUtils.getUserFromPrincipal(principal)).build();
            wishlistRepo.save(wl);
        } catch (Exception e) {
            errorMessage = "Error persisting entity: " + e.getMessage();
            log.error(errorMessage);
            throw new Wishlist500Exception(errorMessage);
        }
    }

    @Override
    public List<WishlistDTO> getAllMyWishlists(Principal principal, Pageable page) {
        log.info("getAllMyWishlists - user: {} - page: {} ", principal.getName(), page.getPageNumber());
        return wishlistRepo.findAllByUser(userUtils.getUserFromPrincipal(principal), page)
                .stream().map(WishlistMapper.INSTANCE::wishlistToWishlistDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addBookToWishlist(Principal principal, Long wishlistId, List<Long> booksSet) {
        log.info("addBookToWishlist - wishlistId: {} - user: {} - bookSet: {}", wishlistId, principal.getName(),
                booksSet);
        validateArrayOfBooks(booksSet);
        Wishlist wl = findWishlist(wishlistId);
        validateUserFromWishlist(wl, principal);
        booksSet.forEach(bookId -> {
            String errorMessage;
            Optional<Book> book = booksService.findById(bookId);
            if (book.isEmpty()) {
                errorMessage = "Invalid bookId: " + bookId;
                log.warn(errorMessage);
                throw new Wishlist400Exception(errorMessage);
            }
            wl.getBooks().add(book.get());
        });
        try {
            wishlistRepo.save(wl);
        } catch (Exception e) {
            String errorMessage = "Error while persisting books on wishlist: " + e.getMessage();
            log.error(errorMessage);
            throw new Wishlist500Exception(errorMessage);
        }
    }

    @Override
    @Transactional
    public void deleteBooksFromWishlist(Principal principal, Long wishlistId, List<Long> booksSet) {
        log.info("deleteBooksFromWishlist - wishlistId: {} - user: {} - bookSet: {}", wishlistId, principal.getName(),
                booksSet);
        validateArrayOfBooks(booksSet);
        Wishlist wl = findWishlist(wishlistId);
        validateUserFromWishlist(wl, principal);

        booksSet.forEach(bookId -> {
            String errorMessage;
            Optional<Book> book = booksService.findById(bookId);
            if (book.isEmpty()) {
                errorMessage = "Invalid bookId: " + bookId;
                log.warn(errorMessage);
                throw new Wishlist400Exception(errorMessage);
            }
            if(wl.getBooks().contains(book.get())){
                wl.getBooks().remove(book.get());
            }else {
                errorMessage = "Book not in wishlist: " + bookId;
                log.warn(errorMessage);
                throw new Wishlist400Exception(errorMessage);
            }
        });
        try {
            wishlistRepo.save(wl);
        } catch (Exception e) {
            String errorMessage = "Error while removing books from wishlist";
            log.error(errorMessage);
            throw new Wishlist500Exception(errorMessage);
        }
    }

    @Override
    public List<BooksApiDTO> listMyWishlist(Principal principal, Long wishlistId) {
        Wishlist wl = findWishlist(wishlistId);
        validateUserFromWishlist(wl, principal);
        return wl.getBooks().stream().map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
    }

    private Wishlist findWishlist(Long wishlistId){
        Optional<Wishlist> wl = wishlistRepo.findById(wishlistId);
        String errorMessage;
        if (wl.isEmpty()) {
            errorMessage = "Wishlist not found.";
            log.warn(errorMessage);
            throw new Wishlist404Exception(errorMessage);
        }
        return wl.get();
    }

    private void validateUserFromWishlist(Wishlist wl, Principal principal){
        if (!wl.getUser().getUserId().equals(userUtils.getUserFromPrincipal(principal).getUserId())) {
            String errorMessage= "Wishlist does not correspond to the user.";
            log.warn(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
    }

    private void validateArrayOfBooks(List<Long> booksSet){
        if(booksSet.isEmpty()) {
            String errorMessage = "Empty books list.";
            log.warn(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
    }
}
