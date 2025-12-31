plugins {
    java
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "io.github.adrianvic.itemeconomy"
version = System.getenv("ITEMECO_VERSION_NAME") ?: "unknown"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/creatorfromhell/")
}

/* ----------------------------------------- */
/*            SUPPORTED VERSIONS             */
/* ----------------------------------------- */

val mcVersions = listOf<String>()

/* ----------------------------------------- */
/*       CREATE SOURCE SET PER VERSION       */
/* ----------------------------------------- */

tasks.withType<ProcessResources> {
    inputs.property("version", project.version)

    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

mcVersions.forEach { ver ->
    val ss = sourceSets.create(ver) {
        java.srcDir("src/$ver/java")

        resources.setSrcDirs(listOf("src/$ver/resources", "src/main/resources"))

        compileClasspath += sourceSets["main"].output
        runtimeClasspath += output + compileClasspath
    }

    tasks.withType<ProcessResources> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    configurations[ss.implementationConfigurationName]
        .extendsFrom(configurations["implementation"])

    configurations[ss.compileOnlyConfigurationName]
        .extendsFrom(configurations["compileOnly"])

}

/* ----------------------------------------- */
/*               DEPENDENCIES                */
/* ----------------------------------------- */

dependencies {
    add("compileOnly", "io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    add("compileOnly", "net.milkbowl.vault:VaultUnlockedAPI:2.16")
//    add("r1_21CompileOnly", "io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
//    add("b1_7_3CompileOnly", files("libs/craftbukkit-1060.jar"))
}

/* ----------------------------------------- */
/*               BUILD TASKS                 */
/* ----------------------------------------- */

mcVersions.forEach { ver ->
    tasks.register<Jar>("jar${ver.replace(".", "_").replace("-", "_").replace("/", "_").capitalize()}") {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(sourceSets["main"].output)
        from(sourceSets[ver].output)
        archiveClassifier.set(ver)

        manifest {
            attributes(
                "itemeco-Impl-Version" to ver,
                "itemeco-Environment" to (System.getenv("ITEMECO_BUILD_CHANNEL") ?: "dev")
            )
        }

    }
}

tasks.register("buildAll") {
    dependsOn(tasks.withType<Jar>())
}

/* ----------------------------------------- */
/*              JAVA SETTINGS                */
/* ----------------------------------------- */

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.runServer {
    minecraftVersion("1.21")
}
