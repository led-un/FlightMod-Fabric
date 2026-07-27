# FlightMod - 轻量级飞行+防摔模组

Minecraft 1.20.1 辅助模组，支持 Fabric 和 Forge 双平台。

## 功能

| 按键 | 功能 | 说明 |
|------|------|------|
| **J** | 开关飞行 | 使用 Abilities 模式实现飞行，非创造模式下也可飞行 |
| **K** | 开关防摔 | 修改发包的 onGround 字段，服务器认为你始终在地面，不会造成摔落伤害 |

## 防摔模式说明

防摔模式（NoFall）有两种工作方式：

- **PACKET 模式（默认）**：通过 Mixin 拦截 `ClientPacketListener.send()` 方法，在玩家下落时将 `ServerboundMovePlayerPacket` 的 `onGround` 字段设为 `true`。服务器收到后认为玩家在地面，不会计算摔落伤害。
- **SIMPLE 模式**：在客户端 tick 时直接重置 `fallDistance`。注意：此模式在纯客户端修改，部分服务器的反作弊可能不认可。

修复了原版防摔模式的两个 bug：
1. `ServerboundMovePlayerPacket` 是抽象类，有 `Pos`、`PosRot`、`StatusOnly` 三个子类，其中 `StatusOnly` 没有 `onGround` 字段，原代码未处理导致崩溃
2. 移除了 `sun.misc.Unsafe` 黑魔法（高版本 Java 已封禁），改用标准反射 API

## 安装

### Fabric
1. 安装 [Fabric Loader](https://fabricmc.net/) >= 0.14.21
2. 安装 [Fabric API](https://modrinth.com/mod/fabric-api) >= 0.88.1
3. 将 `flightmod-fabric-1.20.1-1.0.0.jar` 放入 `.minecraft/mods/` 目录

### Forge
1. 安装 [Forge](https://files.minecraftforge.net/) 47.x (Minecraft 1.20.1)
2. 将 `flightmod-forge-1.20.1-1.0.0.jar` 放入 `.minecraft/mods/` 目录

## 构建

### Fabric
```bash
cd flightmod-fabric
./gradlew build
# 产物在 build/libs/
```

### Forge
```bash
cd flightmod-forge
./gradlew build
# 产物在 build/libs/
```

要求：JDK 17+

## 项目结构

```
src/main/java/com/github/flightmod/
├── FlightMod.java          # 主入口（Fabric: ClientModInitializer / Forge: @Mod）
├── KeyHandler.java         # 按键处理（J=飞行, K=防摔）
├── mixin/
│   └── NoFallMixin.java    # Mixin: 拦截发包修改 onGround
└── modules/
    ├── Flight.java         # 飞行模块
    └── NoFall.java         # 防摔模块
```

## 版本历史

### v1.0.0
- 移除「切换飞行模式」(L键)，飞行统一使用 Abilities 模式
- 修复防摔模式 PACKET 模式的兼容性问题
- 支持 Fabric 1.20.1 和 Forge 1.20.1

## 开源协议

MIT License
