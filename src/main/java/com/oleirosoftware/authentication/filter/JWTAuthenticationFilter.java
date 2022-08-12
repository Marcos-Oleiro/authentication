package com.oleirosoftware.authentication.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleirosoftware.authentication.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String  SECRET = "SECRET_KEY";
    // public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final long    EXPIRATION_TIME = 7_776_000_000L; // 90 days 
    public static final String  TOKEN_PREFIX = "Bearer ";
    public static final String  HEADER_STRING = "Authorization";
    public static final String  SIGN_UP_URL = "/api/services/controller/user";

    AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/jwt/post");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            log.info("Usuário recebido no filtro {}", user);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            log.error("Erro no maping");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String token = JWT.create()
            .withSubject(User.class.cast(authResult.getPrincipal()).getUserName())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SECRET.getBytes()));

        String body = (User.class.cast(authResult.getPrincipal()).getUserName()) + " " + token;
        
        response.getWriter().write(body);
        response.getWriter().flush();
    }
    // public static void main(String... args) {
    //     String secret = "123@abc";
    //     Algorithm algorithm = Algorithm.HMAC512(secret);

    //     User user = new User();
    //     user.setUserName("marcos");
    //     user.setPassword("senha");

    //     String generatedToken = JWT.create()
    //             .withIssuer("Simple Solution")
    //             .withSubject(user.getUserName())
    //             .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    //             .withClaim("username", user.getUserName())
    //             .withClaim("role", "User")
    //             .sign(algorithm);

    //     System.out.println(generatedToken);
    // }

}
