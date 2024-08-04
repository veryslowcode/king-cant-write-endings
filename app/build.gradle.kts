plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":dajfa"))
    implementation(project(":wari"))
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "vsc.cli.eol.Runner"
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "vsc.cli.eol.Runner"
    }
}

tasks.register<Jar>("fatJar") {
    dependsOn("build")
    manifest {
        attributes["Main-Class"] = "vsc.cli.eol.Runner"
    }
    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks.jar.get() as CopySpec)
    archiveFileName = "kcwe.jar"
}
