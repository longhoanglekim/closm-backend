package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.dtos.mappers.UserInfoDTO;
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
    private UserRepository userRepository;

    public UserInfoDTO getUserDTO(String email) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return UserMapper.userToUserDTO(user);
        }
        throw new Exception("User not found");
    }

    public Optional<User> getUserByEmail(String email) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            return userOpt;
        }
        throw new Exception("User not found");
    }
}
