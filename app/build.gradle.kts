plugins {
    id("com.android.application")
}

android {
    compileSdk = 36
    buildToolsVersion = "36.0.0"
    ndkVersion = "29.0.13113456"
    namespace = "io.github.vvb2060.TODO"
    defaultConfig {
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        externalNativeBuild {
            ndkBuild {
                abiFilters += listOf("arm64-v8a")
                arguments += "-j${Runtime.getRuntime().availableProcessors()}"
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            vcsInfo.include = false
            proguardFiles("proguard-rules.pro")
            signingConfig = signingConfigs["debug"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
        }
    }
    packaging {
        resources {
            excludes += "**"
        }
    }
    lint {
        checkReleaseBuilds = false
    }
    dependenciesInfo {
        includeInApk = false
    }
}

dependencies {
    compileOnly("androidx.annotation", "annotation", "1.3.0")
    compileOnly("de.robv.android.xposed", "api", "82")
}
