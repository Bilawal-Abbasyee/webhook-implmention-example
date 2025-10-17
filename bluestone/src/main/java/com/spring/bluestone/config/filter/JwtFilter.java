package com.spring.bluestone.config.filter;

import com.spring.bluestone.config.utils.JwtUtil;
import com.spring.bluestone.entity.UserSession;
import com.spring.bluestone.repo.UserSessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil; // Assume you have a JwtUtil class for JWT operations
    private final UserSessionRepository userSessionRepository;
    public JwtFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil, UserSessionRepository userSessionRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userSessionRepository = userSessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token); // Implement this method to extract username from token
        }

        Optional<UserSession> sessionOpt = userSessionRepository.findByTokenAndIsActiveFalse(token);
        if (sessionOpt.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        if(username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(Boolean.TRUE.equals(jwtUtil.validateToken(token))){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                auth.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(request, response);
    }
}
