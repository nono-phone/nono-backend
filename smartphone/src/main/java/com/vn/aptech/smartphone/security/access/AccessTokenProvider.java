package com.vn.aptech.smartphone.security.access;

import com.vn.aptech.smartphone.exception.ApplicationException;
import com.vn.aptech.smartphone.security.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
@Component
@RequiredArgsConstructor
@Qualifier("accessTokenProvider")
public class AccessTokenProvider implements TokenProvider {
    @Value("${core.auth.issuer}")
    private String issuer;

    @Value("${core.auth.access.privateKey}")
    private String accessPrivateKey;

    @Value("${core.auth.access.expirationInMs}")
    private long accessExpirationInMs;

    @Override
    public String generateToken(Authentication authentication) {
        validate(authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return generateAccessToken(principal);
    }


    private void validate(Authentication authentication) {
        Assert.notNull(authentication, "Authentication is missing");
        Assert.notNull(authentication.getPrincipal(), "Authentication principal is missing");
    }

    private String generateAccessToken(UserDetails principal) {
        Date expiryDate = getAccessExpiryDate();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("authorities", principal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setIssuer(issuer)
                .signWith(getAccessPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    private Date getAccessExpiryDate() {
        Date now = new Date();
        return new Date(now.getTime() + this.accessExpirationInMs);
    }

    private PrivateKey getAccessPrivateKey() {
            KeyFactory keyFactory;
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                byte[] decode = Base64.getDecoder().decode(this.accessPrivateKey);
                PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(decode);
                return keyFactory.generatePrivate(keySpecPKCS8);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new ApplicationException(e.getMessage());
            }
    }


}
