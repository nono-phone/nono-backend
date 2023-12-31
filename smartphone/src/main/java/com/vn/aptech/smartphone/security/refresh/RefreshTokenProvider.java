package com.vn.aptech.smartphone.security.refresh;

import com.vn.aptech.smartphone.security.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Qualifier("refreshTokenProvider")
public class RefreshTokenProvider implements TokenProvider {
    @Value("${core.auth.issuer}")
    private String issuer;

    @Value("${core.auth.refresh.secretKey}")
    private String refreshSecretKey;

    @Value("${core.auth.refresh.expirationInMs}")
    private long refreshExpirationInMs;

    @Override
    public String generateToken(Authentication authentication) {
        //validate object authentication
        validate(authentication);
        //get info getPrincipal() de generate refresh token
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return generateRefreshToken(principal);
    }

    private void validate(Authentication authentication) {
        Assert.notNull(authentication, "Authentication is missing");
        Assert.notNull(authentication.getPrincipal(), "Authentication principal is missing");
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication, "Only Accepts Core Token");
    }

    private String generateRefreshToken(UserDetails principal) {
        Date expiryDate = getRefreshExpiryDate();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .setIssuer(issuer)
                .signWith(getRefreshSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Date getRefreshExpiryDate() {
        Date now = new Date();
        return new Date(now.getTime() + this.refreshExpirationInMs);
    }

    private Key getRefreshSecretKey() {
        byte [] refreshSecretKeyDecode = Base64.getDecoder().decode(this.refreshSecretKey);
        return new SecretKeySpec(refreshSecretKeyDecode, "HmacSHA512");
    }

}
