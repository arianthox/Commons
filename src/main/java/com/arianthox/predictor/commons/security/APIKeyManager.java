package com.arianthox.predictor.commons.security;

import com.arianthox.predictor.commons.model.APIKeyModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Log
@UtilityClass
public class APIKeyManager {

    protected String generateSecret(final int keyLen) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLen);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encoded = secretKey.getEncoded();
            return DatatypeConverter.printHexBinary(encoded).toLowerCase(Locale.getDefault());
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    protected String generateToken(final String subject, String secret, final int expirationTimeInDays) {
        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(expirationTimeInDays).atZone(ZoneId.systemDefault()).toInstant()))
                .sign(HMAC512(secret.getBytes()));
    }

    public APIKeyModel generateAPIKey(final String subject, final int expirationTimeInDays) {
        log.log(Level.FINE,"Generating APIKey {}",subject);
        APIKeyModel.APIKeyModelBuilder builder = APIKeyModel.builder();
        builder.subject(subject);
        String secret = generateSecret(256);
        builder.secret(secret);
        String token = generateToken(subject, secret, expirationTimeInDays);
        builder.apiKey(token);

        return builder.build();
    }

    public boolean validateAPIKey(final String apiKey, final String subject, final String secret) {
        log.log(Level.FINE,"Validating APIKey {}",apiKey);
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(apiKey)
                .getSubject().equalsIgnoreCase(subject);

    }

}
