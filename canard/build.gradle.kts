plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        val datetimeMain by sourceSets.creating {
            dependsOn(common.main)
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }

        add(kodeinTargets.jvm.jvm) {
            main.dependsOn(datetimeMain)
            target.setCompileClasspath()
            main.dependencies {
                implementation(libs.slf4j.api)
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

        sourceSets.all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
            }
        }
    }
}

kodeinUpload {
    name = "Canard"
    description = "Kotlin Multiplatform Log Library"
}