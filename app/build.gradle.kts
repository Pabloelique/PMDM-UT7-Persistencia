// build.gradle.kts del módulo app: aquí se configura la aplicación Android.
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // Paquete que pide el enunciado (es la ruta que luego se ve en el Device File Explorer:
    // data/data/com.ies.iax.prgmdm/...)
    namespace = "com.ies.iax.prgmdm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ies.iax.prgmdm"
        minSdk = 24          // Android 7.0 en adelante
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")                    // extensiones Kotlin de Android
    implementation("androidx.appcompat:appcompat:1.7.0")               // AppCompatActivity
    implementation("com.google.android.material:material:1.12.0")     // componentes Material (botones, switch...)
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
}
