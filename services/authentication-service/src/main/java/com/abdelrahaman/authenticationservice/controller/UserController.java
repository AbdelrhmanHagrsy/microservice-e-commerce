package com.abdelrahaman.authenticationservice.controller;

import com.abdelrahaman.authenticationservice.dto.LogIn;
import com.abdelrahaman.authenticationservice.dto.RegistrationRequest;
import com.abdelrahaman.authenticationservice.service.AuthService;
import com.abdelrahaman.authenticationservice.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.abdelrahaman.authenticationservice.constant.Constant.ApisMapping.*;
import static com.abdelrahaman.authenticationservice.constant.Constant.ControllersMapping.USER_CONTROLLER;

@RestController
@RequestMapping(USER_CONTROLLER)
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @PostMapping(REGISTER_NEW_USER_API)
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest){
        return  ResponseEntity.ok(authService.RegisterNewUser(registrationRequest));
    }


    @GetMapping(LOGIN_API)
    public ResponseEntity<String> login(@RequestBody @Valid LogIn logIn){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logIn.userName(),logIn.password()));
        if(authentication.isAuthenticated())
            return ResponseEntity.ok(jwtService.GenerateToken(logIn.userName()));
        else
            return ResponseEntity.badRequest()
                    .body("Invalid credentials , Please verify your username and password.");
    }

    @GetMapping(CONFIRM_ACCOUNT_API)
    public ResponseEntity<String> confirmAccount(@RequestParam(name = "token") String confirmationTokenId){
        return authService.confirmUserAccount(confirmationTokenId);
    }

    @GetMapping(VALIDATE_TOKEN)
    public Boolean validateToken(@RequestParam(name = "token") String JwtToken){
        jwtService.isTokenValid(JwtToken);
        return true;
    }

}
