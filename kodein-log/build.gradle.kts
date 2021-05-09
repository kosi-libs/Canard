plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        val datetimeMain by sourceSets.creating {
            dependsOn(common.main)
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")
            }
        }

        add(kodeinTargets.jvm.jvm) {
            main.dependsOn(datetimeMain)
            target.setCompileClasspath()
            main.dependencies {
                implementation("org.slf4j:slf4j-api:1.7.30")
                compileOnly(rootProject.files("libs/android-log.jar"))
            }
        }

        add(kodeinTargets.js.js) {
            main.dependsOn(datetimeMain)
            configure(listOf(mainCompilation, testCompilation)) {
                kotlinOptions {
                    sourceMap = true
                    sourceMapEmbedSources = "always"
                }
            }
        }

        val defaultNativeMain by sourceSets.creating {
            dependsOn(common.main)
        }

        add(kodeinTargets.native.allDesktop) {
            main {
                dependsOn(defaultNativeMain)
                dependsOn(datetimeMain)
            }
        }

        val embeddedNativeMain by sourceSets.creating {
            dependsOn(common.main)
        }

        add(kodeinTargets.native.allEmbeddedLinux) {
            main {
                dependsOn(defaultNativeMain)
                dependsOn(embeddedNativeMain)
            }
        }

        add(kodeinTargets.native.allDarwin) {
            main.dependsOn(datetimeMain)
            mainCompilation.cinterops.create("darwin_log")
        }
    }
}

kodeinUpload {
    name = "Kodein-Log"
    description = "Kodein Log Library"
}