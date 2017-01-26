package com.duckduckgo.mobile.android.util;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fgei on 1/25/17.
 */

public class DefaultUtils {

    public static boolean isDDGDefaultBrowser(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"));
        return isDDGDefaultForIntent(context, intent);
    }

    public static boolean isDDGDefaultSearch(Context context) {
        //Intent.ACTION_ASSIST is not supported
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return false;
        }
        //In Marshmallow this will return false, uncomment the code below after we implement the assistant API
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return false;/*
            String assistant = Settings.Secure.getString(context.getContentResolver(), "voice_interaction_service");
            if(assistant == null) {
                return false;
            }
            ComponentName componentName = ComponentName.unflattenFromString(assistant);
            return componentName != null && componentName.getPackageName().equals(context.getPackageName());*/
        }
        Intent intent = new Intent(Intent.ACTION_ASSIST);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        return isDDGDefaultForIntent(context, intent);
    }

    public static void promptDDGAsDefaultBrowser(final Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        new AlertDialog.Builder(context)
                .setTitle("Browser")
                .setMessage("Do you want to set DuckDuckGo as the default browser?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* todo check a better way to prompt the user to select DDG as the default app,
                           ACTION_HOME_SETTINGS shows the browser/assistant default app only on M and up (sdk >= 23), is present in lollipop (23 > sdk >= 21) but show just the launcher options, and is unavailable below lollipop (sdk < 21)
                           ACTION_APPLICATION_DETAILS_SETTINGS show the browser options only in (sdk >= 23)
                        if(false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            //Intent intent = new Intent(Settings.ACTION_HOME_SETTINGS);
                            Intent intent = new Intent(Settings.ACTION_SEARCH_SETTINGS);
                            context.startActivity(intent);
                            Toast.makeText(context, "Click on browser app and then select DuckDuckGo", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:"+context.getPackageName()));
                            context.startActivity(intent);
                        }*/
                        //context.startActivity(intent);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:"+context.getPackageName()));
                        context.startActivity(intent);
                        Toast.makeText(context, "Click on browser app and then select DuckDuckGo", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();

    }

    public static void promptDDGAsDefaultAssistant(final Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        new AlertDialog.Builder(context)
                .setTitle("Assistant")
                .setMessage("Do you want to set DuckDuckGo as the default assistant?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_VOICE_INPUT_SETTINGS));
                        Toast.makeText(context, "Select DuckDuckGo on Assist app", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create().show();
    }

    private static boolean isDDGDefaultForIntent(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        ResolveInfo activity = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activity != null && activity.activityInfo.packageName.equals(context.getPackageName());
    }
}
