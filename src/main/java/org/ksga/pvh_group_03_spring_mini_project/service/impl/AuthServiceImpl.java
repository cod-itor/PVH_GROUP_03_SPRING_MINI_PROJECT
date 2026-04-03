package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ksga.pvh_group_03_spring_mini_project.exception.NotYetVerifiedException;
import org.ksga.pvh_group_03_spring_mini_project.exception.UserAlreadyExistException;
import org.ksga.pvh_group_03_spring_mini_project.helper.OtpHelper;
import org.ksga.pvh_group_03_spring_mini_project.helper.SendOTPMailUtils;
import org.ksga.pvh_group_03_spring_mini_project.jwt.JwtUtils;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AppUserRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.request.AuthRequest;
import org.ksga.pvh_group_03_spring_mini_project.model.response.AppUserResponse;
import org.ksga.pvh_group_03_spring_mini_project.model.response.TokenResponse;
import org.ksga.pvh_group_03_spring_mini_project.repository.AppUserRepository;
import org.ksga.pvh_group_03_spring_mini_project.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SendOTPMailUtils sendOTPMailUtils;
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final OtpHelper otpHelper;

    @Override
    public AppUserResponse register(AppUserRequest appUserRequest) {
        //Check if username or email already exist
        AppUser findByUsername = appUserRepository.findUserByIdentifier(appUserRequest.getUsername());
        AppUser findByEmail = appUserRepository.findUserByIdentifier(appUserRequest.getEmail());

        if (findByUsername != null || findByEmail != null) {
            throw new UserAlreadyExistException("Username or Email already exist!");
        }

//        Encode Password before registering
        String encodedPassword = passwordEncoder.encode(appUserRequest.getPassword());
        appUserRequest.setPassword(encodedPassword);

        AppUser registeredAppUser = appUserRepository.registerAppUser(appUserRequest);

        //Sending OTP after register
        Integer otpCode = otpHelper.generateOTP(appUserRequest.getEmail());
        try {
            sendOTPMailUtils.sendOtpEmail(appUserRequest.getEmail(), otpCode);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
        return modelMapper.map(registeredAppUser, AppUserResponse.class);
    }

    @Override
    public TokenResponse login(AuthRequest authRequest) {
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getIdentifier(), authRequest.getPassword()
            ));

            System.out.println(auth);


            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println();
            System.out.println("NO: -> " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            System.out.println();

            AppUser appUser = appUserRepository.findUserByIdentifier(authRequest.getIdentifier());

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("AppUserUUID", appUser.getAppUserId());
            extraClaims.put("username", appUser.getUsername());
            extraClaims.put("email", appUser.getEmail());
            extraClaims.put("is_verified", appUser.getIsVerified());

//Check for verification before login
            if (!appUser.getIsVerified()) {
                throw new NotYetVerifiedException("Your email address is not yet verified. Please verify your email address before logging in.");
            }

            return new TokenResponse(jwtUtils.generateToken(extraClaims, authRequest.getIdentifier()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void verifyUser(String email, String otpCode) {
        System.out.println("OTP code: " + otpCode);
        if (!otpHelper.validateOTP(email, otpCode)) {
            throw new NotYetVerifiedException("The OTP you've entered is invalid or has expired. Please request a new OTP code and try again.");
        }
            appUserRepository.updateVerifyAppUser(email, true);
    }

    @Override
    public void resendOTP(String email) {
        otpHelper.clearOTPFromCache(email);
        Integer otpCode = otpHelper.generateOTP(email);
        try {
            sendOTPMailUtils.sendOtpEmail(email, otpCode);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
