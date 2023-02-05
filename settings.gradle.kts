buildscript {
    repositories {
        mavenLocal()
        maven(url = "https://raw.githubusercontent.com/kosi-libs/kodein-internal-gradle-plugin/mvn-repo")
    }
    dependencies {
        classpath("org.kodein.internal.gradle:kodein-internal-gradle-settings:7.0.2")
    }
}

apply { plugin("org.kodein.settings") }

// Root project name is conflicting with sub-projects with the same name when using project accessors
// see. https://github.com/gradle/gradle/issues/16608
rootProject.name = "KosiLogging"

include(
        ":canard",
        ""
)
