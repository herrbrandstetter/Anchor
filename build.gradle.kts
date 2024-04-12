import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.9.21"
}

application.mainClass.set("de.herrbrandstetter.anchor.AnchorKt")
group = "de.herrbrandstetter"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.19")
    implementation("org.danilopianini:khttp:1.4.3")
    implementation("org.mongodb:mongodb-driver-kotlin-sync:5.0.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    archiveFileName.set("anchor.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    sourceCompatibility = "1.8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}