package io.github.vvb2060.xposed;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Init implements IXposedHookLoadPackage {
    private static final String TAG = "XposedInit";

    private static void hook(DexHelper dex) {
        //TODO
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!"TODO".equals(lpparam.packageName)) return;
        if (!"TODO".equals(lpparam.processName)) return;
        new Thread(() -> {
            try (var dex = new DexHelper(lpparam.classLoader)) {
                hook(dex);
            }
            Log.d(TAG, "hook done");
        }).start();
        Log.d(TAG, "handleLoadPackage done");
    }
}
