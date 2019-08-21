package com.domain.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @ClassName: PasswordWithPBKDF2
 * @Description: PBKDF2加密算法
 * @Author: linjinhuang
 * @Date: 2019/6/17 14:25
 */
public class PasswordWithPBKDF2 {

    /**
     * PBKDF2算法类型
     */
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    /**
     * 盐的长度(字节)
     */
    public static final int SALT_BYTE_SIZE = 16 / 2;

    /**
     * 生成密文的长度
     */
    public static final int HASH_BIT_SIZE = 64 * 4;

    /**
     * 迭代次数
     */
    public static final int PBKDF2_ITERATIONS = 1000;

    /**
     * 对输入的password进行验证
     *
     * @param attemptedPassword 待验证的password
     * @param encryptedPassword 密文
     * @param salt              盐值
     * @return 是否验证成功
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用同样的盐值对用户输入的password进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比較，同样则验证成功。否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * 生成密文
     *
     * @param password 明文password
     * @param salt     盐值
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String getEncryptedPassword(String password, String salt) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * 生成盐
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        return toHex(salt);
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex 十六进制字符串
     * @return 二进制字符串
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 二进制字符串转十六进制字符串
     *
     * @param array 二进制字符串
     * @return 十六进制字符串
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }


    /**
     * 明文生成随机盐+密文字符串(用于生成密码)
     *
     * @param password 明文密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String getPasswordAddSalt(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //生成随机盐
        String saltStr = generateSalt();
        //根据随机盐和明文生成密文
        String encryptedPassword = getEncryptedPassword(password, saltStr);
        return saltStr + encryptedPassword;

    }

    /**
     * 校验密码
     *
     * @param password   明文密码
     * @param dbPassword 数据库中的密文(随机盐+密码密文)
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkPassword(String password, String dbPassword) {
        boolean result = false;
        try {
            //截取随机盐
            String saltStr = dbPassword.substring(0, SALT_BYTE_SIZE * 2);
            //截取密文密码
            String dbPasswordStr = dbPassword.substring(SALT_BYTE_SIZE * 2);
            //检验密码
            result = authenticate(password, dbPasswordStr, saltStr);
        } catch (Exception e) {
            return false;
        }
        return result;
    }


}
