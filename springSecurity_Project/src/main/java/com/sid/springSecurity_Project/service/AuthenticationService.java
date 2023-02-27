package com.sid.springSecurity_Project.service;


import com.sid.springSecurity_Project.config.JwtService;
import com.sid.springSecurity_Project.controller.AuthenticationRequest;
import com.sid.springSecurity_Project.controller.AuthenticationResponse;
import com.sid.springSecurity_Project.controller.RegisterRequest;
import com.sid.springSecurity_Project.entity.Role;
import com.sid.springSecurity_Project.entity.User;
import com.sid.springSecurity_Project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
    /**
     *This method will allow us to create a user
     *  save it to the database
     *  return the generated token
     * */
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode((request.getPassword())))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            /**Now the user is already authenticated****/

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return  AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .build();
    }

}
