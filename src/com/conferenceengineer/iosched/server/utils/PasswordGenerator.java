package com.conferenceengineer.iosched.server.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Password Generator
 */
public final class PasswordGenerator {

    /**
     * The characters to use in the generation of the password.
     */

    private static final String CANDIDATE_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!+-=#<>/";


    private static final int CANDIDATE_CHARACTES_LENGTH = CANDIDATE_CHARACTERS.length();

    /**
     * Private constructor to stop instantiation.
     */

    private PasswordGenerator() {
        super();
    }

    /**
     * Generate a new password.
     */

    public static String generatePassword()
            throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        int length = 8+sr.nextInt(5);
        StringBuilder password = new StringBuilder(length);
        for(int i = 0 ; i < length; i++) {
            password.append(CANDIDATE_CHARACTERS.charAt(sr.nextInt(CANDIDATE_CHARACTES_LENGTH)));
        }
        return password.toString();
    }
}
