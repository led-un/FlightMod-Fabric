# FlightMod (Fabric) - 轻量级飞行+防摔模组

Minecraft Fabric 辅助模组，支持 1.20.1 和 1.21.1。

> 需要 Forge 版本？👉 [FlightMod-Forge](https://github.com/led-un/FlightMod-Forge)

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

1. 安装 [Fabric Loader](https://fabricmc.net/) >= 0.14.21 (1.20.1) / >= 0.16.10 (1.21.1)
2. 安装 [Fabric API](https://modrinth.com/mod/fabric-api) >= 0.88.1 (1.20.1) / >= 0.108.0 (1.21.1)
3. 从 [Releases](https://github.com/led-un/FlightMod-Fabric/releases) 下载对应版本的 JAR，放入 `.minecraft/mods/` 目录

## 下载

| MC 版本 | 下载 |
|---------|------|
| 1.20.1 | [flightMod-fabric-1.20.1.jar](https://github.com/led-un/FlightMod-Fabric/releases/tag/1.20.1) |
| 1.21.1 | [flightMod-fabric-1.21.1.jar](https://github.com/led-un/FlightMod-Fabric/releases/tag/1.21.1) |

## 构建

```bash
./gradlew build
# 产物在 build/libs/
```

要求：JDK 17+ (1.20.1) / JDK 21+ (1.21.1)

### 1.21.1 构建注意事项
由于 Loom 内置的 Gson 2.9.1 在 JDK 17+ 上无法反序列化 Record 类型，构建时需要在 `settings.gradle` 的 `buildscript` 中强制使用 Gson 2.10.1：
```groovy
buildscript {
    repositories { mavenCentral() }
    dependencies { classpath "com.google.code.gson:gson:2.10.1" }
}
```

## 项目结构

```
src/main/java/com/github/flightmod/
├── FlightMod.java          # 主入口（ClientModInitializer）
├── KeyHandler.java         # 按键处理（J=飞行, K=防摔）
├── mixin/
│   ├── NoFallMixin.java              # Mixin: 拦截发包修改 onGround
│   └── ServerboundMovePlayerPacketAccessor.java  # Accessor: onGround 字段
└── modules/
    ├── Flight.java         # 飞行模块
    └── NoFall.java         # 防摔模块
```

## 开源协议

MIT License
