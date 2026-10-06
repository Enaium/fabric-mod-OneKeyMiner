import me.modmuss50.mpp.PublishModTask
import org.gradle.util.internal.VersionNumber

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

afterEvaluate {
    publishMods {
        val disableObfuscation = (findProperty("fabric.loom.disableObfuscation") ?: false).toString().toBoolean()
        file = tasks.named<AbstractArchiveTask>(if (disableObfuscation) "jar" else "remapJar").get().archiveFile.get()
        type = STABLE
        displayName = "OneKeyMiner ${project.version}"
        changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
        val version = VersionNumber.parse(findProperty("minecraft.version").toString())
        // Modrinth only takes the fabric loader from 18w43b (1.14) on, so a version below it is published
        // as legacy fabric, the loader such a build is written against anyway.
        val modrinthPublishing = gradle.startParameter.taskNames.any {
            it.substringAfterLast(':').let { name -> name == "publishModrinth" || name == "publishMods" }
        }
        modLoaders.add(
            if (modrinthPublishing && version < VersionNumber.parse("1.14.4")) "legacy-fabric" else "fabric"
        )

        val modern = version >= VersionNumber.parse("1.14")

        curseforge {
            projectId = "626666"
            accessToken = providers.gradleProperty("curseforge.token")
            minecraftVersions.add(property("minecraft.version").toString())
            clientRequired = true
            serverRequired = true
            requires("fabric-language-kotlin", "mineconf", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        modrinth {
            projectId = "MxjO3Kkh"
            accessToken = providers.gradleProperty("modrinth.token")
            minecraftVersions.add(property("minecraft.version").toString())
            requires("fabric-language-kotlin", "mineconf", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        github {
            repository = "Enaium/fabric-mod-OneKeyMiner"
            accessToken = providers.gradleProperty("github.token")
            commitish = "master"
        }

        tasks.withType<PublishModTask>().configureEach {
            dependsOn(tasks.named(if (disableObfuscation) "jar" else "remapJar"))
        }
    }
}