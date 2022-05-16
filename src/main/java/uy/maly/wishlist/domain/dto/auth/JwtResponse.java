package uy.maly.wishlist.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author JMaly
 * @project wishlist
 */
@Data
public class JwtResponse {
    private String token;
    private static final String type = "Bearer";


    public JwtResponse(String jwt) {
        this.token = jwt;
    }
}
