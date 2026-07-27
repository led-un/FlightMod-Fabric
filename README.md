# FlightMod (Fabric 1.20.1) - 轻量级飞行+防摔模组

Minecraft Fabric 1.20.1 辅助模组。

> 需要 Fabric 1.21.1？切换到 [1.21.1 分支](https://github.com/led-un/FlightMod-Fabric/tree/1.21.1)

> 其他加载器版本： [Forge 1.20.1](https://github.com/led-un/FlightMod-Forge) | [NeoForge 1.21.1](https://github.com/led-un/FlightMod-NeoForge)

## 功能

| 按键 | 功能 | 说明 |
|------|------|------|
| **J** | 开关飞行 | 使用 Abilities 模式实现飞行，非创造模式下也可飞行 |
| **K** | 开关防摔 | 修改发包的 onGround 字段，服务器认为你始终在地面，不会造成摔落伤害 |

## 防摔模式说明

防摔模式（NoFall）有两种工作方式：

- **PACKET 模式（默认）**：通过 Mixin 拦截 `ClientPacketListener.send()` 方法，在玩家下落时将 `ServerboundMovePlayerPacket` 的 `onGround` 字段设为 `true`。服务器收到后认为玩家在地面，不会计算摔落伤害。
- **SIMPLE 模式**：在客户端 tick 时直接重置 `fallDistance`。注意：此模式在纯客户端修改，部分服务器的反作弊可能不认可。

## 安装

1. 安装 [Fabric Loader](https://fabricmc.net/) >= 0.14.21
2. 安装 [Fabric API](https://modrinth.com/mod/fabric-api) >= 0.88.1
3. 从 [Releases](https://github.com/led-un/FlightMod-Fabric/releases) 下载 `flightMod-fabric-1.20.1.jar`，放入 `.minecraft/mods/` 目录

## 下载

| MC 版本 | 分支 | 下载 |
|---------|------|------|
| **1.20.1** | `main`（当前） | [flightMod-fabric-1.20.1.jar](https://github.com/led-un/FlightMod-Fabric/releases/tag/1.20.1) |
| 1.21.1 | `1.21.1` | [flightMod-fabric-1.21.1.jar](https://github.com/led-un/FlightMod-Fabric/releases/tag/1.21.1) |

## 与 1.21.1 版本的源码差异

| 差异项 | 1.20.1 (`main`) | 1.21.1 (`1.21.1`) |
|--------|-----------------|---------------------|
| NoFallMixin 目标 | `ClientPacketListener.send(Packet)` | `Connection.send(Packet, PacketSendListener, boolean)` |
| Loom 版本 | `1.3.+` | `1.10.+` |
| Java 要求 | JDK 17 | JDK 21 |
| Fabric Loader | >= 0.14.21 | >= 0.16.10 |
| Fabric API | >= 0.88.1 | >= 0.108.0 |
| Gson workaround | 不需要 | `settings.gradle` 中强制 Gson 2.10.1 |

> 业务逻辑代码（`Flight.java`、`NoFall.java`、`KeyHandler.java`、`FlightMod.java`）两个版本完全一致。

## 构建

```bash
./gradlew build
# 产物在 build/libs/
```

要求：**JDK 17+**

## 项目结构

```
src/main/java/com/github/flightmod/
├── FlightMod.java                              # 主入口（ClientModInitializer）
├── KeyHandler.java                             # 按键处理（J=飞行, K=防摔）
├── mixin/
│   ├── NoFallMixin.java                        # Mixin: 拦截 ClientPacketListener.send()
│   └── ServerboundMovePlayerPacketAccessor.java # Accessor: onGround 字段
└── modules/
    ├── Flight.java                             # 飞行模块
    └── NoFall.java                             # 防摔模块
```

## 开源协议

MIT License
