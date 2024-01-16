plugins {
    id("java-library")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
}

apply(plugin = "signing")
apply(plugin = "maven-publish")

group = "dev.httpmarco"
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

tasks.register<org.gradle.jvm.tasks.Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.getByName("javadoc"))
}

tasks.register<org.gradle.jvm.tasks.Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(project.the<SourceSetContainer>()["main"].allJava)
}

extensions.configure<PublishingExtension> {
    publications.apply {
        create("maven", MavenPublication::class.java).apply {
            from(components.getByName("java"))

            artifact(tasks.getByName("sourcesJar"))
            artifact(tasks.getByName("javadocJar"))

            pom.apply {
                name.set(project.name)
                url.set("https://github.com/HttpMarco/aeonn")
                description.set("A de-/serialization library to manage simple configurations")

                developers {}

                licenses {}
            }
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(System.getenv("MAVEN_CENTRAL_CRADINATES_USERNAME"))
            password.set(System.getenv("MAVEN_CENTRAL_CRADINATES_PASSWORD"))
        }
    }
    useStaging.set(!project.version.toString().endsWith("-SNAPSHOT"))
}