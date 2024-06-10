package com.kompor.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

    public static String uriToBase64(Context context, Uri uri) {
        try {
            // Get the image extension
            String mimeType = context.getContentResolver().getType(uri);
            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);

            // Open input stream from URI
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Convert bitmap to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream); // Adjust format as needed
            byte[] byteArray = outputStream.toByteArray();

            // Encode byte array to Base64 string
            String base64Image = Base64.encodeToString(byteArray, Base64.NO_WRAP);

            // Return the image data with prefix
            return "data:image/" + extension + ";base64," + base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }
}
