package com.urbanlegends.auth;

import com.urbanlegends.errors.ApiError;
import com.urbanlegends.shared.CurrentUser;
import com.urbanlegends.user.User;
import com.urbanlegends.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;


@RestController
public class AuthController {

    UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @PostMapping("/api/1.0/auth")
    public ResponseEntity<?> handleAıthentication(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }

}
