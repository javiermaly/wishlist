package uy.maly.wishlist.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author JMaly
 * @project wishlist
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BooksApiDTO {
    private String id;
    private String gbid;
    private List<String> authors;
    private String title;
    private String publisher;

}
