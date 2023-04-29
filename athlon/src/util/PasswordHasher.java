/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 *
 * @author k
 */
public class PasswordHasher {
    private final static int MEMORY_COST = 10;
    private final static int TIME_COST = 3;
    private final static int PARALLELISM = 1; // We don't need parallelism here

    public static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(TIME_COST, MEMORY_COST, PARALLELISM, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hash) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(hash, password.toCharArray());
    }
}
