package com.gtan.singerdream.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtan.singerdream.model.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by user on 15-7-30.
 */
public final class TokenHandler {

    private static final String HMAC_ALGO = "HmacSHA256";
    private static final String SEPARATOR = ".";
    private static final String SEPARATOR_SPLITTER = "\\.";

    private final Logger logger = LoggerFactory.getLogger(TokenHandler.class);

    private final Mac hmac;

    public TokenHandler(byte[] secretKey){
        try {
            hmac=Mac.getInstance(HMAC_ALGO);
            hmac.init(new SecretKeySpec(secretKey,HMAC_ALGO));
        } catch (NoSuchAlgorithmException |InvalidKeyException e) {
            throw new IllegalStateException("failed to initialize HMAC " + e.getMessage(),e);
        }
    }

    public Manager parseManagerFromToken(String token){
        final String[] parts=token.split(SEPARATOR_SPLITTER);
        if(parts.length==2 && parts[0].length()>0 && parts[1].length()>0){
            logger.info("this token.part1 ---------->"+parts[0]);
            logger.info("this token.part2 ---------->"+parts[1]);

            try {
                final byte[] userBytes = fromBase64(parts[0]);

            final byte[] hash = fromBase64(parts[1]);

            boolean validHash = Arrays.equals(createHmac(userBytes),hash);
            //如果验证hash成功
            if(validHash){
                final Manager manager= managerFromJSON(userBytes);
                return manager;
            }
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> String createTokenFor(T t){
        byte[] userBytes = toJSON(t);
        byte[] hash = createHmac(userBytes);
        final StringBuilder sb = new StringBuilder(170);
        sb.append(toBase64(userBytes));
        sb.append(SEPARATOR_SPLITTER);
        sb.append(toBase64(hash));
        return sb.toString();
    }

    private <T> byte[] toJSON(T t){
        try {
            return  new ObjectMapper().writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    private Manager managerFromJSON(final byte[] managerBytes){
        try {
            return new ObjectMapper().readValue(new ByteArrayInputStream(managerBytes), Manager.class);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] fromBase64(String content){
        return DatatypeConverter.parseBase64Binary(content);
    }

    private String toBase64(byte[] content){
        return DatatypeConverter.printBase64Binary(content);
    }
    private synchronized byte[] createHmac(byte[] content){
        return hmac.doFinal(content);
    }
}
