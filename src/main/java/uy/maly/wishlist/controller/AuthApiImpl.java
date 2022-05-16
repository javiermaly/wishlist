package uy.maly.wishlist.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uy.maly.wishlist.auth.JwtUtils;
import uy.maly.wishlist.domain.dto.auth.JwtResponse;
import uy.maly.wishlist.domain.dto.auth.LoginRequest;
import uy.maly.wishlist.domain.dto.auth.MessageResponse;
import uy.maly.wishlist.domain.dto.auth.SignupRequest;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.exception.Wishlist400Exception;
import uy.maly.wishlist.repo.UserRepo;

import javax.validation.Valid;

/**
 * @author JMaly
 * @project wishlist
 */
@Slf4j
@RestController
@AllArgsConstructor
public class AuthApiImpl implements AuthApi {

    AuthenticationManager authenticationManager;
    UserRepo userRepo;
    PasswordEncoder passwdEnconder;
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt));
        }catch(BadCredentialsException bce){
            return new ResponseEntity<>(new MessageResponse("Invalid username/password"), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if(signUpRequest.getUsername() == null || signUpRequest.getPassword() == null
            ||signUpRequest.getPassword().equals("") || signUpRequest.getUsername().equals("")){
            return new ResponseEntity<>(new MessageResponse("Invalid username/password"), HttpStatus.BAD_REQUEST);
        }

        if (userRepo.existsByUsername(signUpRequest.getUsername())) {
            String errorMessage = "Error: Username is already taken!";
            log.warn(errorMessage);
            return new ResponseEntity<>(new MessageResponse(errorMessage), HttpStatus.CONFLICT);
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                passwdEnconder.encode(signUpRequest.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
