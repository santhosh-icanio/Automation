/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.trilogy.dcm.security;

import com.trilogy.dcm.ConfigManager;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncryptionManager {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_CBC_PADDING = "AES/CBC/PKCS5Padding";
    private static final String ENCRYPTION_SECRET_KEY = ConfigManager.getConfig().getEncryptionSecretKey();

    public String encrypt(String data) {
        try {
            Cipher cipher = initCipher();
            byte[] iv = generateIv(cipher.getBlockSize());
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(), new IvParameterSpec(iv));
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(concatenate(iv, encryptedData));
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt the data", e);
        }
    }

    public String decrypt(String encryptedData) {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = initCipher();
            byte[] iv = extractIv(decodedData, cipher.getBlockSize());
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(), new IvParameterSpec(iv));
            byte[] originalData = cipher.doFinal(extractEncryptedPart(decodedData, iv.length));
            return new String(originalData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt the data", e);
        }
    }

    private Cipher initCipher() throws Exception {
        return Cipher.getInstance(AES_CBC_PADDING);
    }

    private SecretKeySpec getSecretKeySpec() {
        return new SecretKeySpec(ENCRYPTION_SECRET_KEY.getBytes(), AES_ALGORITHM);
    }

    private byte[] generateIv(int blockSize) {
        byte[] iv = new byte[blockSize];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private byte[] extractIv(byte[] data, int blockSize) {
        byte[] iv = new byte[blockSize];
        System.arraycopy(data, 0, iv, 0, blockSize);
        return iv;
    }

    private byte[] extractEncryptedPart(byte[] data, int ivSize) {
        byte[] encryptedPart = new byte[data.length - ivSize];
        System.arraycopy(data, ivSize, encryptedPart, 0, encryptedPart.length);
        return encryptedPart;
    }
}
