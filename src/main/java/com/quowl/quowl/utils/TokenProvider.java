package com.quowl.quowl.utils;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class TokenProvider {

    private final String secretKey;

    private final long tokenValidity;

    private final long tokenRefresh;

    public TokenProvider(String secretKey, long tokenValidity, long tokenRefresh) {
        this.secretKey = secretKey;
        this.tokenValidity = tokenValidity;
        this.tokenRefresh = tokenRefresh;
    }

    public String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + 1000L * tokenValidity;
        return userDetails.getUsername() + ":" + expires + ":" + computeSignature(userDetails, expires);
    }

    public long refreshToken(UserDetails userDetails, HttpServletResponse response, String token) {
        String[] parts = token.split(":");
        long expires = Long.parseLong(parts[1]);
        long refresh = System.currentTimeMillis() + tokenRefresh*1000L;
        expires += refresh - expires;
        StringBuilder updatedToken = new StringBuilder();
        updatedToken.append(userDetails.getUsername()).append(":");
        updatedToken.append(expires).append(":");
        updatedToken.append(computeSignature(userDetails, expires));
        CookieUtils.setAuthCookie(response, updatedToken.toString());
        return expires;
    }

    public String computeSignature(UserDetails userDetails, long expires) {

        // + IP address

        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername()).append(":");
        signatureBuilder.append(expires).append(":");
        signatureBuilder.append(userDetails.getPassword()).append(":");
        signatureBuilder.append(secretKey);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }

    public String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }
        String[] parts = authToken.split(":");
        return parts[0];
    }

    public boolean validateTokenExpire(UserDetails userDetails, HttpServletResponse response, String authToken) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        long currentTime = System.currentTimeMillis();
        if (expires >= currentTime) {
            expires = refreshToken(userDetails, response, authToken);
        }
        return expires >= currentTime;
    }

    public boolean validateTokenSignature(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        String signatureToMatch = computeSignature(userDetails, expires);

        return signature.equals(signatureToMatch);
    }

}
