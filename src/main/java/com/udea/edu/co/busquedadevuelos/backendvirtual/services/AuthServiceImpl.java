package com.udea.edu.co.busquedadevuelos.backendvirtual.services;

import com.udea.edu.co.busquedadevuelos.backendvirtual.config.AuthUtils;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.UserLoginDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.controllers.dto.request.UserRegisterDto;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Credentials;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.Role;
import com.udea.edu.co.busquedadevuelos.backendvirtual.domain.User;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Builder(toBuilder = true)
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthUtils authUtils;
    private final UserService userService;

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        // TODO: Verificar la no existencia por correo e identificacion
        User entity = User.builder()
                    .id(UUID.randomUUID().toString())
                    .password(this.authUtils.passwordEncoder().encode(String.valueOf(userRegisterDto.getPassword())))
                    .grantedAuthorities(Set.of(new SimpleGrantedAuthority(Role.ROLE_USER.name())))
                    .email(userRegisterDto.getEmail())
                    .active(true)
                    .createdAt(LocalDateTime.now())
                    .name(userRegisterDto.getName())
                    .identification(userRegisterDto.getIdentification())
                .build();
        User savedUser = this.userService.save(entity);
        if (savedUser == null) {
            throw new RuntimeException("No se ha podido guardar el usuario, intente nuevamente.");
        }
    }

    @Override
    public Credentials authenticateUser(UserLoginDto userLoginDto) {
        try {
            Authentication authentication =
                    this.authUtils.authManagerBuilder()
                            .getObject()
                            .authenticate(new UsernamePasswordAuthenticationToken(
                                    userLoginDto.getEmail(),
                                    userLoginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();

            return this.authUtils.jwtProvider()
                    .generateUserCredentials(user);

        } catch (DisabledException | LockedException | BadCredentialsException e1) {
            e1.printStackTrace(System.out);
            throw new RuntimeException("Usuario o contraseña incorrectos.");
        }
    }

//    @Override
//    public Void changePassword(UserChangePassCommand command) {
//        UserLoginCommand userLoginCommand = UserLoginCommand.builder()
//                .email(command.getEmail())
//                .password(command.getCurrentPassword())
//                .build();
//        Credentials> userCredentialsResponse = this.authenticateUser(userLoginCommand);
//        if (!userCredentialsResponse.getStatus().is2xxSuccessful()) {
//            return Response.<Void>builder()
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .value(null)
//                    .build();
//        }
//        User userResponse = this.userService.findByEmail(command.getEmail());
//        if (userResponse.getStatus().isError()) {
//            return Response.<Void>builder()
//                    .status(HttpStatus.NOT_FOUND)
//                    .value(null)
//                    .build();
//        }
//        User user = userResponse.getValue();
//        user.setPassword(this.authUtils.passwordEncoder().encode(command.getNewPassword()));
//        this.userService.save(user);
//        return Response.<Void>builder()
//                .status(HttpStatus.ACCEPTED)
//                .value(null)
//                .build();
//    }

    @Override
    public Credentials refreshToken(String bearerToken) {
        String token = bearerToken.substring(7);
        boolean tokenValid = this.validateToken(token);
        if (!tokenValid) {
            throw new RuntimeException("Token no válido.");
        }
        String email = this.authUtils.jwtProvider().extractSubject(token);
        Optional<User> byEmail = this.userService.findByEmail(email);

        return byEmail.map(user -> this.authUtils.jwtProvider().generateUserCredentials(user))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
    }

    private boolean validateToken(String token) {
        boolean tokenValid = this.authUtils.jwtProvider().isTokenValid(token);
        boolean tokenExpired = this.authUtils.jwtProvider().isTokenExpired(token);
        return tokenValid && !tokenExpired;
    }

}
