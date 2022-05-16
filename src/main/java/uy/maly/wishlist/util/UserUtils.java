package uy.maly.wishlist.util;

import org.springframework.stereotype.Component;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.exception.Wishlist500Exception;
import uy.maly.wishlist.service.UserService;

import java.security.Principal;
import java.util.Optional;

/**
 * @author JMaly
 * @project wishlist
 */
@Component
public class UserUtils {

    private final UserService userService;

    public UserUtils(UserService userService){
        this.userService = userService;
    }

    public User getUserFromPrincipal(Principal principal){
        Optional<User> user = userService.findUserByUsername(principal.getName());
        if(user.isEmpty()){
            throw new Wishlist500Exception("Error fetching user from db");
        }
        return user.get();
    }
}
