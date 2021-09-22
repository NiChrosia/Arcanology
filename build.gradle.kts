@Suppress("unchecked_cast")
fun getProp(name: String) = project.ext[name] as String
fun DependencyHandler.modIncludeImplementation(url: String) = modImplementation(include(url)!!)

plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

base {
    archivesBaseName = getProp("archives_base_name")
    version = getProp("mod_version")
    group = getProp("maven_group")
}

repositories {
    maven {
        name = "Technici4n"
        setUrl("https://raw.githubusercontent.com/Technici4n/Technici4n-maven/master/")
        content {
            includeGroup("net.fabricmc.fabric-api") // until PR # is merged
            includeGroup("dev.technici4n")
        }
    }

    maven {
        name = "BuildCraft"
        setUrl("https://mod-buildcraft.com/maven")
    }

    maven {
        name = "Patchouli"
        setUrl("https://maven.blamejared.com")
        content {
            includeGroup("vazkii.patchouli")
        }
    }

    maven {
        setUrl("https://storage.googleapis.com/devan-maven/")
    }

    maven {
        name = "CottonMC"
        setUrl("https://server.bbkr.space/artifactory/libs-release")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${getProp("minecraft_version")}")
    mappings("net.fabricmc:yarn:${getProp("yarn_mappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${getProp("loader_version")}")
    modIncludeImplementation("net.fabricmc.fabric-api:fabric-api:${getProp("fabric_version")}")
    modIncludeImplementation("net.fabricmc:fabric-language-kotlin:${getProp("fabric_kotlin_version")}")
    modIncludeImplementation("net.devtech:arrp:${getProp("arrp_version")}")

    modApi(include("dev.technici4n:FastTransferLib:${getProp("ftl_version")}")!!)
    modIncludeImplementation("vazkii.patchouli:Patchouli:${getProp("patchouli_version")}")
    modIncludeImplementation("io.github.cottonmc:LibGui:${getProp("libgui_version")}")
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    options.release.set(16)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_16.toString()
    }
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesBaseName}"}
    }
}