plugins {
    kotlin("multiplatform") version "1.3.61"
    `maven-publish`
}

val mavenRepository = "D:/Programs/Maven-local"
group = "io.trouveyre.geopti"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
    }
}

publishing {
    repositories {
        maven(uri("file://$mavenRepository"))
    }

    publications {
        create<MavenPublication>("geopti")
    }
}

