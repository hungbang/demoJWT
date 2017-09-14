package com.smartdev.security.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JWEDataHelper {

    public String encryptData(KeyPair keyPair, String result){
        try{
            Cipher encrypter = Cipher.getInstance("RSA");
            encrypter.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            encrypter.update(result.getBytes());
            return Base64.getEncoder().encodeToString(encrypter.doFinal());
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String decryptData(KeyPair keyPair, String encrypted){
        try{
            Cipher decrypter = Cipher.getInstance("RSA");
            decrypter.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            decrypter.update(Base64.getDecoder().decode(encrypted));
            String decrypted = new String(decrypter.doFinal());
            System.out.println("jajaja: "+ decrypted);
            return decrypted;
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return "";
    }
}
