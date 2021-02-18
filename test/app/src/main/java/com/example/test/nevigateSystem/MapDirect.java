package com.example.test.nevigateSystem;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MapDirect {
    // use current
    public void NaviGoogle(Context context, String latitude, String longitude) {
        if (isAvilible(context, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=w");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Google map has not been installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAvilible(Context context, String packageName) {
        //get packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //all rge information of all packages
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //store
        List<String> packageNames = new ArrayList<String>();
        //get their names
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }
}
