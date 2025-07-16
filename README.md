# OneKeyMiner

[English](README.md) | [中文](README_zh.md)

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-OneKeyMiner?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-OneKeyMiner/releases)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/626666?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/onekeyminer)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/MxjO3Kkh?style=flat-square&logo=modrinth)](https://modrinth.com/mod/onekeyminer)

---

## Overview

**OneKeyMiner** is a Minecraft mod for Fabric that lets you mine multiple connected blocks of the same type with a
single mining action. It streamlines resource gathering and is highly configurable for different playstyles and server
rules.

---

## Features

- Mine large veins of blocks (e.g., ores, logs) with one action
- Configurable block lists for each tool type (axe, hoe, pickaxe, shovel, shears, or any)
- Adjustable mining limit to prevent excessive block breaking
- Toggleable interaction mode for blocks that can be interacted with (e.g., grass, farmland)
- In-game configuration screen (singleplayer/LAN only)
- Customizable keybind for activation (singleplayer only)
- Reloadable config without restarting the game
- Legacy version support (1.7.10+)

---

## Installation

1. **Download the mod:**
    - [CurseForge](https://www.curseforge.com/minecraft/mc-mods/onekeyminer)
    - [Modrinth](https://modrinth.com/mod/onekeyminer)
    - [GitHub Releases](https://github.com/Enaium/fabric-mod-OneKeyMiner/releases)
2. **Install [Fabric Loader](https://fabricmc.net/use/installer/)** for your Minecraft version.
3. **Place the mod JAR** in your `mods` folder.
4. **Launch Minecraft** with the Fabric profile.

---

## Usage

### Commands

| Command                                                                 | Description                                                          | Notes                                  |
|-------------------------------------------------------------------------|----------------------------------------------------------------------|----------------------------------------|
| `/onekeyminer screen`                                                   | Opens the mod's configuration screen                                 | Singleplayer/LAN only                  |
| `/onekeyminer limit`                                                    | Shows the current mining limit                                       | Default: 64                            |
| `/onekeyminer limit <number>`                                           | Sets the max number of blocks mined per action                       |                                        |
| `/onekeyminer <AXE/HOE/PICKAXE/SHOVEL/SHEARS/ANY> list`                 | Lists all blocks in the tool's mining list                           |                                        |
| `/onekeyminer <AXE/HOE/PICKAXE/SHOVEL/SHEARS/ANY> <ADD/REMOVE> <block>` | Adds or removes a block from the tool's list                         | Use block IDs like `minecraft:oak_log` |
| `/onekeyminer interact`                                                 | Shows whether interaction mode is enabled                            | Default: false                         |
| `/onekeyminer interact <true/false>`                                    | Enables/disables interaction mode (e.g., hoeing grass, making paths) |                                        |
| `/onekeyminer reload`                                                   | Reloads the config file                                              |                                        |

**Note:** Setting a very high limit may cause lag or crashes.

### Keybind

- The mod can be activated with a custom key (configurable in-game, singleplayer only).

---

## Legacy Version Notes

| Version       | Command Differences                            | Block ID Format                        |
|---------------|------------------------------------------------|----------------------------------------|
| 1.8+          | `/onekeyminer screen`                          | Subdivided (e.g., `minecraft:oak_log`) |
| 1.7 and below | No config screen command                       | Whole (e.g., `minecraft:log`)          |
| Legacy (all)  | `/onekeyminer screen` is `/onekeyminer-screen` |                                        |

---

## Configuration & Customization

- Edit the config file or use the in-game screen (where available) to:
    - Change mining limits
    - Adjust block lists per tool
    - Toggle interaction mode
    - Set custom keybinds (singleplayer)

---

## Contributing

Contributions, bug reports, and suggestions are welcome! Please open an issue or pull request
on [GitHub](https://github.com/Enaium/fabric-mod-OneKeyMiner).

---

## License

This project is licensed under the [MIT License](LICENSE).
