plugins {
    id("org.kodein.root")
}

allprojects {
    group = "org.kodein.log"
    version = "0.8.0"

    repositories {
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

kodeinPublications {
    repo = "Kodein-Log"
}
