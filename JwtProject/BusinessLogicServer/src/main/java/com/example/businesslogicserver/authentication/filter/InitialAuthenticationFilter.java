package com.example.businesslogicserver.authentication.filter;

import com.example.businesslogicserver.authentication.OtpAuthentication;
import com.example.businesslogicserver.authentication.UsernamePasswordAuthentication;
import com.example.businesslogicserver.authentication.provider.OtpAuthenticationProvider;
import com.example.businesslogicserver.authentication.provider.UsernamePasswordAuthenticationProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    private AuthenticationManager getManager() {
        return new ProviderManager(usernamePasswordAuthenticationProvider, otpAuthenticationProvider
        );
    }

    @Value("${jwt.sign.key}")
    private String signKey;

    private final long JWT_EXPIRATION = 7 * 24 * 60 * 60 * 100; // 1 tuan
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        AuthenticationManager manager = getManager();

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + JWT_EXPIRATION);

        if(code == null) {
            Authentication a = new UsernamePasswordAuthentication(username, password); //chưa xác thực
            manager.authenticate(a);
        } else {
            Authentication a = new OtpAuthentication(username, code);
            manager.authenticate(a);

            SecretKey key = Keys.hmacShaKeyFor(signKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .setClaims(Map.of("username", username))
                    .setIssuedAt(new Date())
                    .setExpiration(expiredDate)
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
