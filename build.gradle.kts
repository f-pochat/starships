import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    application
    id("org.openjfx.javafxplugin").version("0.0.13")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/chess-ui")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USER")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson:gson:2.10")
    implementation("edu.austral.ingsis.starships:starships-ui:1.0.0")
}

ktlint {
    disabledRules.set(listOf("no-wildcard-imports"))
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "18"
    modules = listOf("javafx.graphics")
}

application {
    // Define the main class for the application.
    mainClass.set("main.AppKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
