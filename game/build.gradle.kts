plugins {
    id("cn.enaium.fabric-multi-game")
}

fmg {
    common.set(project(":core"))
}   

val mineconf = libs.versions.mineconf.get()

subprojects {
    apply(plugin = "mod-publish")

    val minecraftVersion = findProperty("minecraft.version")

    val disableObfuscation = findProperty("fabric.loom.disableObfuscation")?.toString()?.toBoolean() ?: false

    dependencies.add(
        if (disableObfuscation) "implementation" else "modImplementation",
        "cn.enaium:mineconf:${minecraftVersion}-${mineconf}"
    )
}