package com.traders.backend.security.filters;

import com.traders.backend.security.validators.JwtBasicValidator;
import com.traders.backend.security.utilities.JwtResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtResolver jwtResolver;

    @Autowired
    private JwtBasicValidator jwtBasicValidator;

    public JwtBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = jwtResolver.getToken(req);
        if(token != null){ // there is a token
            String username = jwtResolver.getUsernameFromToken(token);
            if(jwtBasicValidator.validateToken(token, username)){ // is token valid
                Authentication authentication = jwtResolver.getAuthentication(username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }

}
