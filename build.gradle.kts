plugins {
    id("java")
    id("maven-publish")
}

allprojects {
    group = "net.http.aeon"
    version = "1.0.0"
    description = "Configuration Framework"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    testCompileOnly ("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.24")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId =  "net.http.aeon"
            artifactId = "Aeon"
            version = "1.0.0"
            from(components.getByName("java"))
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/HttpMarco/Aeon")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}