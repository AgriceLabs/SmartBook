package com.barping;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Utility class to generate random, prefixed identifiers.
 */
public final class IdGenerator {
    private static final Random RANDOM = new Random();
    private static final Set<String> BOOK_IDS = new HashSet<>();
    private static final Set<String> TRANSACTION_IDS = new HashSet<>();

    private IdGenerator() {
    }

    public static String nextBookId() {
        return generateUnique("BK", BOOK_IDS);
    }

    public static String nextTransactionId() {
        return generateUnique("TRX", TRANSACTION_IDS);
    }

    private static String generateUnique(String prefix, Set<String> existing) {
        // Loop until we find an unused ID; random keeps IDs non-sequential.
        while (true) {
            int randomNumber = 100 + RANDOM.nextInt(900);
            String candidate = prefix + randomNumber;
            if (existing.add(candidate)) {
                return candidate;
            }
        }
    }
}
