package com.example.implauthenticationserver.util;

import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {

    private GenerateCodeUtil() {};

    public static String generateCode() {
        String code;

        try {
            SecureRandom random = SecureRandom.getInstanceStrong();

            int value = random.nextInt(9000) + 1000;
            code = String.valueOf(value);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("An unexpected error happened when generate the random code");
        }

        return code;
    }
}
