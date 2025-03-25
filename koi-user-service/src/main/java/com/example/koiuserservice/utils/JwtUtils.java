package com.example.koiuserservice.utils;

import com.example.koiuserservice.entity.Users;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtUtils {
    private String jwtSecret = "secret";
    private int jwtExpirationMs = 86400000;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_BLACKLIST_PREFIX = "blacklist:";
    private static final String ACTIVE_TOKEN_PREFIX = "active:";


    @NonFinal
    protected long VALID_DURATION = 86400000;

    @NonFinal
    protected long REFRESHABLE_DURATION = 86400000;


    private void storeTokenInRedis(String token) {
       redisTemplate.opsForValue().set(ACTIVE_TOKEN_PREFIX + token, true, jwtExpirationMs);
    }

    public void blacklistToken(String token) {
        redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + token, true, jwtExpirationMs);
    }

    public String generateToken(Users user) {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("com.templateredis")
                .expirationTime(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .build();
        String token = generateToken(jwtClaimsSet);
        storeTokenInRedis(token);
        return token;
    }

    private String generateToken(JWTClaimsSet claimsSet) {
       try {
           JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
           byte[] key = jwtSecret.getBytes(StandardCharsets.UTF_8);
           if (key.length < 32) {
               key = Arrays.copyOf(key, 32);
           }
           jwsObject.sign(new MACSigner(key));
           return jwsObject.serialize();
       } catch (JOSEException e) {
           throw new RuntimeException(e);
       }
    }

    public boolean isValidToken(String token) {
        try {
            verifyToken(token, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        if (isRefresh) {
            if (isBlacklisted(token)) {
                throw new JOSEException("Token is blacklisted");
            }
        }
        JWSObject jwsObject = JWSObject.parse(token);
        JWSVerifier jwsVerifier = new MACVerifier(jwtSecret);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new JOSEException("Token verification failed");
        }
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Token is expired");
        }
    }

    private boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(TOKEN_BLACKLIST_PREFIX + token);
    }

    public void logout(String token) {
        blacklistToken(token);
    }
}
