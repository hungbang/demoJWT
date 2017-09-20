package com.smartdev.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64URL;
import com.smartdev.security.JWKGenerator;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.*;
import java.text.ParseException;

public class JWEServiceHandler {


    private JWKGenerator generator = new JWKGenerator();

    public String encryptP2P(String result) throws IOException, ParseException, JOSEException, NoSuchAlgorithmException {
        RSAKey keyPair = generator.getRSAKey();
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
        Payload payLoad = new Payload(result);
        JWEObject jweObject = new JWEObject(header, payLoad);
        System.out.println("Encrypted Key: " + jweObject.getEncryptedKey());
        JWEEncrypter jweEncrypter = new RSAEncrypter(keyPair.toRSAPublicKey());
        jweObject.encrypt(jweEncrypter);
        return jweObject.serialize();
    }

    public String decryptP2P(String result) throws IOException, ParseException, JOSEException {
        System.out.println("Data to be decrypted: " + result);
        RSAKey keyPair = generator.getRSAKey();
        JWEDecrypter jweDecrypter = new RSADecrypter(keyPair.toRSAPrivateKey());
        JWEObject jweObject = JWEObject.parse(result);
        jweObject.decrypt(jweDecrypter);
        Payload payload = jweObject.getPayload();
        return payload.toString();
    }

    public static void main(String[] args) {
        System.out.println(EncryptionMethod.Family.AES_GCM.toString());
    }


    public byte[] encryptCEKwithRSA(SecretKey o) throws NoSuchAlgorithmException, IOException, ParseException, JOSEException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(BouncyCastleProviderSingleton.getInstance());
        RSAKey rsaKey = generator.getRSAKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, rsaKey.toRSAPublicKey());
        return cipher.doFinal(o.getEncoded());
    }

    public byte[] encryptContentWithAES(String json, SecretKey secretKey) throws NoSuchAlgorithmException, IOException, ParseException, JOSEException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Provider bc = BouncyCastleProviderSingleton.getInstance();
        Security.addProvider(bc);
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        return cipher.doFinal(json.getBytes());
    }


    public byte[] decryptCEKwithRSA(Base64URL o) throws NoSuchAlgorithmException, IOException, ParseException, JOSEException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Security.addProvider(BouncyCastleProviderSingleton.getInstance());
        RSAKey rsaKey = generator.getRSAKey();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, rsaKey.toRSAPrivateKey());
        return cipher.doFinal(o.decode());
    }

    public byte[] decryptContentWithAES(Base64URL o, SecretKey secretKey) throws NoSuchAlgorithmException, IOException, ParseException, JOSEException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Provider bc = BouncyCastleProviderSingleton.getInstance();
        Security.addProvider(bc);
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        return cipher.doFinal(o.decode());
    }
}
