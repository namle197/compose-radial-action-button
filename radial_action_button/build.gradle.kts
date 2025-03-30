import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    `maven-publish`
    id("com.vanniktech.maven.publish") version "0.31.0"
}

android {
    namespace = "com.namle197.radial_action_button"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

mavenPublishing {
    pom {
        name.set("Composable Radial Action Button")
        description.set("A beautiful radial action button for Jetpack Compose")
        url.set("https://github.com/namle197/compose-radial-action-button")
        inceptionYear.set("2025")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("cvowuvDV")
                name.set("Phuong Nam")
                email.set("phuongnam091097@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/namle197/compose-radial-action-button")
            connection.set("scm:git:git://github.com/namle197/compose-radial-action-button.git")
            developerConnection.set("scm:git:ssh://git@github.com:namle197/compose-radial-action-button.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)  // Publishes to Maven Central via Sonatype

    signAllPublications()  // Required for Maven Central
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}