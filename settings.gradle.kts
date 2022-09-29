buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://raw.githubusercontent.com/kosi-libs/kodein-internal-gradle-plugin/mvn-repo")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:6.21.0")
    }
}

apply { plugin("org.kodein.settings") }

rootProject.name = "Canard"

include(
        ":canard",
        ""
)
