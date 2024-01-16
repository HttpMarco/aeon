plugins {
    id("java")
    id("maven-publish")
}

group = "net.http.aeon"
version = "1.2.1"

dependencies {
    testImplementation(libs.jUnit)
    testRuntimeOnly(libs.jUnit)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}