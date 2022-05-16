package uy.maly.wishlist.service;

import uy.maly.wishlist.domain.dto.google.GoogleBooksApiDTO;

/**
 * @author JMaly
 * @project wishlist
 */
public interface GoogleBooksService {

    GoogleBooksApiDTO findByAuthor(String key, String author);
    GoogleBooksApiDTO findByAuthorAndTitle(String key, String author, String title);
    GoogleBooksApiDTO findByAuthorAndTitleAndPublisher(String key, String author, String title, String publisher);
    GoogleBooksApiDTO findByAuthorAndPublisher(String key, String author, String publisher);
    GoogleBooksApiDTO findByTitle(String key, String title);
    GoogleBooksApiDTO findByTitleAndPublisher(String key, String title, String publisher);
    GoogleBooksApiDTO findByPublisher(String key, String publisher);

}
