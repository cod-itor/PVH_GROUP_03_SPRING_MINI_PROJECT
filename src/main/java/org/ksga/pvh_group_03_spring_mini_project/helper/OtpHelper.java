package org.ksga.pvh_group_03_spring_mini_project.helper;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpHelper {

    private static final Integer EXPIRE_MIN = 2;
    private final LoadingCache<String, String> otpCache;

    public OtpHelper() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return "";
                    }
                });
    }

    public Integer generateOTP(String key) {
        Random random = new Random();
        Integer OTP = 100000 + random.nextInt(900000);

        otpCache.put(key, OTP.toString());

        return OTP;
    }

    public String getOPTByKey(String key) {
        return otpCache.getIfPresent(key);
    }

    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }

    public Boolean  validateOTP(String key, String otpNumber) {
        // get OTP from cache
        String cacheOTP = getOPTByKey(key);
        if (cacheOTP != null && cacheOTP.equals(otpNumber)) {
            clearOTPFromCache(key);
            return true;
        }
        return false;
    }
}