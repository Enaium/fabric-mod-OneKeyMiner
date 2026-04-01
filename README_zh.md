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
- 可自定义激活按键（仅限单人）
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

### 按键绑定

- 可通过自定义按键激活模组（游戏内可配置，仅限单人）。

---

## 配置

模组使用MineConf进行配置.

### 配置项

| 配置                               | 说明                                                          | 备注      |
|----------------------------------|-------------------------------------------------------------|---------|
| limit                            | 每次操作的最大挖掘方块数                                                | 默认：64   |
| axe/hop/pickaxe/shovel/shers/any | 该工具类型的所有可挖掘方块                                               |         |
| interact                         | 交互模式（如锄地、制作路径等）                                             | 默认：关闭   |
| hotkey                           | 热键 （仅单人模式）                                                  | 默认: 打开  |
| shape                            | 矿道的连锁形状 (如HORIZONTAL/VERTICAL_PLANE, COLUMN,SPHERE,TUNNEL等) | 默认：CUEB |

---

## 参与贡献

欢迎贡献代码、报告 Bug 或提出建议！请在 [GitHub](https://github.com/Enaium/fabric-mod-OneKeyMiner) 提交 issue 或 pull
request。

---

## 许可证

本项目采用 [MIT License](LICENSE) 许可。 