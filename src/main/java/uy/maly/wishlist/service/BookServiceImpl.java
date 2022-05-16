package uy.maly.wishlist.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.maly.wishlist.domain.dto.BooksApiDTO;
import uy.maly.wishlist.domain.dto.google.GoogleBooksApiDTO;
import uy.maly.wishlist.domain.dto.google.VolumeDTO;
import uy.maly.wishlist.domain.model.Book;
import uy.maly.wishlist.exception.Wishlist400Exception;
import uy.maly.wishlist.exception.Wishlist500Exception;
import uy.maly.wishlist.mapper.BookMapper;
import uy.maly.wishlist.repo.BookRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
@Transactional
public class BookServiceImpl implements BooksService {

    private final BookRepo bookRepo;
    private final GoogleBooksService googleBooksService;

    @Override
    public List<BooksApiDTO> getAllBooks(Pageable page) {
        log.info("BookServiceImpl - getAllBooks - page: {}", page.getPageNumber());
        return bookRepo.findAll(page)
                .stream().map(BookMapper.INSTANCE::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        log.info("BookServiceImpl - findById: {}", bookId);
        return bookRepo.findById(bookId);
    }

    @Override
    public List<BooksApiDTO> searchBook(String key, String inauthor, String intitle,
                                        String inpublisher, Pageable pageable) {
        log.info("BookServiceImpl - searchBook - inauthor: {} - intitle: {} - inpublisher: {} - page: {} ",
                inauthor, intitle, inpublisher, pageable);
        String errorMessage;
        if (key == null || key.isEmpty()) {
            errorMessage = "No Google api key provided";
            log.warn(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
        if (validateInauthor(inauthor) && validateIntitle(intitle) && validateInpublisher(inpublisher)) {
            errorMessage = "No search criteria";
            log.warn(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
        if (!validateInauthor(inauthor) && validateIntitle(intitle) && validateInpublisher(inpublisher)) {
            log.info("Search criteria: author");
            return existsOrCreateByGoogleID(googleBooksService.findByAuthor(key, inauthor));
        } else if (!validateInauthor(inauthor) && !validateIntitle(intitle) && validateInpublisher(inpublisher)) {
            log.info("Search criteria: author and title");
            return existsOrCreateByGoogleID(googleBooksService.findByAuthorAndTitle(key, inauthor, intitle));
        } else if (!validateInauthor(inauthor) && !validateIntitle(intitle) && !validateInpublisher(inpublisher)) {
            log.info("Search criteria: author, title and publisher");
            return existsOrCreateByGoogleID(googleBooksService.findByAuthorAndTitleAndPublisher(key, inauthor, intitle,
                    inpublisher));
        } else if (!validateInauthor(inauthor) && validateIntitle(intitle) && !validateInpublisher(inpublisher)) {
            log.info("Search criteria: author and publisher");
            return existsOrCreateByGoogleID(googleBooksService.findByAuthorAndPublisher(key, inauthor, inpublisher));
        } else if (validateInauthor(inauthor) && !validateIntitle(intitle) && validateInpublisher(inpublisher)) {
            log.info("Search criteria: title");
            return existsOrCreateByGoogleID(googleBooksService.findByTitle(key, intitle));
        } else if (validateInauthor(inauthor) && !validateIntitle(intitle) && !validateInpublisher(inpublisher)) {
            log.info("Search criteria: title and publisher");
            return existsOrCreateByGoogleID(googleBooksService.findByTitleAndPublisher(key, intitle, inpublisher));
        } else if (validateInauthor(inauthor) && validateIntitle(intitle) && !validateInpublisher(inpublisher)) {
            log.info("Search criteria: publisher");
            return existsOrCreateByGoogleID(googleBooksService.findByPublisher(key, inpublisher));
        } else {
            errorMessage = "Invalid search parameters.";
            log.error(errorMessage);
            throw new Wishlist400Exception(errorMessage);
        }
    }

    private List<BooksApiDTO> existsOrCreateByGoogleID(GoogleBooksApiDTO googleBooksApiDTO) {
        try {
            List<BooksApiDTO> books = new ArrayList<>();
            for (VolumeDTO bookDto : googleBooksApiDTO.getItems()) {
                Optional<Book> book = bookRepo.findByGbid(bookDto.getId());
                if (book.isEmpty()) {
                    log.info("Saving book, gbid: {}", bookDto.getId());
                    Book b = bookRepo.save(
                            Book.builder()
                                    .gbid(bookDto.getId())
                                    .title(bookDto.getVolumeInfo().getTitle())
                                    .publisher(bookDto.getVolumeInfo().getPublisher())
                                    .authors(bookDto.getVolumeInfo().getAuthors())
                                    .build());
                    books.add(BookMapper.INSTANCE.bookToBookDto(b));
                } else {
                    books.add(BookMapper.INSTANCE.bookToBookDto(book.get()));
                }
            }
            return books;
        } catch (Exception e) {
            String errorMessage = "Could not save book.";
            log.error(errorMessage);
            throw new Wishlist500Exception(errorMessage);
        }
    }

    private boolean validateInauthor(String inauthor) {
        return inauthor == null || inauthor.equals("");
    }

    private boolean validateIntitle(String intitle) {
        return intitle == null || intitle.equals("");
    }

    private boolean validateInpublisher(String inpublisher) {
        return inpublisher == null || inpublisher.equals("");
    }


}
