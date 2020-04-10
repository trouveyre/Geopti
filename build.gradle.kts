plugins {
    kotlin("multiplatform") version "1.3.71"
    `maven-publish`
}

val mavenRepository = "D:/Programs/Maven-local"
//val mavenRepository = "C:/Maven-local"
group = "io.trouveyre.geopti"
version = "0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js()

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
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
//                implementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
//                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.1")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

publishing {
    repositories {
        maven(uri("file://$mavenRepository"))
    }
}