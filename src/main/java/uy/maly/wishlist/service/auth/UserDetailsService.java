package uy.maly.wishlist.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uy.maly.wishlist.domain.model.User;

import java.security.Principal;

/**
 * @author JMaly
 * @project wishlist
 */
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
