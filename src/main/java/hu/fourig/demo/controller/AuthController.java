package hu.fourig.demo.controller;

import hu.fourig.demo.data.LoginUserDto;
import hu.fourig.demo.data.UserDto;
import hu.fourig.demo.model.User;
import hu.fourig.demo.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;


    @Operation(summary = "Felhasználó létrehozás")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @Operation(summary = "Felhasználó bejelentkezés")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto userDto) {
        return ResponseEntity.ok(userService.loginUser(userDto));
    }
}
