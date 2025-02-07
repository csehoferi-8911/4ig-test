package hu.fourig.demo.controller;

import hu.fourig.demo.data.LoginUserDto;
import hu.fourig.demo.data.UserDto;
import hu.fourig.demo.model.User;
import hu.fourig.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto userDto) {
        return ResponseEntity.ok(userService.loginUser(userDto));
    }
}
