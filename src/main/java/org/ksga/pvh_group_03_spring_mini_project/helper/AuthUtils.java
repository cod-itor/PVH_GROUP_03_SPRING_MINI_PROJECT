package org.ksga.pvh_group_03_spring_mini_project.helper;

import org.ksga.pvh_group_03_spring_mini_project.exception.InvalidTokenException;
import org.ksga.pvh_group_03_spring_mini_project.model.entity.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtils {
    public UUID getCurrentUserIdentifier() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            AppUser appUser = (AppUser) authentication.getPrincipal();
            return appUser.getAppUserId();
        } catch (Exception e) {
            throw new InvalidTokenException("Authentication required or token is invalid");
        }

    }
}
