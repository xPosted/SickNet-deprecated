package com.jubaka.sors.managed;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by root on 27.10.16.
 */
@Named
@Singleton
public class PassEncoder implements Serializable {

    public static String encode(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] md5Hash = md.digest();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(md5Hash);

        } catch (NoSuchAlgorithmException noalgo) {
            noalgo.printStackTrace();
            return null;
        }
    }
}
