package uy.maly.wishlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uy.maly.wishlist.domain.dto.auth.LoginRequest;
import uy.maly.wishlist.domain.dto.auth.SignupRequest;

import javax.validation.Valid;

/**
 * @author JMaly
 * @project wishlist
 */
@RequestMapping("/v1/auth")
public interface AuthApi {

    @PostMapping("/signin")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @PostMapping("/signup")
     ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest);

}
