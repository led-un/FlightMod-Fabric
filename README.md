# FlightMod (Fabric) - 轻量级飞行+防摔模组

Minecraft Fabric 辅助模组。

> 需要 Forge 版本？👉 [FlightMod-Forge](https://github.com/led-un/FlightMod-Forge)
> 需要 NeoForge 版本？👉 [FlightMod-NeoForge](https://github.com/led-un/FlightMod-NeoForge)

## 功能

| 按键 | 功能 | 说明 |
|------|------|------|
| **J** | 开关飞行 | 使用 Abilities 模式实现飞行，非创造模式下也可飞行 |
| **K** | 开关防摔 | 修改发包的 onGround 字段，服务器认为你始终在地面，不会造成摔落伤害 |

## 防摔模式说明

防摔模式（NoFall）有两种工作方式：

- **PACKET 模式（默认）**：通过 Mixin 拦截 `Connection.send()` 方法，在玩家下落时将 `ServerboundMovePlayerPacket` 的 `onGround` 字段设为 `true`。服务器收到后认为玩家在地面，不会计算摔落伤害。
- **SIMPLE 模式**：在客户端 tick 时直接重置 `fallDistance`。注意：此模式在纯客户端修改，部分服务器的反作弊可能不认可。

## 安装

1. 安装 [Fabric](https://fabricmc.net/) 对应 MC 版本
2. 从 [Releases](https://github.com/led-un/FlightMod-Fabric/releases) 下载对应版本的 JAR，放入 `.minecraft/mods/` 目录

## 下载

| MC 版本 | 下载 |
|---------|------|
| 1.20.1 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.20.1) |
| 1.21 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21) |
| 1.21.1 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.1) |
| 1.21.2 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.2) |
| 1.21.3 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.3) |
| 1.21.4 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.4) |
| 1.21.5 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.5) |
| 1.21.8 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.8) |
| 1.21.10 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.10) |
| 1.21.11 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v1.21.11) |
| 26.1 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v26.1) |
| 26.1.1 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v26.1.1) |
| 26.1.2 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v26.1.2) |
| 26.2 | [Releases](https://github.com/led-un/FlightMod-Fabric/releases/tag/v26.2) |

## 构建

```bash
git clone https://github.com/led-un/FlightMod-Fabric.git -b <mc_version>
cd FlightMod-Fabric
./gradlew build
# 产物在 build/libs/
```

| MC 版本 | JDK |
|---------|-----|
| 1.20.1 | JDK 17 |
| 1.21.x | JDK 21 |
| 26.x | JDK 25 |

## 项目结构

```
src/main/java/com/github/flightmod/
├── FlightMod.java                              # 主入口（ClientModInitializer）
├── KeyHandler.java                             # 按键处理（J=飞行, K=防摔）
├── mixin/
│   ├── NoFallMixin.java                        # Mixin: 拦截 Connection.send()
│   └── ServerboundMovePlayerPacketAccessor.java # Accessor: onGround 字段
└── modules/
    ├── Flight.java                             # 飞行模块
    └── NoFall.java                             # 防摔模块
```

## 开源协议

[MIT License](https://opensource.org/licenses/MIT)
