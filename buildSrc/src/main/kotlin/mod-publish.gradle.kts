import me.modmuss50.mpp.PublishModTask
import org.gradle.util.internal.VersionNumber

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

afterEvaluate {
    publishMods {
        file = tasks.named<AbstractArchiveTask>("remapJar").get().archiveFile.get()
        type = STABLE
        displayName = "OneKeyMiner ${project.version}"
        changelog = rootProject.file("changelog.md").readText(Charsets.UTF_8)
        modLoaders.add("fabric")

        val modern = VersionNumber.parse(properties["minecraft.version"].toString()) >= VersionNumber.parse("1.14")

        curseforge {
            projectId = "626666"
            accessToken = providers.gradleProperty("curseforge.token")
            minecraftVersions.add(property("minecraft.version").toString())
            requires("fabric-language-kotlin", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        modrinth {
            projectId = "MxjO3Kkh"
            accessToken = providers.gradleProperty("modrinth.token")
            minecraftVersions.add(property("minecraft.version").toString())
            requires("fabric-language-kotlin", if (modern) "fabric-api" else "legacy-fabric-api")
        }

        github {
            repository = "Enaium/fabric-mod-OneKeyMiner"
            accessToken = providers.gradleProperty("github.token")
            commitish = "master"
        }

        tasks.withType<PublishModTask>().configureEach {
            dependsOn(tasks.named("remapJar"))
        }
    }
}