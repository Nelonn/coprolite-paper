plugins {
    id("java")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
    compileOnly(project(":coprolite-paper"))
    compileOnly(files("../libs/compile/coprolite-api-0.0.1-SNAPSHOT.jar"))
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}

tasks.named<Copy>("processResources") {
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand("version" to version)
    }
}
