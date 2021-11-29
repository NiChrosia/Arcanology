import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("unchecked_cast")
fun getProperty(name: String): String {
    return project.ext[name] as String
}

fun DependencyHandler.bundledModImplementation(notation: Any): Dependency? {
    return include(notation)?.let(this::modImplementation)
}

plugins {
    id("fabric-loom") version "0.10-SNAPSHOT"
    kotlin("jvm") version "1.5.30"
    kotlin("plugin.serialization") version "1.5.30"
}

base {
    archivesName.set(getProperty("archives_base_name"))
    version = getProperty("mod_version")
    group = getProperty("maven_group")
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

    maven {
        name = "Modrinth"
        setUrl("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${getProperty("minecraft_version")}")
    mappings("net.fabricmc:yarn:${getProperty("yarn_mappings")}:v2")

    modImplementation("net.fabricmc:fabric-loader:${getProperty("loader_version")}")
    bundledModImplementation("net.fabricmc.fabric-api:fabric-api:${getProperty("fabric_api_version")}")
    bundledModImplementation("net.fabricmc:fabric-language-kotlin:${getProperty("fabric_kotlin_version")}")
    bundledModImplementation("net.devtech:arrp:${getProperty("arrp_version")}")
    modApi("maven.modrinth:nucleus:${getProperty("nucleus_version")}")

    bundledModImplementation("vazkii.patchouli:Patchouli:${getProperty("patchouli_version")}")
    bundledModImplementation("io.github.cottonmc:LibGui:${getProperty("libgui_version")}")

    modApi(include("teamreborn:energy:${getProperty("tech_reborn_energy_version")}")!!)

    implementation("com.googlecode.soundlibs:vorbisspi:1.0.3-1")
}

loom {
    runs {
        create("serverTest") {
            server()
            source(sourceSets.main.get())
            vmArg("-Dfabric-api.gametest=1")
        }
    }
}

tasks {
    "compileJava"(JavaCompile::class) {
        options.encoding = "UTF-8"
        options.release.set(16)
    }

    "compileKotlin"(KotlinCompile::class) {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_16.toString()
        }
    }

    "processResources"(ProcessResources::class) {
        inputs.property("version", getProperty("mod_version"))

        filesMatching("fabric.mod.json") {
            expand("version" to getProperty("mod_version"))
        }
    }

    "jar"(Jar::class) {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}"}
        }
    }
}