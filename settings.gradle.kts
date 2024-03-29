pluginManagement {
    repositories {
        includeBuild("build-logic")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyTMDB4WIP"

include(":app")
include(
    ":core:common",
    ":core:ui",
    ":core:network",
    ":core:database",
    ":core:domain",
    ":core:model",
    ":core:testing",
)
include(
    ":feature:movie",
    ":feature:trending",
    ":feature:watchlist",
)
