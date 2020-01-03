plugins {
    id("org.kodein.library.android")
}

kodeinLib {
    dependencies {
        api(project(":kodein-log") target "jvm")
    }
}
