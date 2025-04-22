package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.dtos.response.UserInfoDTO;
import longhoang.uet.mobile.closm.mappers.UserMapper;
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
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public Optional<User> login(LoginInput loginInput) throws Exception {
        Optional<User> user = userRepository.findByEmailAndPassword(loginInput.getEmail(), loginInput.getPassword());
        if (user.isPresent()) {
            return user;
        }
        throw new Exception("Invalid email or password");
    }

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

    public UserInfoDTO getUserDTO(String email) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return UserMapper.userToUserDTO(user);
        }
        throw new Exception("User not found");
    }

    public Optional<User> getUser(String email) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            return userOpt;
        }
        throw new Exception("User not found");
    }
}
