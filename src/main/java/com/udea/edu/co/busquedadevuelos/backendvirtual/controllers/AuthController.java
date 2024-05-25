package com.udea.edu.co.busquedadevuelos.backendvirtual.controllers;

import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.UserLoginDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.UserRegisterDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Credentials;
import com.udea.edu.co.busquedadevuelos.backendvirtual.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController  {

    private final AuthService authService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: implementar
        }
        this.authService.registerUser(userRegisterDto);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO: implementar
        }
        Credentials result = this.authService.authenticateUser(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping(path = "/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader Map<String, String> headers) {
        Optional<String> tokenOptional = Optional.ofNullable(headers.get("authorization"));
        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token not found");
        }
        Credentials credentials = this.authService.refreshToken(tokenOptional.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(credentials);
    }


}
