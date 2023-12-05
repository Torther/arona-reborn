# 更新日志

## v1.1.4
2023-11-20

1. 添加对mirai 2.16.0的支持
2. 修复部分图楼图片获取失败的问题
3. 回滚活动图片生成方式，尝试修复 [#56](https://github.com/diyigemt/arona/issues/56) [#57](https://github.com/diyigemt/arona/issues/57)
4. 修复数据同步失败的问题 [#49](https://github.com/diyigemt/arona/issues/49) [#52](https://github.com/diyigemt/arona/issues/52)

## v1.1.3
2023-09-18

1. 修复防侠没有推送的问题 [#39](https://github.com/diyigemt/arona/issues/39)
2. 添加内置代理方便海外使用 [#45](https://github.com/diyigemt/arona/issues/45)
3. nga转发添加分群过滤uid功能

`nga.yml`: 新增配置参数，允许海外用户设置回国代理

`groupUidFilterMap`: 分群过滤器，具体配置访问文档：https://doc.arona.diyigemt.com/config/base-config#nga-yml

## v1.1.2
2023-08-17

1. nga图楼多群转发增加可配的间隔 [#37](https://github.com/diyigemt/arona/issues/37)
2. 更新nga图楼转发匹配 [#38](https://github.com/diyigemt/arona/issues/38)
3. nga图楼转发提供[合并转发开关](https://doc.arona.diyigemt.com/v1/config/base-config#nga-yml)
4. `/活动`指令重新支持 `/活动 日服|国际服|国服`

## v1.1.1
2023-08-09

1. 修复没有依赖aarch64的问题 [#34](https://github.com/diyigemt/arona/issues/34)
2. 修复与[mirai-skia-plugin@1.3.2](https://github.com/cssxsh/mirai-skia-plugin)冲突的问题 [#35](https://github.com/diyigemt/arona/issues/35)
3. `/活动`指令支持国服
4. [每日防侠预警支持分群生效](https://doc.arona.diyigemt.com/v1/config/base-config#arona-notify-yml)

## v1.1.0
2023-07-13

1. 修复schaledb数据源url变更的问题 [#29](https://github.com/diyigemt/arona/issues/29)
2. 更改图片生成方式
3. 删除`/攻略 阿罗娜`的默认返回值

## v1.0.14
2023-02-14

1. 修复某些情况下数据库初始化失败导致服务失效的问题
2. 修复服务关闭时不断复读服务未开启的问题
3. 攻略指令新增功能
4. 移除额外的拼音库依赖
5. 攻略指令**不再**提供本地模糊搜索结果

## v1.0.13
2022-10-26

1. 修复某些情况下在配置了塔罗牌一日一次后抽取结果正逆位错误的问题

## v1.0.12
2022-10-22

1. 修复学生生日信息会随着时间增加的问题
2. 修复某些情况下数据库字段过短的问题
3. `/塔罗牌`指令结果会同时附上P站[@Shi0n老师](https://www.pixiv.net/users/4150140)绘制的BA版塔罗牌图片
4. 静态资源使用CDN加速，优化各种图片第一次加载的速度

## v1.0.11
2022-10-07

1. 攻略指令模糊搜索功能优化
2. 修复某些情况下攻略指令覆写配置读取乱码的问题

## v1.0.10
2022-10-02

1. 攻略指令的别名覆写配置支持一对多
2. 修复N2H2防侠时间错误的问题
3. 修复配置文件中机器人运行的qq与实际不一样时仍会回应的问题
4. 攻略指令提供模糊搜索建议功能
5. 添加配置项arona-trainer.yml -> tipWhenNull 允许用户配置是否启用`/攻略`指令的模糊搜索提示
6. 添加配置项arona-trainer.yml -> fuzzySearchSource 允许用户配置`/攻略`指令的模糊搜索来源
7. 攻略指令的别名支持多对一配置
8. 新增公告功能，可以接收作者的公告
9. 新增卡池自动更新功能，可以接收懒狗作者根据新池子内容自动更新本地抽卡数据库
10. 添加配置项arona.yml -> remoteCheckInterval 允许用户配置**远端**功能的开启与否
11. 攻略图片下载失败时将会提供反馈信息
12. 修复某些情况下无法获取学生生日信息导致`/活动`指令失效的问题
13. 修复在服务器环境下部署时系统中没有中文字体导致`/活动`指令结果中文乱码的问题
14. 新增特殊配置文件`./data/net.diyigemt.arona/config/trainer_config.yml`允许用户在`mirai-console`运行时修改`/攻略`指令的别名覆写配置
15. 添加指令`/抽卡 list`可查看最近两个池子的配置
16. 后端服务地址转移到国内的服务器上，优化`/攻略`指令第一次下载图片时的速度

## v1.0.9
2022-09-13

1. 新增配置文件arona-trainer.yml，为`/攻略`指令提供别名复写功能
2. 修复v1.0.8版本中初始化失败导致服务失效的严重Bug

## v1.0.9
2022-09-13

1. 新增配置文件arona-trainer.yml，为`/攻略`指令提供别名复写功能
2. 修复v1.0.8版本中初始化失败导致服务失效的严重Bug

## v1.0.8
2022-09-12

1. 删除配置文件arona-gacha-limit.yml，原有的配置项移动到arona-gacha.yml配置文件中
2. 移除配置项arona-gacha->revoke，将功能移动到arona-gacha->revokeTime中
3. 将抽卡限制记录信息改用数据库存储
4. 抽卡配置指令支持配置1 2 3 星和pick up出货率，并可以修改每日限制次数和撤回时间间隔
5. 添加游戏名记录功能，方便对社团进行管理
6. 修复抽卡限制没有刷新的问题
7. 国际服信息来源新增GameKee，并推荐使用该信息源
8. `/攻略` 指令新增部分夜喵杂图，涵盖了基本所有的一图流攻略
9. 修复抽卡历史排行名称错误的问题
10. 攻略指令图片将会在本地data文件夹下进行缓存，加快发送速度并一定程度上防止后端崩了发不出图片的问题

## v1.0.7-M1
2022-08-29

1. 删除配置项arona-notify->dropNotify 双倍掉落结束时间将固定为晚上22点整
2. 国际服活动信息来源新增[SchaleDB](https://lonqie.github.io/SchaleDB/)，来源更加稳定
3. 活动信息新增学生生日信息
4. 添加指令`/攻略<string>`，提供目前主线所有地图(1-1到H19-3)走格子的图文攻略以及巴哈姆特@夜喵貓貓咪喵(asaz5566a)对于目前日服所有已实装学生的评价

## v1.0.6
2022-08-26

1. 添加配置项nga.yml->source，可选择nga的数据来源，紧急修复nga主站(ngabbs.com)炸了的问题，默认选择副站

## v1.0.5
2022-08-21

1. 添加配置项arona.yml->endWithSensei允许用户设置称呼系统的后缀，默认是"老师"(保留人设)
2. 添加指令`/叫我 <string>`允许给自己自定义昵称，阿罗娜将会在抽卡、摸头中称呼你
3. 活动推送现在将会使用图片了，比之前的纯文字版好了不少

## v1.0.4
2022-08-10

1. 修复防侠失效的问题

## v1.0.3
2022-08-07

1. 修复nga推送在缓存刷新后重新推送的问题
2. 修复国际服pickup没有特殊名称的问题

## v1.0.2
2022-08-02

1. 修复nga推送刷新cache后不更新cacheday导致一直发送图楼信息的问题
2. 修复国际服双倍掉落时间获取不到的问题
3. 修复在群聊中执行exit命令时错误发送停止信息
4. 添加紧急停止命令，使用投票制在管理员不在的情况下紧急停止服务

## v1.0.1
2022-07-31

1. 修复nga图楼推送由于卡审核跳过某些楼层的问题
2. 日服推送新增配置项defaultJPActivitySource，允许用户指定默认数据源
3. 修复塔罗牌在某些情况下同一天结果不一致的问题

## v1.0.0
2022-07-21

1. 修复一些由于wikiru规则更新导致的日服活动信息乱码的问题

## v1.0.0-M1
2022-07-19

1. 添加新配置项(arona-gacha.yml -> revokeTime) 允许用户配置抽卡结果撤回时间
2. 防侠预警添加维护预警
3. 修复在某些情况下防侠预警不在整点发送的Bug
4. 新增配置项(arona-notify.yml -> defaultActivityCommandServer) 允许用户配置"/活动"指令的默认目标服务器
5. 新增配置项(arona.yml -> managerGroup)允许用户配置管理员以便在线更改服务配置
6. 新增配置项(arona.yml -> permissionDeniedMessage)允许用户配置非机器人管理员在尝试执行管理员权限的指令时回复的消息
7. 新增配置项(arona.yml -> autoCheckUpdate)配置arona是否自动检查更新
8. 新增配置项(arona.yml -> updateCheckTime)配置arona每天自动检查更新的时间
9. 新增配置项(arona.yml -> sendStatus)配置是否允许arona收集匿名统计信息(仅包括计算机名、第一次开启时间和总使用时长（暂不开启）
10. 移除各配置文件中的enable配置项
11. 新增配置文件(arona-service.yml)统一管理各模块的开关
12. 新增功能->NGA图楼推送
13. 新增功能->每日塔罗牌
14. 日服防侠预警功能添加部分翻译
15. 所有涉及可编辑回复的内容支持mirai-code(也就是可以回复图片语音之类的)

## v0.1.1
2022-07-05

修复国际服总力战没有结束时间的问题 
