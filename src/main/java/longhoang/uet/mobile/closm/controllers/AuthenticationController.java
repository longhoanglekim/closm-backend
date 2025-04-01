package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.LoginResponse;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterResponse;
import longhoang.uet.mobile.closm.models.User;
import longhoang.uet.mobile.closm.services.UserService;
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
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginInput input) {
        try {
            Optional<User> user = userService.login(input);
            if (user.isPresent()) {
                String token = UUID.randomUUID().toString();
                return ResponseEntity.ok(new LoginResponse(token, Long.getLong("3600000"), null));
            }
            throw new Exception("Invalid username or password");
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
            userService.register(input);
            return ResponseEntity.ok().body(new RegisterResponse("Dang ky thanh cong email " + input.getEmail(), null));
        } catch (Exception e) {
            RegisterResponse response = new RegisterResponse();
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
