buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://raw.githubusercontent.com/kosi-libs/kodein-internal-gradle-plugin/mvn-repo")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:6.20.2-kotlin-1.7.20-RC")
    }
}

apply { plugin("org.kodein.settings") }

rootProject.name = "Canard"

include(
        ":canard",
        ""
)
