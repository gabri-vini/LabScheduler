package com.xaviervinicius.labschedule.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xaviervinicius.labschedule.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

@Service
public class JWTService {
    private final Algorithm algorithm;
    private final String issuer;

    public JWTService(Algorithm algorithm, @Value("${spring.application.name}") String issuer){
        this.algorithm = algorithm;
        this.issuer = issuer;
    }

    public String tokenize(String value, int hoursToLive){
        try{
            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(value)
                    .withExpiresAt(DateUtils.now().plus(hoursToLive, ChronoUnit.HOURS))
                    .sign(this.algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }

    public String tokenize(String value){
        try{
            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(value)
                    .sign(this.algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }

    public String decode(String token){
        try{
            return JWT.require(this.algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            throw new RuntimeException(e);
        }
    }
}
