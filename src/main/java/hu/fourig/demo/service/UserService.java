package hu.fourig.demo.service;

import hu.fourig.demo.data.AuthResponse;
import hu.fourig.demo.data.LoginUserDto;
import hu.fourig.demo.data.UserDto;
import hu.fourig.demo.model.User;
import hu.fourig.demo.repository.UserRepository;
import hu.fourig.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public User registerUser(UserDto userDto) {
        if (findByUsername(userDto.username()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }
        String encodedPassword = passwordEncoder.encode(userDto.password());
        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(encodedPassword);
        user.setRole(userDto.role());
        return userRepository.save(user);
    }

    public AuthResponse loginUser(LoginUserDto userDto) {
        return findByUsername(userDto.username())
                .filter(user -> passwordEncoder.matches(userDto.password(), user.getPassword()))
                .map(user -> new AuthResponse(jwtUtil.generateToken(user.getUsername(), user.getRole())))
                .orElse(new AuthResponse("Invalid username or password!"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}