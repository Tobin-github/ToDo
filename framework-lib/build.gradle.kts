plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.toVersion(rootProject.extra["java_version"] as Int)
    targetCompatibility = JavaVersion.toVersion(rootProject.extra["java_version"] as Int)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = (rootProject.extra["java_version"] as Int).toString()
    }
}

dependencies {
    compileOnly(files("libs/framework.jar"))
    compileOnly(files("libs/car.jar"))


}