package org.ksga.pvh_group_03_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ksga.pvh_group_03_spring_mini_project.service.OtpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of OtpService using Redis for storage and rate limiting
 * Provides secure OTP generation, verification, and rate limiting capabilities
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final RedisTemplate<String, Object> redisTemplate;

    // OTP Configuration
    private static final int OTP_LENGTH = 6;
    private static final long OTP_TTL_SECONDS = 120; // 2 minutes
    private static final String OTP_PREFIX = "otp:";
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final String ATTEMPT_PREFIX = "attempt:";

    // Rate limiting configuration - max 5 OTP requests per 60 seconds
    @Value("${otp.rate.limit.attempts:5}")
    private int maxAttempts;

    @Value("${otp.rate.limit.window:60}")
    private long rateLimitWindowSeconds;

    /**
     * Generates a random 6-digit OTP
     *
     * @return a generated random OTP as a string
     */
    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String generatedOtp = String.valueOf(otp);
        log.info("Generated OTP: {}", generatedOtp);
        return generatedOtp;
    }

    /**
     * Sends OTP to the provided email and stores it in Redis
     * Implements rate limiting to prevent OTP generation abuse
     *
     * @param email Email address to send OTP
     * @param otp   OTP code to send
     * @param ttl   Time to live in seconds
     */
    @Override
    public void sendOtp(String email, String otp, long ttl) {
        String otpKey = OTP_PREFIX + email;

        // Store the OTP in Redis with the specified TTL
        redisTemplate.opsForValue().set(otpKey, otp, ttl, TimeUnit.SECONDS);
        log.info("OTP stored in Redis for email: {}", email);

        // Increment attempt counter for rate limiting
        incrementOtpAttempt(email);

        // TODO: Integrate with email service to send OTP
        // Current implementation stores in Redis, email sending is handled
        // by SendOTPMailUtils in AuthService
        log.info("OTP ready to be sent to email: {}", email);
    }

    /**
     * Verifies the OTP sent to the provided email
     * Deletes the OTP from Redis after successful verification
     *
     * @param email Email address to verify OTP
     * @param otp   OTP code to verify
     * @return true if OTP is valid and matches, false otherwise
     */
    @Override
    public boolean verifyOtp(String email, String otp) {
        String otpKey = OTP_PREFIX + email;
        String storedOtp = (String) redisTemplate.opsForValue().get(otpKey);

        log.info("Attempting to verify OTP for email: {}", email);

        // Check if the received OTP matches the stored one
        if (storedOtp != null && storedOtp.equals(otp)) {
            // Delete the OTP from Redis after successful verification
            redisTemplate.delete(otpKey);
            // Clear rate limit counter on successful verification
            clearRateLimit(email);
            log.info("OTP verification successful for email: {}", email);
            return true;
        }

        log.warn("Invalid OTP provided for email: {}", email);
        return false;
    }

    /**
     * Checks if OTP is present and valid for the provided email
     *
     * @param email Email address to check
     * @return true if OTP exists in Redis, false otherwise
     */
    @Override
    public boolean isOtpPresent(String email) {
        String otpKey = OTP_PREFIX + email;
        return redisTemplate.hasKey(otpKey);
    }

    /**
     * Checks if the email address has exceeded the OTP generation rate limit
     *
     * @param email Email address to check rate limit
     * @return true if rate limit exceeded, false otherwise
     */
    @Override
    public boolean isRateLimited(String email) {
        String attemptKey = ATTEMPT_PREFIX + email;
        Object attempts = redisTemplate.opsForValue().get(attemptKey);

        if (attempts == null) {
            return false;
        }

        int currentAttempts = Integer.parseInt(attempts.toString());
        boolean rateLimited = currentAttempts >= maxAttempts;

        if (rateLimited) {
            log.warn("Rate limit exceeded for email: {}. Attempts: {}/{}", email, currentAttempts, maxAttempts);
        }

        return rateLimited;
    }

    /**
     * Increments the OTP generation attempt counter for rate limiting
     *
     * @param email Email address to increment counter
     */
    @Override
    public void incrementOtpAttempt(String email) {
        String attemptKey = ATTEMPT_PREFIX + email;
        Long attempts = redisTemplate.opsForValue().increment(attemptKey);

        // Set expiration time for the attempt counter on first increment
        if (attempts == 1) {
            redisTemplate.expire(attemptKey, rateLimitWindowSeconds, TimeUnit.SECONDS);
            log.info("Attempt counter initialized for email: {}", email);
        }

        log.info("OTP generation attempt #{} for email: {}", attempts, email);
    }

    /**
     * Clears the OTP for the given email address
     *
     * @param email Email address to clear OTP
     */
    @Override
    public void clearOtp(String email) {
        String otpKey = OTP_PREFIX + email;
        redisTemplate.delete(otpKey);
        log.info("OTP cleared for email: {}", email);
    }

    /**
     * Helper method to clear rate limit counter
     *
     * @param email Email address to clear rate limit
     */
    private void clearRateLimit(String email) {
        String attemptKey = ATTEMPT_PREFIX + email;
        redisTemplate.delete(attemptKey);
        log.info("Rate limit counter cleared for email: {}", email);
    }
}
