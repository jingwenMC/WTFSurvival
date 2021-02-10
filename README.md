# WTFSurvival
一个生存小游戏  
  
当前正式版本：`1.1.1-RELEASE`  
暂无 快照版本

## 简介
该插件将会进行一系列的监听动作，从而改变游戏内的一些机制。  
（说不出其他的来了，淦）

## 权限
权限节点|默认拥有|说明
----|----|----
wtfs.login|OP|允许使用`/wtfs login`登录Bilibili账号
wtfs.admin|OP|允许控制游戏的开始和停止，插件的重载，以及报告的生成

## 指令
指令|所需权限|说明
----|----|----
/wtfs help|无|打开插件帮助页面
/wtfs start|wtfs.admin|开始一场游戏，参与玩家为当前在服务器内的玩家
/wtfs end|wtfs.admin|结束正在进行的游戏
/wtfs reload|wtfs.admin|重载插件配置文件以及语言文件
/wtfs report|wtfs.admin|在插件目录生成系统报告
/wtfs login|wtfs.login|登录用于获取数据的Bilibili账号

## 声明
由于Bilibili平台的限制，本插件必须登录Bilibili账号才能获取数据。
本插件不会以任何方式收集、上传账号数据至第三方。  
本插件采用GPL-3.0许可证进行许可，具体条款请见 [此处](https://github.com/jingwenMC/WTFSurvival/blob/master/LICENSE) 。

## 鸣谢
感谢 [JetBrains s.r.o.](https://www.jetbrains.com/?from=WTFSurvival) 为本项目提供的IDE。
[![jetbrains-variant-4.png](https://i.loli.net/2020/11/12/ZoW8CwaOEqk12Fj.png)](https://www.jetbrains.com/?from=WTFSurvival)  

感谢 [肥皂不宅](https://space.bilibili.com/8689128/) 在本插件开发时提供的大力支持。
