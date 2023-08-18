package com.vn.aptech.smartphone.security.access;

import com.vn.aptech.smartphone.security.AppUserDetails;
import com.vn.aptech.smartphone.security.BasicUserDetails;
import com.vn.aptech.smartphone.security.TokenResolver;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
@Slf4j
@RequiredArgsConstructor
@Component
@Qualifier("accessTokenResolver")
public class AccessTokenResolver implements TokenResolver {
    private final UserDetailsService userDetailsService;
    @Value("${core.auth.access.publicKey}")
    private String accessPublicKey;

    @Override
    public Authentication verifyToken(String accessToken) {
        Jws<Claims> claimsJws = validateAccessToken(accessToken);
        if (claimsJws == null) return null;
        Claims body = claimsJws.getBody();
        String subject = body.getSubject();
        //
        BasicUserDetails userDetails = (BasicUserDetails) userDetailsService.loadUserByUsername(subject);
        //create application UserDetails
        AppUserDetails appUserDetails = AppUserDetails.create(userDetails.getSafeguardUser());
        return new AccessAuthenticationToken(appUserDetails, appUserDetails.getAuthorities());
    }

    public Jws<Claims> validateAccessToken(String accessToken) {
        try {
            PublicKey publicKey = getAccessPublicKey();
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(accessToken);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty", ex);
        } catch (NoSuchAlgorithmException ex) {
            log.error("The JWT does not reflect this algorithm", ex);
        } catch (InvalidKeySpecException ex) {
            log.error("Invalid public key spec", ex);
        }
        return null;
    }

    //Generate public key tu bien env
    private PublicKey getAccessPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(this.accessPublicKey));
        return keyFactory.generatePublic(keySpec);
    }
}
