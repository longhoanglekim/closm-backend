package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.models.User;
import longhoang.uet.mobile.closm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterInput registerInput)  throws Exception {
        Optional<User> user = userRepository.findByEmail(registerInput.getEmail());
        if (user.isPresent()) {
            throw new Exception("User already exists");
        }
        User newUser = new User();
        newUser.setEmail(registerInput.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerInput.getPassword()));
        newUser.setFullName(registerInput.getFullName());
        newUser.setPhone(registerInput.getPhone());
        return userRepository.save(newUser);
    }
    public User authenticate(LoginInput loginInput) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInput.getEmail(), loginInput.getPassword()));
        if (authentication.isAuthenticated()) {
            return userRepository.findByEmail(loginInput.getEmail()).get();
        }
        throw new BadCredentialsException("Invalid email or password");
    }

    public User authenticateAdmin(LoginInput loginInput) throws BadCredentialsException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginInput.getEmail(), loginInput.getPassword()));
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid email or password");

        }
        User user = userRepository.findByEmail(loginInput.getEmail()).get();
        if (user.getRole().equals("ROLE_USER"))
            throw new BadCredentialsException("Invalid email or password");
        return userRepository.findByEmail(loginInput.getEmail()).get();
    }
}
