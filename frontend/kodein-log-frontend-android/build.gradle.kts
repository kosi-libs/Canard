plugins {
    id("org.kodein.library.android")
}

kodeinLib {
    dependencies {
        api(project(":kodein-log-api") target "jvm")
    }
}
