package com.kompor.ui;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Utils {
    public static TextWatcher clearError(TextInputLayout til) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til.setError("");
                til.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    public static void setInputError(TextInputLayout til, String error) {
        til.setErrorEnabled(true);
        til.setError(error);
    }

    public static boolean checkEmail(TextInputLayout til, String email) {
        if (!isValidEmail(email)) {
            til.setErrorEnabled(true);
            til.setError("Not a valid email!");
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }
}
