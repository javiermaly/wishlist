package uy.maly.wishlist.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @author JMaly
 * @project wishlist
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @ManyToMany
    private Set<Book> books;
    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;
}
