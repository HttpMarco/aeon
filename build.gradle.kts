plugins {
    id("java-library")
    alias(libs.plugins.nexus.publish)
}

apply(plugin = "signing")
apply(plugin = "maven-publish")

group = "dev.httpmarco"
version = "1.2.0.1-SNAPSHOT"

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
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
    // options
    options.encoding = "UTF-8"
    options.isIncremental = true
}

extensions.configure<PublishingExtension> {
    publications {
        create("library", MavenPublication::class.java) {
            from(project.components.getByName("java"))

            pom {
                name.set(project.name)
                url.set("https://github.com/httpmarco/osgon")
                description.set("Reflection/Data libary")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        name.set("Mirco Lindenau")
                        email.set("mirco.lindenau@gmx.de")
                    }
                }
                scm {
                    url.set("https://github.com/httpmarco/osgon")
                    connection.set("https://github.com/httpmarco/osgon.git")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            username.set(System.getenv("ossrhUsername")?.toString() ?: "")
            password.set(System.getenv("ossrhPassword")?.toString() ?: "")
        }
    }
    useStaging.set(!project.rootProject.version.toString().endsWith("-SNAPSHOT"))
}