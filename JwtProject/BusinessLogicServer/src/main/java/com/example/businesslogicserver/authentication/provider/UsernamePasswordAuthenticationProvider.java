package com.example.businesslogicserver.authentication.provider;

import com.example.businesslogicserver.authentication.UsernamePasswordAuthentication;
import com.example.businesslogicserver.authentication.proxy.AuthenticationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AuthenticationProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        proxy.sendAuth(username, password);
        return new UsernamePasswordAuthentication(username, password); //return cái này chưa xác thực (cái 3 param mới xác thực r)
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
    }
}
