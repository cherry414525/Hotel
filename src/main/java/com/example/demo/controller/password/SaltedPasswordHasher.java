package com.example.demo.controller.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class SaltedPasswordHasher {
    // 生成随机的 salt
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte saltByte : saltBytes) {
            stringBuilder.append(String.format("%02x", saltByte));
        }
        return stringBuilder.toString();
    }

    // 对密码进行加密
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(salt.getBytes());
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                stringBuilder.append(String.format("%02x", hashedByte));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 验证密码是否匹配
    public static boolean verifyPassword(String providedPassword, String salt, String storedPassword) {
        String hashedPassword = hashPassword(providedPassword, salt);
        return hashedPassword != null && hashedPassword.equals(storedPassword);
    }
}