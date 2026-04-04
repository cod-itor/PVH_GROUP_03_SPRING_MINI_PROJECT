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
import org.ksga.pvh_group_03_spring_mini_project.service.OtpService;
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
    private final OtpService otpService;

    @Override
    public AppUserResponse register(AppUserRequest appUserRequest) {
        //Check if username or email already exist
        AppUser findByUsername = appUserRepository.findUserByIdentifier(appUserRequest.getUsername());
        AppUser findByEmail = appUserRepository.findUserByIdentifier(appUserRequest.getEmail());

        if (findByUsername != null || findByEmail != null) {
            throw new UserAlreadyExistException("Username or Email already exist!");
        }

        // Check rate limiting before generating OTP
        if (otpService.isRateLimited(appUserRequest.getEmail())) {
            throw new RuntimeException("Too many OTP requests. Please try again later.");
        }

//        Encode Password before registering
        String encodedPassword = passwordEncoder.encode(appUserRequest.getPassword());
        appUserRequest.setPassword(encodedPassword);

        AppUser registeredAppUser = appUserRepository.registerAppUser(appUserRequest);

        //Sending OTP after register using Redis-based OTP service
        String generatedOtp = otpService.generateOtp();
        otpService.sendOtp(appUserRequest.getEmail(), generatedOtp, 120);

        try {
            sendOTPMailUtils.sendOtpEmail(appUserRequest.getEmail(), Integer.parseInt(generatedOtp));
            log.info("OTP sent successfully to email: {}", appUserRequest.getEmail());
        } catch (MessagingException e) {
            log.error("Error sending OTP email: {}", e.getMessage());
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
        log.info("Attempting OTP verification for email: {}", email);

        if (!otpService.verifyOtp(email, otpCode)) {
            throw new NotYetVerifiedException("The OTP you've entered is invalid or has expired. Please request a new OTP code and try again.");
        }

        appUserRepository.updateVerifyAppUser(email, true);
        log.info("User email verified successfully: {}", email);
    }

    @Override
    public void resendOTP(String email) {
        log.info("Resending OTP to email: {}", email);

        // Check rate limiting before generating new OTP
        if (otpService.isRateLimited(email)) {
            throw new RuntimeException("Too many OTP requests. Please try again later.");
        }

        otpService.clearOtp(email);
        String generatedOtp = otpService.generateOtp();
        otpService.sendOtp(email, generatedOtp, 120);

        try {
            sendOTPMailUtils.sendOtpEmail(email, Integer.parseInt(generatedOtp));
            log.info("OTP resent successfully to email: {}", email);
        } catch (MessagingException e) {
            log.error("Error resending OTP email: {}", e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}