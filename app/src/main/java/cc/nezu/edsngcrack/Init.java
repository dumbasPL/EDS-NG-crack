package cc.nezu.edsngcrack;

import java.util.Arrays;
import java.util.Objects;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook;

import io.github.vvb2060.xposed.DexHelper;

public class Init implements IXposedHookLoadPackage {
    private static final String TAG = "edsngcrack";

    private static void hook(DexHelper dex) {
        var arr = dex.findMethodUsingString(
            "moduleVersion", false, dex.encodeClassIndex(Enum.class),
            (short) 3, null, -1, null, null, null, true);
        var getModuleState = Arrays.stream(arr)
                .mapToObj(dex::decodeMethodIndex)
                .filter(Objects::nonNull)
                .findFirst();
        if (getModuleState.isPresent()) {
            var method = getModuleState.get();
            Log.d(TAG, "getModuleState: " + method);
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                Enum<?> cachedAvailable = null;
                Enum<?> cachedNotPurchased = null;

                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    var res = (Enum<?>)param.getResult();
                    if (cachedAvailable == null || cachedNotPurchased == null) {
                        for (var value : res.getDeclaringClass().getEnumConstants()) {
                            if (value.name().equals("Available")) {
                                cachedAvailable = (Enum<?>) value;
                                Log.d(TAG, "getModuleState: Found Available enum value: " + cachedAvailable.name());
                            }
                            if (value.name().equals("NotPurchased")) {
                                cachedNotPurchased = (Enum<?>) value;
                                Log.d(TAG, "getModuleState: Found NotPurchased enum value: " + cachedNotPurchased.name());
                            }
                        }
                    }
                    if (cachedAvailable != null && cachedNotPurchased != null && res == cachedNotPurchased) {
                        Log.d(TAG, "getModuleState: Replacing NotPurchased with Available");
                        param.setResult(cachedAvailable);
                    }
                }
            });
        } else {
            Log.e(TAG, "Failed to find getModuleState");
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!"com.sovworks.projecteds".equals(lpparam.packageName)) return;
        try (var dex = new DexHelper(lpparam.classLoader)) {
            hook(dex);
        }
        Log.d(TAG, "handleLoadPackage done");
    }
}
