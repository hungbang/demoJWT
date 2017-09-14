package com.smartdev.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.UUID;

public class JWKGenerator {
    public JWK generateKeyJWK() throws NoSuchAlgorithmException {
        // Generate the RSA key pair
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048); // Set the desired key length
        KeyPair keyPair = gen.generateKeyPair();

        // Convert to JWK format
        JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateKey) keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString()) // Give the key some ID (optional)
                .build();
        // Output
        System.out.println(jwk);
        return jwk;
    }

    public RSAPrivateKey rsaPrivateKey(String key) throws ParseException, JOSEException {
//        RSAKey rsaKey = RSAKey.parse("{\"p\":\"_nZKcW5PB-RiBtP7DRwppaUD9SPOA9WeOQi5AmJZh1B_eYjrqvyMO_6_qcaWEtnRQokzbrc0zKRQcJHVBEuiMzNAGdHwgj7deMK3RFLC93DV48QWTrM5RryFnPf9jik2vrCtkIG80Fg9mzrtdB6q_l4NKKKWXd6VIqlfphzMwMM\",\"kty\":\"RSA\",\"q\":\"pL1ANO2ub9cwtL0x0ophxXQBhrk8U0JDm_23syP6eS18WG1bfR9O3E-RMDkayyYLwDp86DVBcFkkFXU2wjRBHiaTkVI9MnIlBPr_9q34J_1OjeiAZJj4d3rTbavGIEKbSl6EpmoE-f49EX2Adk8uVQ7jVqUMue9vud7uxYbn_us\",\"d\":\"Kj-ffqLo24t_bWuSBgsGjLtSOR-7tf4kHTQdrBi1pBC6ME9PE9X2PTt85HfCEa95Szp8CDMedMVTsuUh0o7jx3tdhdDoSbk5yshpYH1CHETkvz3o7hsXwwXCRRHkNplxFF9oaCLH1353NPof6R-O4ZOgpyDBamI5ACgfQwfU0mfxF6GNEuNFiWlUNjJlR3COxADQ7V0Z4NkrE3IMl8HJNKsKaitdpxnH1XJRvYsh0LDpgwHEV9w-pvMGZdNJMdRJhzMArpjfqlJ8GfxBLjnodVfrhZSKxXo7bnc1seg3CIr-Cin3jZXhv7MgwQsHItWHqhMgI8M9SE55BSJ1mnF1RQ\",\"e\":\"AQAB\",\"kid\":\"c8b3e574-73a2-4e90-8cce-ecca5848b499\",\"qi\":\"hiHERTx1hgjo1Bf_0ISldhdgYaNbBGVUtSZwOK1cbhuFfuL2rN96F9k4fAelmvPWyfa1YSeP1EG3rjm1yb_3Q3SkTxiTy3SFiGDvdOUes0IF_beWwgS7MclTGqALC52OWoGqM6CpQIPitQD0XePcq55xgq38c84YbO5THVZWuSo\",\"dp\":\"QkQ18MJETI7aRGLx7yEVa3DEzhMr9G2uDbZKz-ci341EFTdmABpSvtEBwsnXjUMyLryiK0QEuQJVfGtN6Rd1PxYqrE6y7ohGjstOhLl00pGM8gq_sHEWCmjP9ufSHTJ4ckJ6gLZ4oF1KdeFrsyDFcQa3MIze0cVEW0l2gkDfxZc\",\"dq\":\"ex18aJHgtXCq08g4_dGxc_FYWqkArnBtOp0ItnaC8CDQNv2zS_Uj6H4GUXW70mqiiYysYJW8TAwybV7mB4kpyTCnRAmj9plblTdIJ8CoHqquFFvX6FZyCkjeza18KgvdJl0w4XWGNpaMaQ_T-5b9V1ADEnPS6PpKEyzKYRCLPPU\",\"n\":\"o7_k189NcoIYsR8LHzgaKHOnGv5khknS186ghB3C0P8MzxNTB3rM84knaIz11S1zFri5t0kXDmzNXEpBu4xd5zAdVCOFCyaVjT4ujbgA_fioS_tW6w_ZdKU5zSmrUKUUir_F-iTKHxJYUUp8h3amtQUbCT3TBYVEVPtfoC5i5bCSMVsm0Ab7YloWt5BlKqMw6JkJMmJd901HU0bokstCEdvFM8m0fiKhO_vYEu6Fq7oZ8TvX9IKR4dmtoZLmJMS120-d5yxcwxbI9oqChAZ0N6EiSc1QsSWhOAIvw7FoF8OpTVSMJs8eV7QjKYVW_red8B-KPUmC1jS5OGfGmSttAQ\"}\n");
        RSAKey rsaKey = RSAKey.parse(key);
        return rsaKey.toRSAPrivateKey();
    }

    public RSAPublicKey rsaPublicKey(String key) throws ParseException, JOSEException {
//        RSAKey rsaKey = RSAKey.parse("{\"p\":\"_nZKcW5PB-RiBtP7DRwppaUD9SPOA9WeOQi5AmJZh1B_eYjrqvyMO_6_qcaWEtnRQokzbrc0zKRQcJHVBEuiMzNAGdHwgj7deMK3RFLC93DV48QWTrM5RryFnPf9jik2vrCtkIG80Fg9mzrtdB6q_l4NKKKWXd6VIqlfphzMwMM\",\"kty\":\"RSA\",\"q\":\"pL1ANO2ub9cwtL0x0ophxXQBhrk8U0JDm_23syP6eS18WG1bfR9O3E-RMDkayyYLwDp86DVBcFkkFXU2wjRBHiaTkVI9MnIlBPr_9q34J_1OjeiAZJj4d3rTbavGIEKbSl6EpmoE-f49EX2Adk8uVQ7jVqUMue9vud7uxYbn_us\",\"d\":\"Kj-ffqLo24t_bWuSBgsGjLtSOR-7tf4kHTQdrBi1pBC6ME9PE9X2PTt85HfCEa95Szp8CDMedMVTsuUh0o7jx3tdhdDoSbk5yshpYH1CHETkvz3o7hsXwwXCRRHkNplxFF9oaCLH1353NPof6R-O4ZOgpyDBamI5ACgfQwfU0mfxF6GNEuNFiWlUNjJlR3COxADQ7V0Z4NkrE3IMl8HJNKsKaitdpxnH1XJRvYsh0LDpgwHEV9w-pvMGZdNJMdRJhzMArpjfqlJ8GfxBLjnodVfrhZSKxXo7bnc1seg3CIr-Cin3jZXhv7MgwQsHItWHqhMgI8M9SE55BSJ1mnF1RQ\",\"e\":\"AQAB\",\"kid\":\"c8b3e574-73a2-4e90-8cce-ecca5848b499\",\"qi\":\"hiHERTx1hgjo1Bf_0ISldhdgYaNbBGVUtSZwOK1cbhuFfuL2rN96F9k4fAelmvPWyfa1YSeP1EG3rjm1yb_3Q3SkTxiTy3SFiGDvdOUes0IF_beWwgS7MclTGqALC52OWoGqM6CpQIPitQD0XePcq55xgq38c84YbO5THVZWuSo\",\"dp\":\"QkQ18MJETI7aRGLx7yEVa3DEzhMr9G2uDbZKz-ci341EFTdmABpSvtEBwsnXjUMyLryiK0QEuQJVfGtN6Rd1PxYqrE6y7ohGjstOhLl00pGM8gq_sHEWCmjP9ufSHTJ4ckJ6gLZ4oF1KdeFrsyDFcQa3MIze0cVEW0l2gkDfxZc\",\"dq\":\"ex18aJHgtXCq08g4_dGxc_FYWqkArnBtOp0ItnaC8CDQNv2zS_Uj6H4GUXW70mqiiYysYJW8TAwybV7mB4kpyTCnRAmj9plblTdIJ8CoHqquFFvX6FZyCkjeza18KgvdJl0w4XWGNpaMaQ_T-5b9V1ADEnPS6PpKEyzKYRCLPPU\",\"n\":\"o7_k189NcoIYsR8LHzgaKHOnGv5khknS186ghB3C0P8MzxNTB3rM84knaIz11S1zFri5t0kXDmzNXEpBu4xd5zAdVCOFCyaVjT4ujbgA_fioS_tW6w_ZdKU5zSmrUKUUir_F-iTKHxJYUUp8h3amtQUbCT3TBYVEVPtfoC5i5bCSMVsm0Ab7YloWt5BlKqMw6JkJMmJd901HU0bokstCEdvFM8m0fiKhO_vYEu6Fq7oZ8TvX9IKR4dmtoZLmJMS120-d5yxcwxbI9oqChAZ0N6EiSc1QsSWhOAIvw7FoF8OpTVSMJs8eV7QjKYVW_red8B-KPUmC1jS5OGfGmSttAQ\"}\n");
        RSAKey rsaKey = RSAKey.parse(key);
        return rsaKey.toRSAPublicKey();
    }

    public KeyPair getKeyPair() throws IOException, ParseException {
        JWKSet jwkSet = JWKSet.load(new File(ClassLoader.getSystemResource("keystore.json").getPath()));
        JWKGenerator jwkGenerator = new JWKGenerator();
        RSAKey rsaKey = (RSAKey)jwkSet.getKeyByKeyId("2708");
        try {
            KeyPair keyPair = new KeyPair(rsaKey.toPublicKey(), rsaKey.toPrivateKey());
            return keyPair;
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RSAKey getRSAKey() throws IOException, ParseException {
        JWKSet jwkSet = JWKSet.load(new File(ClassLoader.getSystemResource("keystore.json").getPath()));
        JWKGenerator jwkGenerator = new JWKGenerator();
        return (RSAKey)jwkSet.getKeyByKeyId("2708");
    }

}
