plugins {
    id("org.kodein.library.mpp")
}

kodein {
    kotlin {
        add(kodeinTargets.jvm.jvm) {
            target.setCompileClasspath()
            main.dependencies {
                implementation("org.slf4j:slf4j-api:1.7.30")
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

        add(kodeinTargets.native.allDesktop + kodeinTargets.native.allLinux + kodeinTargets.native.allAndroid) {
            main { dependsOn(defaultNativeMain) }
        }

        add(kodeinTargets.native.allIos)
    }
}