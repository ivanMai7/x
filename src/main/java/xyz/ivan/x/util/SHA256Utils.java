package xyz.ivan.x.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utils {

    /**
     * 使用 SHA-256 算法对字符串进行加密
     *
     * @param input 需要加密的字符串
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(String input) {
        try {
            // 获取 SHA-256 消息摘要实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 执行加密
            byte[] digest = md.digest(input.getBytes());

            // 将字节数组转换为 16 进制字符串
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 加密算法不存在", e);
        }
    }

    /**
     * 将字节数组转换为 16 进制字符串
     *
     * @param bytes 字节数组
     * @return 16 进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        // 测试 SHA-256 加密
        String original = "hello world";
        String encrypted = SHA256Utils.encrypt(original);
        System.out.println("原始字符串: " + original);
        System.out.println("加密结果: " + encrypted);
    }
}
