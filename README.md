一、项目结构
>>> sph_demo 父级项目 sql文件夹内有项目初始化sql
>>>>>>  cloud_config 配置中心 从git地址:https://github.com/mxdppz0707/cloudConfig 上拉取配置数据
>>>>>>  spring_gateway 网关项目 集成了jwt验证和redis缓存，相关配置从配置中心拉取。web请求的单元测试放置在test目录下
>>>>>>  support_microservice 基础支撑服务 启动时请注意数据库连接配置
>>>>>>  content_microservice 后台管理服务 启动时请注意数据库连接配置
>>>>>>  app_web app服务端服
>>>>>>  admin_web admin后端管理服务端

二、启动顺序
cloud_confi -> spring_gateway -> support_microservice | content_microservice | app_web app | admin_web admin

三、注意事项
1.jdk版本1.8
2.注册中心使用 eureka,项目启动前，请先下载eureka服务端并启动
3.缓存使用redis，请先下载redis并启动，默认端口号 6379
