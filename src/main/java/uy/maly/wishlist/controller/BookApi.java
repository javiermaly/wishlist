package uy.maly.wishlist.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author JMaly
 * @project wishlist
 */
@RequestMapping("/v1/books")
public interface BookApi {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> getAllBooks(Pageable pageable);

    @RequestMapping(value = {"/volumes"}, method = RequestMethod.GET)
    ResponseEntity<?> searchBook(@RequestParam(value = "key") String key,
                                 @RequestParam(value = "inauthor", required = false) String inauthor,
                                 @RequestParam(value = "intitle", required = false) String intitle,
                                 @RequestParam(value = "inpublisher", required = false) String inpublisher,
                                 Pageable pageable);
}
