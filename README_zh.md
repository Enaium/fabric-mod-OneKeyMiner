# OneKeyMiner

[English](README.md) | [中文](README_zh.md)

[![Version](https://img.shields.io/github/v/tag/Enaium/fabric-mod-OneKeyMiner?label=version&style=flat-square&logo=github)](https://github.com/Enaium/fabric-mod-OneKeyMiner/releases)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/626666?style=flat-square&logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/onekeyminer)
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/MxjO3Kkh?style=flat-square&logo=modrinth)](https://modrinth.com/mod/onekeyminer)

---

## 概述

**OneKeyMiner** 是一个适用于 Fabric 的 Minecraft 模组，允许你通过一次挖掘操作采集多个相连的同类方块。它简化了资源收集过程，并且高度可配置，适合不同的游戏风格和服务器规则。

---

## 特性

- 一次操作挖掘大量相连方块（如矿石、原木）
- 针对每种工具类型（斧头、锄头、镐、铲、剪刀或任意）可配置方块列表
- 可调节的挖掘上限，防止一次性破坏过多方块
- 可切换的交互模式（如草地、农田等可交互方块）
- 游戏内配置界面（仅限单人/局域网）
- 可自定义激活按键（仅限单人）
- 配置可热重载，无需重启游戏
- 支持旧版本（1.7.10+）

---

## 安装方法

1. **下载模组：**
    - [CurseForge](https://www.curseforge.com/minecraft/mc-mods/onekeyminer)
    - [Modrinth](https://modrinth.com/mod/onekeyminer)
    - [GitHub Releases](https://github.com/Enaium/fabric-mod-OneKeyMiner/releases)
2. **安装 [Fabric Loader](https://fabricmc.net/use/installer/)**，选择你的 Minecraft 版本。
3. **将模组 JAR 文件** 放入 `mods` 文件夹。
4. **使用 Fabric 配置文件启动 Minecraft。**

---

## 使用方法

### 指令

| 指令                                                                      | 说明                   | 备注                            |
|-------------------------------------------------------------------------|----------------------|-------------------------------|
| `/onekeyminer screen`                                                   | 打开模组配置界面             | 仅限单人/局域网                      |
| `/onekeyminer limit`                                                    | 显示当前挖掘上限             | 默认：64                         |
| `/onekeyminer limit <number>`                                           | 设置每次操作的最大挖掘方块数       |                               |
| `/onekeyminer <AXE/HOE/PICKAXE/SHOVEL/SHEARS/ANY> list`                 | 列出该工具类型的所有可挖掘方块      |                               |
| `/onekeyminer <AXE/HOE/PICKAXE/SHOVEL/SHEARS/ANY> <ADD/REMOVE> <block>` | 向工具列表添加或移除方块         | 使用如 `minecraft:oak_log` 的方块ID |
| `/onekeyminer interact`                                                 | 显示交互模式是否启用           | 默认：关闭                         |
| `/onekeyminer interact <true/false>`                                    | 启用/禁用交互模式（如锄地、制作路径等） |                               |
| `/onekeyminer hotkey <true/falst>`                                      | 启用/禁用热键 （仅单人模式）      | 默认: 打开                        |
| `/onekeyminer reload`                                                   | 重新加载配置文件             |                               |

**注意：** 设置过高的挖掘上限可能导致卡顿或崩溃。

### 按键绑定

- 可通过自定义按键激活模组（游戏内可配置，仅限单人）。

---

## 旧版本说明

| 版本      | 指令差异                                          | 方块ID格式                    |
|---------|-----------------------------------------------|---------------------------|
| 1.8+    | `/onekeyminer screen`                         | 细分（如 `minecraft:oak_log`） |
| 1.7 及以下 | 无配置界面指令                                       | 整体（如 `minecraft:log`）     |
| 所有旧版本   | `/onekeyminer screen` 为 `/onekeyminer-screen` |                           |

---

## 配置与自定义

- 编辑配置文件或使用游戏内界面（如可用）可：
    - 修改挖掘上限
    - 调整各工具的方块列表
    - 切换交互模式
    - 设置自定义按键（仅限单人）

---

## 参与贡献

欢迎贡献代码、报告 Bug 或提出建议！请在 [GitHub](https://github.com/Enaium/fabric-mod-OneKeyMiner) 提交 issue 或 pull
request。

---

## 许可证

本项目采用 [MIT License](LICENSE) 许可。 