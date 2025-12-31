# YuuccMCCommandMenu

**YuuccMCCommandMenu** 是一个基于 Fabric 1.20.1 开发的辅助类模组。它通过直观的 GUI 界面，帮助玩家快速管理游戏中的环境设置（时间、天气）以及复杂的游戏规则（Game Rules），无需再手动输入繁琐的指令。

## 🌟 核心功能


**快捷菜单导航**：默认按下 **J 键* 即可唤出主菜单，轻松跳转至各个子模块。



**时间控制**：支持一键切换白天、中午、夜晚和午夜 ，并具备**时间锁定**功能（需配合 Shift 键操作）。



**天气调节**：支持晴天、下雨、雷雨以及降雪 的快速切换与锁定 。


**游戏规则管理 (Game Rules)**：

**可视化列表**：通过分页系统管理多个游戏规则 。



**布尔值开关**：快速切换如 `keepInventory`（保留物品栏）、`mobGriefing`（生物破坏）等规则 。



**数值调节**：循环调节 `randomTickSpeed`（随机刻速度）、`maxEntityCramming`（实体挤压上限）等数值 。





**本地化支持**：目前已完整支持 **简体中文 (zh_cn)** 和 **英语 (en_us)**。



## 🛠️ 安装要求


**Minecraft**: `1.20.1` 



**Fabric Loader**: `>=0.18.4` 



**Fabric API**: `0.92.6+1.20.1` 



## 🚀 使用方法

1. 将编译好的 `.jar` 文件放入 Minecraft 的 `mods` 文件夹中。
2. 进入游戏后，按下键盘上的 **J*键打开主菜单。
3. 点击对应的分类按钮进入设置页面。
4. **关于锁定功能**：在时间或天气菜单中，按住 **Shift 键**并点击锁定按钮 ，模组将每隔 5 秒（100 ticks）自动维护一次当前设置 。



## 📝 开发者说明

该项目使用标准的 Gradle 构建系统：

**构建项目**: 执行 `gradlew build`。

**源码路径**: 逻辑主要分布在 `src/main/java` 和 `src/client/java`  中。


**仓库地址**: [https://github.com/scargallacostavz931-design/YuuccMCCommandMenu](https://github.com/scargallacostavz931-design/YuuccMCCommandMenu)

## ⚖️ 开源协议

本项目采用 **MIT License** 协议授权。

```text
Copyright (c) 2025 Yuucc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
