package org.ksga.pvh_group_03_spring_mini_project.service;

/**
 * Service interface for OTP generation, storage, and verification using Redis
 * with rate limiting capabilities
 */
public interface OtpService {

    /**
     * Generates a random 6-digit OTP
     *
     * @return a generated random OTP string
     */
    String generateOtp();

    /**
     * Sends OTP to provided email address and stores it in Redis
     * with rate limiting to prevent abuse
     *
     * @param email Email address to send OTP
     * @param otp   OTP code to send
     * @param ttl   Time to live in seconds (typically 300 for 5 minutes)
     */
    void sendOtp(String email, String otp, long ttl);

    /**
     * Verifies the OTP sent to provided email address
     *
     * @param email Email address to verify OTP
     * @param otp   OTP code to verify
     * @return true if the OTP is verified and matches, false otherwise
     */
    boolean verifyOtp(String email, String otp);

    /**
     * Checks if OTP is present for provided email address
     *
     * @param email Email address to check OTP
     * @return true if OTP is present and valid, false otherwise
     */
    boolean isOtpPresent(String email);

    /**
     * Checks if user has exceeded the OTP generation rate limit
     *
     * @param email Email address to check rate limit
     * @return true if user has exceeded the rate limit, false otherwise
     */
    boolean isRateLimited(String email);

    /**
     * Increments the OTP generation attempt counter for rate limiting
     *
     * @param email Email address to increment counter
     */
    void incrementOtpAttempt(String email);

    /**
     * Clears the OTP for a given email
     *
     * @param email Email address to clear OTP
     */
    void clearOtp(String email);
}
