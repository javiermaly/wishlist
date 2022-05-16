package uy.maly.wishlist.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author JMaly
 * @project wishlist
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "books",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"gbid"}) },
        indexes = {
                @Index(name = "indx_title", columnList = "title"),
                @Index(name = "indx_publisher", columnList = "publisher"),
                @Index(name = "indx_publisherTitle", columnList = "publisher, title"),
                @Index(name = "indx_publisher", columnList = "publisher"),
                @Index(name = "indx_gbid", columnList = "gbid")
        }
)
public class Book {
    @Id
    @GeneratedValue
    private long id;
    private String gbid;
    @ElementCollection
    private List<String> authors;
    private String title;
    private String publisher;

}
