package com.vigram.downloader.component.library;

import android.annotation.SuppressLint;
import android.content.Context;

public class ClipboardHelper {

    @SuppressWarnings("deprecation")
    @SuppressLint("ObsoleteSdkInt")
    public static void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            assert clipboard != null;
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            assert clipboard != null;
            clipboard.setPrimaryClip(clip);
        }
    }
}
