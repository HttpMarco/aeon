plugins {
    id("java")
    id("maven-publish")
}

allprojects {
    group = "net.http.aeon"
    version = "1.2.1"
    description = "Configuration Framework"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "net.http.aeon"
            artifactId = "Aeon"
            version = "1.2.1"
            from(components.getByName("java"))
        }
    }
}