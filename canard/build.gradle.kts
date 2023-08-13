plugins {
    kodein.library.mpp
}

kotlin.kodein {
    jsEnv()

//    all() TODO kotlinx-datetime does not support WASM

    val commonMain by common.main
//        val datetimeMain by sourceSets.creating {
//            dependsOn(common.main)
//            dependencies {
//                api(libs.kotlinx.datetime)
//            }
//        }
    val datetimeMain by kotlin.sourceSets.creating {
        dependsOn(commonMain)
        dependencies {
            api(libs.kotlinx.datetime)
        }
    }

//        add(kodeinTargets.jvm.jvm) {
//            main.dependsOn(datetimeMain)
//            target.setCompileClasspath()
//            main.dependencies {
//                implementation(libs.slf4j.api)
//                compileOnly(rootProject.files("libs/android-log.jar"))
//            }
//        }
//    }
    jvm {
        target.setCompileClasspath()
        sources.main {
            dependsOn(datetimeMain)
            dependencies {
                implementation(libs.slf4j.api)
                compileOnly(rootProject.files("libs/android-log.jar"))
            }
        }
    }

//    add(kodeinTargets.js.js) {
//        main.dependsOn(datetimeMain)
//        configure(listOf(mainCompilation, testCompilation)) {
//            kotlinOptions {
//                sourceMap = true
//                sourceMapEmbedSources = "always"
//            }
//        }
//    }
    js {
        sources.main { dependsOn(datetimeMain) }
        compilations.configureEach {
            kotlinOptions {
                sourceMap = true
                sourceMapEmbedSources = "always"
            }
        }
    }

    val allNativeMain by kotlin.sourceSets.creating {
        dependsOn(commonMain)
    }
//    val defaultNativeMain by sourceSets.creating {
//        dependsOn(common.main)
//    }
    val defaultNativeMain by kotlin.sourceSets.creating {
        dependsOn(allNativeMain)
    }

//    add(kodeinTargets.native.allDesktop) {
//        main {
//            dependsOn(defaultNativeMain)
//            dependsOn(datetimeMain)
//        }
//    }
    allDesktop {
        sources.main {
            dependsOn(defaultNativeMain)
            dependsOn(datetimeMain)
        }
    }

//    val embeddedNativeMain by sourceSets.creating {
//        dependsOn(common.main)
//    }
    val embeddedNativeMain by kotlin.sourceSets.creating {
        dependsOn(allNativeMain)
    }

//    add(kodeinTargets.native.allEmbeddedLinux) {
//        main {
//            dependsOn(defaultNativeMain)
//            dependsOn(embeddedNativeMain)
//        }
//    }
    linuxArm64 {
        sources.main {
            dependsOn(defaultNativeMain)
            dependsOn(embeddedNativeMain)
        }
    }

//    add(kodeinTargets.native.allDarwin) {
//        main.dependsOn(datetimeMain)
//        mainCompilation.cinterops.create("darwin_log")
//    }
    val allAppleMobileMain by kotlin.sourceSets.creating {
        dependsOn(allNativeMain)
    }
    // TODO watchosDeviceArm64 is not supported by KotlinX DateTime
    addAll(targets.allAppleMobile - targets.watchosDeviceArm64) {
        sources.main {
            dependsOn(allAppleMobileMain)
            dependsOn(datetimeMain)
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
        compilations.main {
            cinterops.create("darwin_log")
        }
    }
}

kotlin.sourceSets.all {
    languageSettings {
        optIn("kotlin.RequiresOptIn")
    }
}

kodeinUpload {
    name = "Canard"
    description = "Kotlin Multiplatform Log Library"
}