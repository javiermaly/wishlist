package uy.maly.wishlist.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uy.maly.wishlist.domain.dto.google.GoogleBooksApiDTO;
import uy.maly.wishlist.exception.Wishlist500Exception;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author JMaly
 * @project wishlist
 */
@Service
@Slf4j
public class GoogleBooksServiceImpl implements GoogleBooksService {

    private final RestTemplate restTemplate;

    @Value("${maly.googlebooksapi.url}")
    private String apiBaseUrl;

    public GoogleBooksServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GoogleBooksApiDTO findByAuthor(String key, String author) {
        String searchCriteria = "+inauthor:" + author;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByAuthorAndTitle(String key, String author, String title) {
        String searchCriteria = "+inauthor:" + author + "+intitle:" + title;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByAuthorAndTitleAndPublisher(String key, String author, String title, String publisher) {
        String searchCriteria = "+inauthor:" + author + "+intitle:" + title + "+inpublisher:" + publisher;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByAuthorAndPublisher(String key, String author, String publisher) {
        String searchCriteria = "+inauthor:" + author + "+inpublisher:" + publisher;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByTitle(String key, String title) {
        String searchCriteria = "+intitle:" + title;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByTitleAndPublisher(String key, String title, String publisher) {
        String searchCriteria = "+intitle:" + title + "+inpublisher:" + publisher;
        return this.apiCall(key, searchCriteria);
    }

    @Override
    public GoogleBooksApiDTO findByPublisher(String key, String publisher) {
        String searchCriteria = "+inpublisher:" + publisher;
        return this.apiCall(key, searchCriteria);
    }


    private GoogleBooksApiDTO apiCall(String key, String queryParams) {
        String errorMessage;
        String apiUrl = UriComponentsBuilder.fromUriString(apiBaseUrl)
                .queryParam("key", key)
                .queryParam("q", queryParams)
                .queryParam("fields", "items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/publisher)")
                .build().toUri().toString();
        try {
            apiUrl = URLDecoder.decode(apiUrl, StandardCharsets.UTF_8);
            ResponseEntity<GoogleBooksApiDTO> response = restTemplate.getForEntity(apiUrl, GoogleBooksApiDTO.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                errorMessage = "Unsuccessfully call to Google Books API";
                log.error(errorMessage);
                throw new Wishlist500Exception(errorMessage);
            }
        } catch (Exception e) {
            errorMessage = e.getMessage();
            log.error(errorMessage);
            throw new Wishlist500Exception(errorMessage);
        }
    }
}
