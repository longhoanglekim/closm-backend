package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.auth.LoginInput;
import longhoang.uet.mobile.closm.dtos.auth.RegisterInput;
import longhoang.uet.mobile.closm.models.User;
import longhoang.uet.mobile.closm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
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
        newUser.setPassword(registerInput.getPassword());
        newUser.setFullName(registerInput.getFullName());
        newUser.setPhone(registerInput.getPhone());
        return userRepository.save(newUser);
    }
}
