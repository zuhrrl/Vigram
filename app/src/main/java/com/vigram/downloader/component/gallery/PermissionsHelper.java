package com.vigram.downloader.component.gallery;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

public class PermissionsHelper {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 100; // any code you want.
    private static boolean permissionsGranted;

    public static PermissionsHelper newInstance() {
        PermissionsHelper permissionHelper = new PermissionsHelper();
        return permissionHelper;

    }

    public boolean checkAndRequestPermissions(Activity activity, String... permissions) {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }

            else {
                permissionsGranted = true;
            }

        }
        if (!listPermissionsNeeded.isEmpty()) {
            // permission granted
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
        }


        return permissionsGranted;
    }


}
