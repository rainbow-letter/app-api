plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"

    kotlin("kapt") version "2.0.21"
}

group = "kr.co.rainbowletter"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("io.jsonwebtoken:jjwt:0.12.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("software.amazon.awssdk:s3:2.29.51")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor:reactor-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.7.3")

    // image-webp
    implementation("com.sksamuel.scrimage:scrimage-core:4.3.0")
    implementation("com.sksamuel.scrimage:scrimage-webp:4.3.0")
    runtimeOnly("com.mysql:mysql-connector-j")

    // feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.2.0")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.5")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.5")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.5")
}

val generatedQueryDsl = file("src/main/generated")

sourceSets {
    main {
        kotlin.srcDirs += generatedQueryDsl
    }
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generatedQueryDsl)
}

tasks.named("clean") {
    doLast {
        generatedQueryDsl.deleteRecursively()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}