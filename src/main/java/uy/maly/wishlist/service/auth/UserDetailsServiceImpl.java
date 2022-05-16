package uy.maly.wishlist.service.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.repo.UserRepo;

import javax.transaction.Transactional;

/**
 * @author JMaly
 * @project wishlist
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService  {

    UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }



}
