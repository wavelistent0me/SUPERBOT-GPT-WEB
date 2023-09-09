# 如果项目能帮到你，还请给仓库点下 Star，谢谢！

> 超过 200 个星，就会迭代下一版本，把加强版小程序和加强版 web 整合到一起
> 
> 我们很高兴地宣布，我们已经发布了Web加强版！如果你已经成功部署了我们的产品，请考虑尝试部署这个加强版。它将Web和小程序完美整合，提供更优质的用户体验。  [传送门](https://github.com/dulaiduwang003/TIME-SEA-PLUS)

# 部署教程

> 有问题微信群讨论
>
> 工作繁忙 更新速率会稍微慢一点
>
> 希望大家不要拿去贩卖谢谢！！！
>
> 注意看完教程  
> 记得点个 star😊💕

### 前言

有问题 Wechat ：SeatimeIsland

WEB 部署视频 - 喂饭级别，可成功部署
https://www.bilibili.com/video/BV1FX4y1J7D2/

web 演示视频
https://www.bilibili.com/video/BV1EX4y1C7jE/?spm_id_from=333.999.0.0&vd_source=247eccf88822f409670040957c2f29a9

请作者喝肥皂快乐水 😘
![微信图片_20230414223811](https://user-images.githubusercontent.com/87460202/232085684-b17cb802-2e24-4614-ae06-aea823145310.jpg)

[GitHub 作者主页](https://github.com/dulaiduwang003/ChatGPT_wechat)

## Linux 准备环境 (必须)

- centOS 8 或更高
- MySQL 8
- Redis 7
- OpenJdk 17 以上 （必须）

## 后端部署

1. 创建 MySQL 数据库 取名为 super_web
2. 将项目中的.sql 文件执行到该库中 也就是导入表数据
3. ~ 找到 application-prod.yml 将 SSL 证书放置在同目录下 , 如果使用其他方式配置请忽略(只是为了后期适配小程序) ~

```yaml
## application-prod.yml
server:
  # SSL证书
  ssl:
    key-store: classpath:XXX.pfx
    key-store-password: 证书密码
    key-store-type: PKCS12
```

4. 找到 application-prod.yml 配置好 mysql 以及 redis 中间件 以及管理员邮箱账号

```yaml
## application-prod.yml
server:
  #   SSL证书
  ssl:
    key-store: XXXX.pfx
    key-store-password: XXXXX
    key-store-type: PKCS12

spring:
  data:
    redis:
      database: 4
      host: 服务器ip
      port: 端口号
      password: "redis密码"
  # 这里方式很多
  mail:
    # 这里具体看你 我用的是QQ的
    host: smtp.qq.com
    username: 邮箱
    password: 邮箱授权码
    default-encoding: UTF-8
  datasource:
    url: jdbc:mysql://服务器ip:端口号/super_web?useUnicode=true&serverTimezone=Asia/Shanghai&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true&useSSL=true
    username: mysql账号
    password: "mysql密码"

# 管理员账号
console:
  email: "管理员邮箱 例如 2074055628@qq.com"
  password: "管理员密码"

file:
  ## 图片缓存路径
  path: /apps/chat/resource/
```

6. 这里邮箱以 QQ 邮箱为例 打开设置
   ![1](/static/img.png)
7. 滑到下面可以看到 IMAP/SMTP 服务 一套流程走完后 腾讯会给一个授权码 ，把授权码 填到 步骤 5 中 mail 节点属性下得 邮箱授权码
   ![1](/static/img_1.png)
8. 直接打包 jar 用 宝塔运行 或者 自写一个 dockerfile 部署就行， 如果是 docker 部署 请注意容器内部 ip

## 前端部署

1. 找到 .env.production 如图所示 <br/>
   ![1](/static/img_2.png)
2. 打开 后 把下面域名替换为 自己得域名 如果有端口就设置端口

```yaml
# just a flag
ENV = 'production'

# base api   这里用https是因为后端设置了SSL证书  如果没配 可以不设置
VUE_APP_BASE_API = 'https://服务器域名:端口'


# 另外 请自己手动附上 WSS
路径位于 views目录中的 IndexView.vue 以及 BingView.vue
自己修改成你的 服务器域名和端口

```

3. 设置好后 在项目目录下执行命令安装依赖

   > npm run install

4. 执行打包命令

   > npm run build

5. 打包完成后 会在项目根目录下生成 dist 文件如图所示 <br/>
   ![img.png](/static/img_3.png)

6. 把他扔到 nginx 即可 不懂可以百度 另外需要配置 SSL 证书 不然应为某些浏览器会检查不安全连接时会禁用某些 javascript 参数 会导致 一些功能失效

## 使用部分

1. 点击 logo 切换为管理员登录方式 ，这里填写你之前配置好的管理邮箱账号及密码 <br/>
   ![img.png](/static/img_4.png)

2. 登录成功后显示
   ![img.png](/static/img_5.png)

3. 点击个人中心 找到管理控制台 目前要使用需要配置这三项
   ![img.png](/static/img_6.png)

4. 首先配置 服务器数据
   ![img.png](/static/img_10.png)

   > 服务器策略和之前版本一样 代理 自定义 直连 <br/>
   > 官方密钥填写 OPENKEY 即可 <br/>
   > 官方 API 官网的就行 到 /v1 结尾即可 如 https://xxx/v1/ <br/>
   > 自定义密钥为 第三方 api 密钥 <br/>
   > 自定义 API 为 第三方 API 到 /v1 结尾即可 如 https://xxx/v1/ <br/>
   > Clash 代理 IP 如 127.0.0.1 具体看你配置 <br/>
   > clash 代理端口 如 7892 具体看你配置 <br/>
   > SDAPI 填写如 https://XXXXX/sdapi/v1/txt2img <br/>
   > MJID 填写服务器 ID <br/>
   > MJChannerID 填写频道 ID <br/>
   > BotToken 填写机器人 token <br/>
   > BingCookie 填写 bingcookie <br/>

5. 首先配置运营 填写用户在使用如下功能时得消耗次数
   ![img.png](/static/img_7.png)

6. 滑到下面也有配置 保存即可
   ![img.png](/static/img_8.png)

# 小程序部署

1. 打开 env 配置后端接口以及微信参数即可
   ![img.png](/static/img_9.png)

2. 微信模拟器运行示例
   ![img.png](/static/img_12.png)

## todoList

0. [完成]完善 B 站喂饭级别教程；
1. 移动端管理员页面兼容适配；
2. 管理员页面补充上下文交互
3. 用户端，支持提示词上传和下载，云端管理 json 预设。后期接口请求，支持用户上传提示词;
4. 本地存储会话记录；
5. 为每个对话设置系统 Prompt
6. 允许用户自行编辑内置 Prompt 列表
7. 预制角色：使用预制角色快速定制新对话
8. 分享为图片，分享到 ShareGPT 链接
9. 脚本部署,争取做到半自动或者开箱即用
10. 推进服务端部署 LocalAI 项目 llama / gpt4all / rwkv / vicuna / koala / gpt4all-j / cerebras / falcon / dolly 等等，或者使用 api-for-open-llm
