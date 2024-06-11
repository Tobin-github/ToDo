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
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/jcenter")
        maven("https://jitpack.io")

        google()
        mavenCentral()
    }
}

rootProject.name = "To Do"
include(":app")
include(":navigation-tab")
include(":component-service")
include(":module-accounting")
include(":module-schedule")
include(":module-my")
include(":module-media")
include(":shared")
include(":common-lib")
include(":module-gallery")
include(":framework-lib")
include(":module-ui")
include(":app-compose")
