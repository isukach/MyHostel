package by.bsuir.suite.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordEncryptor {
    public static final String SALT = "A7C678D1E45A";

    private PasswordEncryptor() {
    }

    public static String encrypt(String pass){
        char[] chars = pass.toCharArray();
        byte[] bytes = new byte[chars.length];
        for(int i = 0; i < chars.length; i ++){
            bytes[i] = (byte)chars[i];
        }
        try{
            MessageDigest alg = MessageDigest.getInstance("SHA-1");
            alg.update(bytes);
            byte[] hash = alg.digest();
            StringBuilder result = new StringBuilder("");
            for (byte aHash : hash) {
                result.append(String.format("%02X", aHash));
            }
            return result.toString();
        }
        catch(NoSuchAlgorithmException e){
            return null;
        }
    }
}
