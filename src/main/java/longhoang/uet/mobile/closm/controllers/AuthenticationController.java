package longhoang.uet.mobile.closm.controllers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.LoginResponse;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterResponse;
import longhoang.uet.mobile.closm.models.User;
import longhoang.uet.mobile.closm.services.UserDetailsImpl;
import longhoang.uet.mobile.closm.services.UserService;
import longhoang.uet.mobile.closm.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginInput input) {
        try {
            User user = userService.authenticate(input);
            String token = jwtUtil.generateToken(new UserDetailsImpl(user));
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setEmail(user.getEmail());
            return ResponseEntity.ok(loginResponse);

        }
        catch (Exception e) {
            LoginResponse response = new LoginResponse();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterInput input) {
        try {
            log.debug("Register user: {}", input);
            userService.register(input);
            return ResponseEntity.ok().body(new RegisterResponse("Dang ky thanh cong email " + input.getEmail(), null));
        } catch (Exception e) {
            RegisterResponse response = new RegisterResponse();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
