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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
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