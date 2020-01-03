plugins {
    id("org.kodein.library.mpp")
}

allprojects {
    group = "org.kodein.log"
    version = "0.1.0"
}

kodein {
    kotlin {
        add(kodeinTargets.native.allIos) {
            main.dependencies {
                api(project(":kodein-log"))
            }
        }
    }
}
