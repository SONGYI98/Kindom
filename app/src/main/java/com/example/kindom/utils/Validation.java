package com.example.kindom.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Encapsulate logic for validating email, password and nonempty field
 */
public class Validation {

    private static final Pattern PATTERN_PASSWORD = Pattern.compile("^" +
            "(?=.*[A-Z])" +    // at least 1 uppercase letter
            "(?=.*[a-z])" +    // at least 1 lowercase letter
            "(?=.*[0-9])" +    // at least 1 number
            ".{8,}");          // at least 8 characters


    public static boolean isNonEmpty(CharSequence target) {
        return !TextUtils.isEmpty(target);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(CharSequence target) {
        return isNonEmpty(target) && PATTERN_PASSWORD.matcher(target).matches();
    }
}