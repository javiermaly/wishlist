package uy.maly.wishlist.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;



/**
 * @author JMaly
 * @project wishlist
 */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
})
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    @NotBlank(message = "Username is mandatory")
    @Size(max = 20)
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(max = 2000)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Wishlist> wishlistSet;

    public User(String username, String encodedPassword) {
        this.username = username;
        this.password = encodedPassword;
    }

}
