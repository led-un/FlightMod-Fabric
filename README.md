# FlightMod (Fabric 26.1.2) - 轻量级飞行+防摔模组

Minecraft Fabric 26.1.2 辅助模组，提供飞行和防摔功能。

> 其他 MC 版本分支： [1.20.1](https://github.com/led-un/FlightMod-Fabric/tree/1.20.1) | [1.21](https://github.com/led-un/FlightMod-Fabric/tree/1.21) | [1.21.1](https://github.com/led-un/FlightMod-Fabric/tree/1.21.1) | [1.21.2](https://github.com/led-un/FlightMod-Fabric/tree/1.21.2) | [1.21.3](https://github.com/led-un/FlightMod-Fabric/tree/1.21.3) | [1.21.4](https://github.com/led-un/FlightMod-Fabric/tree/1.21.4) | [1.21.5](https://github.com/led-un/FlightMod-Fabric/tree/1.21.5) | [1.21.8](https://github.com/led-un/FlightMod-Fabric/tree/1.21.8) | [1.21.10](https://github.com/led-un/FlightMod-Fabric/tree/1.21.10) | [1.21.11](https://github.com/led-un/FlightMod-Fabric/tree/1.21.11) | [26.1](https://github.com/led-un/FlightMod-Fabric/tree/26.1) | [26.1.1](https://github.com/led-un/FlightMod-Fabric/tree/26.1.1) | [26.2](https://github.com/led-un/FlightMod-Fabric/tree/26.2)

> 其他加载器版本： [Forge 1.20.1](https://github.com/led-un/FlightMod-Forge) | [NeoForge 1.21.1](https://github.com/led-un/FlightMod-NeoForge)

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

1. 安装 [Fabric Loader](https://fabricmc.net/) >= 0.18.4
2. 安装 [Fabric API](https://modrinth.com/mod/fabric-api) >= 0.155.2
3. 从 [Releases](https://github.com/led-un/FlightMod-Fabric/releases) 下载 `flightmod-fabric-26.1.2.jar`，放入 `.minecraft/mods/` 目录

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
git clone https://github.com/led-un/FlightMod-Fabric.git -b 26.1.2
cd FlightMod-Fabric
./gradlew build
```

构建输出在 `build/libs/` 目录下。

## 开源协议

MIT License
