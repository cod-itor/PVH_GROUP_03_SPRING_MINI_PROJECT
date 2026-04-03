package org.ksga.pvh_group_03_spring_mini_project.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ksga.pvh_group_03_spring_mini_project.exception.InvalidTokenException;
import org.ksga.pvh_group_03_spring_mini_project.service.AppUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AppUserService appUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extracting the token from the Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Validate the token and set the authentication in the security context
            try {

                // Extract the identifier (e.g., username) from the token
                String Identifier = jwtUtils.extractIdentifier(token);

                // Check if the identifier is not null and there is no existing authentication in the security context
                if (Identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = appUserService.loadUserByUsername(Identifier);


                    if (jwtUtils.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()

                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                            throw new InvalidTokenException("Authentication required or token is invalid");
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}

