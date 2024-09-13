plugins {
    id("java")
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow")
}

group = rootProject.group
version = rootProject.version

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle(project.properties["paper_build"].toString())
    pluginRemapper("net.fabricmc:tiny-remapper:0.10.3:fat")
    compileOnly(files("../libs/compile/coprolite-api-0.0.1-SNAPSHOT.jar"))
    compileOnly("org.spongepowered:mixin:0.8.5")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.encoding = "UTF-8"
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("coprolite.plugin.json") {
            expand("version" to version)
        }
    }

    reobfJar {
        remapperArgs.add("--mixin")
    }

    assemble {
        dependsOn("reobfJar")
    }
}
