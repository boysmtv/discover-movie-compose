pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        google()

        /* disable for using jitpack*/
        mavenLocal()
//        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()

        /* disable for using jitpack*/
        mavenLocal()
//        maven("https://jitpack.io")
    }
}

rootProject.name = "App Movie"
include(":app")
include(":feature")
include(":data")
include(":domain")
include(":common")
include(":nav")
