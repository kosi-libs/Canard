plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {

        common {
            main.dependencies {
                api(project(":kodein-log-api"))
            }
        }

        add(kodeinTargets.jvm) {
            target.setCompileClasspath()
        }

        add(kodeinTargets.js)

        add(kodeinTargets.native.allNonWeb)
    }
}
