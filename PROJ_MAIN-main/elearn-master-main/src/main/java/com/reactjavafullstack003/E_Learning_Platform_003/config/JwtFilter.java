package com.reactjavafullstack003.E_Learning_Platform_003.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.reactjavafullstack003.E_Learning_Platform_003.service.CustomUserDetailsService;
import com.reactjavafullstack003.E_Learning_Platform_003.service.JwtService;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.List;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String token = null;
    String userName = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        userName = jwtService.extractUserName(token);
    }

    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(userName);

        if (jwtService.validateToken(token, userDetails)) {
            Claims claims = jwtService.extractAllClaims(token);
            List<GrantedAuthority> authorities = ((List<?>) claims.get("roles")).stream()
                    .map(role -> new SimpleGrantedAuthority((String) role))
                    .collect(Collectors.toList());
        
            // Extract userID or instructorID based on the role
            Integer userID = claims.get("userID", Integer.class);
            Integer instructorID = claims.get("instructorID", Integer.class);
        
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
}
}
