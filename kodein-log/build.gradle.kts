plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
            main.dependencies {
                implementation("org.slf4j:slf4j-api:1.7.30")
                compileOnly(rootProject.files("libs/android.jar"))
            }
        }
        add(kodeinTargets.js.js) {
            configure(listOf(mainCompilation, testCompilation)) {
                kotlinOptions {
                    sourceMap = true
                    sourceMapEmbedSources = "always"
                }
            }
        }

        val defaultNativeMain by sourceSets.creating {
            dependsOn(sourceSets["commonMain"])
        }

        add(kodeinTargets.native.allDesktop + kodeinTargets.native.allEmbeddedLinux) {
            main { dependsOn(defaultNativeMain) }
        }

        add(kodeinTargets.native.allDarwin) {
            mainCompilation.cinterops.create("ios_log")
        }
    }
}

kodeinUpload {
    name = "Kodein-Log"
    description = "Kodein Log Library"
}