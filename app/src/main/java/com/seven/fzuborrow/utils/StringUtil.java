package com.seven.fzuborrow.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.DocumentsContract;

public class StringUtil {
    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

}
