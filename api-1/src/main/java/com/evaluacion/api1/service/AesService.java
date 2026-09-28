package com.evaluacion.api1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AesService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH = 128;

    private final SecretKeySpec key;

    public AesService(@Value("${app.aes.key}") String secretKey) {
        byte[] bytes = secretKey.getBytes(StandardCharsets.UTF_8);
        if (bytes.length != 32) {
            throw new IllegalArgumentException("app.aes.key debe tener exactamente 32 bytes");
        }
        this.key = new SecretKeySpec(bytes, "AES");
    }

    public String decrypt(String encrypted) {
        try {
            byte[] all = Base64.getDecoder().decode(encrypted);

            byte[] iv = new byte[12];
            byte[] cipherText = new byte[all.length - 12];

            System.arraycopy(all, 0, iv, 0, 12);
            System.arraycopy(all, 12, cipherText, 0, cipherText.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH, iv));

            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("No fue posible descifrar el secreto");
        }
    }
}
