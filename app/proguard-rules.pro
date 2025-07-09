-repackageclasses
-allowaccessmodification

-keep class io.github.vvb2060.xposed.Init {
    <init>();
}

-keep class io.github.vvb2060.xposed.DexHelper {
    native <methods>;
    long token;
    java.lang.ClassLoader classLoader;
}

-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
}
