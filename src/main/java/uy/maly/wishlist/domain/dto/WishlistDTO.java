package uy.maly.wishlist.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.maly.wishlist.domain.model.Book;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author JMaly
 * @project wishlist
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {
    private long id;
    @NotBlank
    private String name;
    private Set<Book> books;
}
