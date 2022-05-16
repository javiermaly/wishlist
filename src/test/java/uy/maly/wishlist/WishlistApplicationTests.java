package uy.maly.wishlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uy.maly.wishlist.controller.AuthApi;
import uy.maly.wishlist.controller.BookApi;
import uy.maly.wishlist.domain.dto.auth.LoginRequest;
import uy.maly.wishlist.domain.dto.auth.SignupRequest;
import uy.maly.wishlist.domain.model.User;
import uy.maly.wishlist.repo.UserRepo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class WishlistApplicationTests {

    @Mock
    private UserRepo userRepo;
    @Mock
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthApi authApi;
    @Autowired
    private BookApi bookApi;

    @Autowired
    private PasswordEncoder passwdEnconder;

    private static final Pageable pageable = PageRequest.of(0, 20);

    private static final String API_KEY = "AIzaSyDYk3-lNMQynqmE_sz5-Ebn2T5EYa_s4n8";


    @Test
    void contextLoads() {
    }

    // AuthApi Tests
    @Test
    public void shouldFailOnRegister(){
        SignupRequest sr = SignupRequest.builder().username("").password("").build();
        User user = new User(sr.getUsername(),
                passwdEnconder.encode(sr.getPassword()));
        lenient().when(userRepo.save(user)).thenReturn(user);
        assertTrue(authApi.registerUser(sr).getStatusCode()
                .is4xxClientError());
    }

    @Test
    public void shouldSuccessOnRegister(){
        SignupRequest sr = SignupRequest.builder().username("javiermaly").password("passwd").build();
        User user = new User(sr.getUsername(),
                passwdEnconder.encode(sr.getPassword()));
        lenient().when(userRepo.save(user)).thenReturn(user);
        assertTrue(authApi.registerUser(sr).getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    public void shouldFailOnLogin(){
        LoginRequest lr = LoginRequest.builder().username("").password("").build();
        assertTrue(authApi.authenticateUser(lr).getStatusCode()
                .is4xxClientError());
    }

    @Test
    public void shouldSuccessOnLogin(){
        SignupRequest loginRequest = SignupRequest.builder().username("JAVIER").password("passwd").build();
        Authentication authentication = null;
        lenient().when(authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                loginRequest.getPassword()))).thenReturn(authentication);


        LoginRequest lr = LoginRequest.builder().username("JAVIER").password("passwd").build();

    }
    // AuthApi Tests end

    // BookApi Tests
    @Test
    public void shouldSuccessOnBookList(){
        assertTrue(bookApi.getAllBooks(pageable).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void searchBookListNoApiKey(){
        assertTrue(bookApi.searchBook("","Java","","",pageable)
                .getStatusCode().is4xxClientError());
    }

    @Test
    public void searchBookListInvalidParameters(){
        assertTrue(bookApi.searchBook(API_KEY,"","","",pageable)
                .getStatusCode().is4xxClientError());
    }

    @Test
    public void searchBookListByIntitle(){
        ResponseEntity<?> response = bookApi.searchBook(API_KEY,
                "", "Java", "", pageable);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void searchBookListByAuthor(){
        ResponseEntity<?> response = bookApi.searchBook(API_KEY,
                "Sonmez", "", "", pageable);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void searchBookListByPublisher(){
        ResponseEntity<?> response = bookApi.searchBook(API_KEY,
                "", "", "0'reilly", pageable);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    // BookApi Tests end


    // WishlistApi Tests
        //TODO:   WishlistApi Tests
    // WishlistApi Tests end

}
