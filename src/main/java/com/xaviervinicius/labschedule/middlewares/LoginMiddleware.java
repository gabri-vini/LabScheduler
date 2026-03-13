package com.xaviervinicius.labschedule.middlewares;

import com.xaviervinicius.labschedule.repository.UserRepository.UserRepository;
import com.xaviervinicius.labschedule.services.JWTService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginMiddleware extends OncePerRequestFilter implements Middleware{
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearer(request);

        if(token != null){
            String relatedEmail = jwtService.decode(token);
            UserDetails user = userRepository.findByEmail(relatedEmail).orElseThrow();

            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    @Nullable
    private String getBearer(HttpServletRequest req){
        if(req == null) return null;
        String header = req.getHeader("Authorization");
        return header != null ? header.substring(7) : null;
    }
}
