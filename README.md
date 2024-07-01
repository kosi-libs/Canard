[![Maven Central](https://img.shields.io/maven-central/v/org.kodein.log/canard)](https://mvnrepository.com/artifact/org.kodein.log/canard)
![Github Actions](https://github.com/kosi-libs/Canard/actions/workflows/snapshot.yml/badge.svg)
[![MIT License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/kosi-libs/Canard/blob/master/LICENSE.txt)
[![Slack channel](https://img.shields.io/badge/Chat-Slack-green.svg?style=flat&logo=slack)](https://kotlinlang.slack.com/messages/kodein/)

# Canard - Kotlin Multiplatform Logging library

**Canard** is a lightweight Kotlin Multiplatform logging library with a simple API, working on: 
 - JVM / Android
 - iOS, as well as all Kotlin/Native targets
 - JavaScript / WasmJS

**Canard** allows you to:

- Easily set up logging in a Kotlin Multiplatform
- Log what you need on different levels
- Avoid worrying about platform-specific frontend implementations

**Canard** is a good choice because:

- It integrates nicely with all Kotlin/Multiplatform targets
- It has a straightforward design with a user-friendly and comprehensible API.

## Installation

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.kodein.log:canard:{version}")
            }
        }
    }
}
```

## Example

```kotlin
// Simple example
val loggerFactory = LoggerFactory(defaultLogFrontend)
val logger = newLogger(loggerFactory)

logger.info { "Welcome to ..." }
logger.warning { "... the Canard documentation!" }
```

## Read more

See **[Canard documentation](https://kosi-libs.org/canard/)**.
