plugins {
    kodein.library.mpp
}

kotlin.kodein {
    allNative()
    js()
    wasmJs()
    wasmWasi()

    jvm {
        target.setCompileClasspath()
        sources.main {
            dependencies {
                implementation(libs.slf4j.api)
                compileOnly(rootProject.files("libs/android-log.jar"))
            }
        }
    }

    allApple {
        compilations.main {
            cinterops.create("darwin_log")
        }
        sources.main {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }

    val nativeMain by kotlin.sourceSets.getting
    val defaultNativeMain by kotlin.sourceSets.creating {
        dependsOn(nativeMain)
    }

    addAll(targets.allNative - targets.allApple) {
        sources.main {
            dependsOn(defaultNativeMain)
        }
    }
}

kodeinUpload {
    name = "Canard"
    description = "Kotlin Multiplatform Log Library"
}