@Suppress("unchecked_cast")
fun getProp(name: String) = project.ext[name] as String
fun DependencyHandler.modIncludeImplementation(url: String) = modImplementation(include(url)!!)

plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

base {
    archivesName.set(getProp("archives_base_name"))
    version = getProp("mod_version")
    group = getProp("maven_group")
}

repositories {
    maven {
        name = "Modmuss50"
        setUrl("https://maven.modmuss50.me/")
        content {
            includeGroup("RebornCore")
            includeGroup("TechReborn")
            includeGroup("teamreborn")
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
    modIncludeImplementation("net.fabricmc.fabric-api:fabric-api:${getProp("fabric_api_version")}")
    modIncludeImplementation("net.fabricmc:fabric-language-kotlin:${getProp("fabric_kotlin_version")}")
    modIncludeImplementation("net.devtech:arrp:${getProp("arrp_version")}")

    modIncludeImplementation("vazkii.patchouli:Patchouli:${getProp("patchouli_version")}")
    modIncludeImplementation("io.github.cottonmc:LibGui:${getProp("libgui_version")}")

    modApi(include("teamreborn:energy:${getProp("tech_reborn_energy_version")}")!!)
}

tasks.processResources {
    inputs.property("version", getProp("mod_version"))

    filesMatching("fabric.mod.json") {
        expand("version" to getProp("mod_version"))
    }
}

loom {
    accessWidenerPath.set(file("src/main/resources/arcanology.accesswidener"))
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
        rename { "${it}_${base.archivesName.get()}"}
    }
}