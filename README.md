# <a name="spring_cloud_top">Spring Cloud微服务架构入门教程</a>
<img src="/SpringCloud.png"  alt="无法显示该图片" />

### By Gary

---
# Index：
### <a href="#chapter1">1.MicroService</a>
### <a href="#chapter2">2.Spring Cloud & Spring Boot</a>
### <a href="#chapter3">3.Eureka</a>
### <a href="#chapter4">4.Ribbon & Feign</a>
### <a href="#chapter5">5.Hystrix & Hystrix Dashboard & Turbine</a>
### <a href="#chapter6">6.Zipkin & Sleuth</a>
### <a href="#chapter7">7.Zuul</a>
### <a href="#chapter8">8.Spring Cloud Config</a>
### <a href="#chapter9">9.Spring Cloud Bus & RabbitMQ</a>
### <a href="#chapter10">10.SideCar</a>
### <a href="#chapter11">11.总结</a>
### <a href="#chapter12">12.引用说明</a>

---
# <a name="chapter1">1.MicroService</a>
## 1.1 微服务架构：
### 微服务架构是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务与服务间采用轻量级的通信机制互相沟通（通常是基于HTTP协议的RESTful API）。每个服务都围绕着具体业务进行构建，并且能够被独立的部署到生产环境、类生产环境等。另外，应当尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言，应根据业务上下文，选择合适的语言、工具对其进行构建。——Martin Fowler
### 总结：
- 小, 且专注于做一件事情
- 独立的进程中
- 轻量级的通信机制
- 松耦合、独立部署

### 1.1.1 轻量级的通信机制：
### 微服务各服务之间使用“轻量级”的通信机制，所谓轻量级是指通信协议与语言无关、与平台无关。微服务通信方式：
- 同步通信方式：RPC、REST等
	- 优点：
		- 1.实现方便
		- 2.协议通用，比如HTTP
		- 3.系统架构简单，无需中间件代理 
	- 缺点：
		- 1.客户端耦合服务方
		- 2.通信双方必须同时在线，否则会造成阻塞
		- 3.客户端需要知道服务方的Endpoint，或者需要支持服务发现机制
- 异步通信方式：消息队列
	- 优点：
		- 1.系统解耦和
		- 2.通信双方可以互相对对方无感知 
	- 缺点：
		- 1.额外的编程复杂性。比如需要考虑消息可靠传输、高性能，以及编程模型的变化等
		- 2.额外的运维复杂性。比如消息中间件的稳定性、高可用性、扩展性等非功能特性

## 1.2 架构演变：单机（集中式）架构->集群架构->面向服务架构（SOA）->微服务架构（MSA）
### 1.2.1 单机（集中式）架构
### 一个项目包含了所有业务模块，部署到一台服务器上
- 单机：单个独立的系统实例化部署
- 集中式：集中的应用（Application）、文件（DATA）、数据（File）

### 1.2.2 集群架构
### 单机处理到达瓶颈，将单机上的项目复制部署到其他几台服务器上，这就是集群。每一台服务器就是该集群的一个节点，每个节点提供的服务都是一样的。集群需要配合负载均衡服务器一起使用，负载均衡服务器作用在于按每个节点的负载情况分配用户请求，以达到每个节点负载平衡

### 1.2.3 面向服务架构（SOA）
### SOA是一种粗粒度、松耦合服务架构，服务之间通过简单、精确定义接口进行通讯。SOA只是一种架构设计模式，而SOAP、REST、RPC是根据这种设计模式构建出来的规范，其中SOAP通俗理解就是Http+Xml的形式，REST就是Http+Json的形式，RPC是基于Socket的形式。CXF就是典型的SOAP/REST框架，Dubbo就是典型的RPC框架，而Spring Cloud就是遵守REST规范的生态系统。（一般会对拆解出来的各个子系统，每个再做集群部署）

### 1.2.4 微服务架构（MSA）
### 微服务其实就是随着互联网的发展，复杂的平台、业务的出现，导致SOA架构向更细粒度的发展。微服务不再强调传统SOA架构里面比较重的ESB企业服务总线。微服务把所有的“思考”逻辑包括路由、消息解析等放在服务内部，去掉一个大一统的ESB，服务间轻通信，是比SOA更彻底的拆分。

### 从部署角度来看：
- 集中式：集中式系统是指由一台或多台主计算机组成中心节点，数据集中存储于这个中心节点中，并且整个系统的所有业务单元都集中部署在这个中心节点上，系统所有的功能均由其集中处理
- 集群：多台服务器部署相同应用构成一个集群
- 分布式：不同模块部署在不同服务器上。分布式系统是一个硬件或软件组件分布在不同的网络计算机上，彼此之间仅仅通过消息传递进行通信和协调的系统。（服务化架构使搭建分布式系统成为了可能）

---
# <a name="chapter2">2.Spring Cloud & Spring Boot</a>
## 2.1 Spring Cloud
### 2.1.1 Spring Cloud介绍：
### Spring Cloud是一组基于Spring Boot封装的微服务框架，提供了完整的分布式系统解决方案。它为基于JVM的云应用开发中涉及的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等操作提供了一种简单的开发方式
### Spring Cloud Netflix项目是Spring Cloud的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为Spring Boot应用提供了自配置的Netflix OSS整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（Eureka），断路器（Hystrix），智能路由（Zuul），客户端负载均衡（Ribbon）等
![](https://i.imgur.com/YeW6lo5.png)

### 2.1.2 Spring Cloud版本命名：
![](https://i.imgur.com/ijzwPdf.png)

- 按伦敦站点，迭代发布版本Angel -> Brixton -> Camden [卡姆登区]-> Dalston– [多尔斯顿]
- 每个版本修复一轮严重 bug ，就会发布一个 “service releases” 版本，用 .SRX 来表示，X 是版本号
### 2.1.2 Spring Cloud架构图：
![](https://i.imgur.com/BmBQ2tQ.png)

### 2.1.3 Spring Cloud启动过程：
### 1.实例化SpringApplication
- 加载webEnvironment
- 从spring.factories中加载所有可用的ApplicationContextInitializer
- 从spring.factories中加载所有可用的ApplicationListener
- 设置main方法的定义类

### 2.执行run方法
- 从spring.factories中加载所有可用的SpringApplicationRunListener发起事件，listener响应事件
- Listener响应prepareEnvironment事件
- 设置是否打印banner
- 创建应用上下文，spring容器IOC
- prepareContext()，initializer初始化
- refreshContext()，启动tomcat
- afterRefresh()，获取所有的runner并执行run方法，例如spring batch的JobLauncherCommandLineRunner
- Listener响应context的finish事件

## 2.2 Spring Boot
### 2.2.1 Spring Boot介绍：
### 针对基于spring 框架的web项目而言，基本需要如下几步：
- 创建一个java web项目
- 下载第三方相关库(手动或maven下载jar)
- 配置web.xml（dispatcherSevlet、log、编码、session、mapping等等）
- 配置spring及MVC九大组件中需要的部分
- 开发业务
- 开发非业务功能（如：安全、健康检查）
- 下载安装并配置tomcat
- 构建war
- 部署项目到tomcat
### Spring Boot是一个用来简化搭建Spring应用以及简化其开发过程的框架，简化的内容整体大概为如下几块：
- **简化依赖**：通过maven中一个spring-boot-starter-xxx就可以把需要的功能模块所有指定版本的依赖包全部引入
- **简化容器**：通过maven中一个spring-boot-starter-web配置就可以引入一个内置的tomcat
- **简化配置**：通过@EnableAutoConfiguration就可以让spring boot智能化自动配置相应模块（需要classPath引入对应模块的jar来配合）；通过@Configuration来减少甚至完全消除对xml的依赖
- **提供通用组件**：提供常用的监控功能

### 2.2.2 Spring Boot简单使用：
### 2.2.2.1 修改pom文件：
![](https://i.imgur.com/C9BszDu.png)
![](https://i.imgur.com/8o3lYlc.png)
### 2.2.2.2 配置文件：
![](https://i.imgur.com/Pwm9jBO.png)
### 2.2.2.3 添加注解：
![](https://i.imgur.com/IvIT7dE.png)
### 简化容器：
![](https://i.imgur.com/RkLhd13.png)

### 独立进程：
![](https://i.imgur.com/dlmMOn9.png)

### 2.2.3 Spring Boot Admin介绍：
### 是在Spring Boot Actuator的基础上提供简洁的可视化WEB UI用来管理Spring Boot应用程序，提供如下功能：
- 显示name/id和版本号
- 显示在线状态
- Logging日志级别管理
- JMX beans管理
- Threads会话和线程管理
- Trace应用请求跟踪
- 应用运行参数信息，如：
	> Java系统属性
	
	> Java环境变量属性
	
	> 内存信息
	
	> Spring 环境属性

### 2.2.4 Spring Boot Admin简单使用：
### 2.2.4.1 添加依赖：
![](https://i.imgur.com/7ukchBt.png)
### 2.2.4.2 配置文件：
![](https://i.imgur.com/7eDbFIP.png)
### 2.2.4.3 添加注解：
![](https://i.imgur.com/MbmQwAU.png)

### 2.2.5 Spring Boot Admin WebUI：
![](https://i.imgur.com/m0xPmeA.png)

### 消息通知：
![](https://i.imgur.com/RyGfytC.png)

### 应用运行详细参数信息：
![](https://i.imgur.com/2Ob8MJE.png)
![](https://i.imgur.com/3BPbYQZ.png)
![](https://i.imgur.com/m2u2PkM.png)
![](https://i.imgur.com/ZhsHCcK.png)

### Logging日志级别管理：
![](https://i.imgur.com/wCMuovB.png)

### Threads会话和线程管理：
![](https://i.imgur.com/qpikatj.png)

### Trace应用请求跟踪：
![](https://i.imgur.com/jcfJM7S.png)

---
# <a name="chapter3">3.Eureka服务注册与发现</a>
### 3.1 Eureka介绍：
- Eureka以RESTful API的方式为服务实例提供了注册、管理和查询等操作
- 可以运行多个Eureka实例构建集群达到高可用性
- Eureka Server提供可视化的监控页面
- Spring Cloud为服务治理做了一层抽象接口，所以在Spring Cloud应用中可以支持多种不同的服务治理框架，比如：Netflix Eureka、Consul、Zookeeper

![](https://i.imgur.com/0MOUcf2.png)

### 3.2 Eureka简单使用：
### 3.2.1 添加依赖：
![](https://i.imgur.com/5ImANns.png)
### 3.2.2 配置文件：
![](https://i.imgur.com/ArpLdDp.png)
### 3.2.3 添加注解：
![](https://i.imgur.com/7ITZCpV.png)

### 3.3 Eureka Server Web UI介绍：
![](https://i.imgur.com/kQnbw8r.png)
![](https://i.imgur.com/5ZQinxv.png)

### 3.4 Eureka原理：
### Eureka包括Eureka Server和Eureka Client，Eureka client再分为Service Provider和Service Consumer：
- **Eureka Server**：服务的注册中心，负责维护注册的服务列表
- **Service Provider**：服务提供方，作为一个Eureka Client，向Eureka Server做服务注册、续约和下线等操作，注册的主要数据包括服务名、机器ip、端口号、域名等
- **Service Consumer**：服务消费方，作为一个Eureka Client，向Eureka Server获取Service Provider的注册信息，并通过远程调用与Service Provider进行通信

---
# <a name="chapter4">4.Ribbon & Feign服务调用和客户端负载均衡</a>
## 4.1 Ribbon
### 4.1.1 Ribbon介绍：
### Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。它是一个基于HTTP和TCP的客户端负载均衡器。它可以通过在客户端中配置ribbonServerList来设置服务端列表去轮询访问以达到均衡负载的作用
### 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务实例列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动
![](https://i.imgur.com/HXACZzz.png)
### 4.1.2 Ribbon原理：
### 继承负载均衡策略的抽象类-AbstractLoadBanlancerRule：
- RandomRule - 随机策略
![](https://i.imgur.com/PoNT2SM.png)

- RoundRobinRule - 线性轮循策略
- WeightedResponseTimeRule - 加权响应时间策略
![](https://i.imgur.com/rTG6Pj2.png)
![](https://i.imgur.com/Ov5PD2k.png)

- BestAvailableRule - 选最空闲实例策略
![](https://i.imgur.com/NP42doF.png)

- ZoneAwareLoadBalancer - 区域感知策略

## 4.2 Feign
### 4.2.1 Feign介绍：
### Spring Cloud Feign是一套基于Netflix Feign实现的声明式服务调用客户端。它使得编写Web服务客户端变得更加简单。我们只需要通过创建接口并用注解来配置它既可完成对Web服务接口的绑定。Spring Cloud Feign还扩展了对Spring MVC注解的支持，同时还整合了Ribbon和Eureka来提供均衡负载的HTTP客户端实现

### 4.2.2 Feign简单使用：
### 4.2.2.1 添加依赖：
![](https://i.imgur.com/8JjXUpV.png)
### 4.2.2.2 配置文件：
![](https://i.imgur.com/9ntnoRD.png)
### 4.2.2.3 添加注解：
![](https://i.imgur.com/CjfbYVq.png)
![](https://i.imgur.com/9ed4YDB.png)

---
# <a name="chapter5">5.Hystrix & Hystrix Dashboard & Turbine</a>
## 5.1 Hystrix
### 5.1.1 Hystrix介绍：
### Spring Cloud Hystrix中实现了线程隔离、断路器等一系列的服务保护功能。它也是基于Netflix的开源框架 Hystrix实现的，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控等强大功能
### 5.1.2 Hystrix原理：
![](https://i.imgur.com/G5l5Sp5.png)

## 5.2 Hystrix Dashboard
### 5.2.1 Hystrix Dashboard介绍：
### Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题
![](https://i.imgur.com/23HaBAn.png)

### 5.2.2 Hystrix Dashboard简单使用：
### 5.2.2.1 添加依赖
![](https://i.imgur.com/LCvLe3L.png)
### 5.2.2.2 配置文件
![](https://i.imgur.com/ofoPGpm.png)
### 5.2.2.3 添加注解
![](https://i.imgur.com/ECmkP4G.png)

### 5.2.3 Hystrix Dashboard Web UI：
![](https://i.imgur.com/YsGWo75.png)

### Hystrix Dashboard监控单个服务实例：
![](https://i.imgur.com/0sBEXD6.png)
![](https://i.imgur.com/W6EDa3s.png)

- **圆形颜色和大小**：健康情况和流量
- **折线**：2分钟内的吞吐量变化情况
- **hosts**：集群内节点个数
- **median**：每个请求时间的中位数
- **mean**：平均每个请求消耗的时间
- **subsriberGetAccount**：
	> 绿：成功请求数量
	
	> 蓝：断路数量
	
	> 黄：超时的线程数量
	
	> 紫：线程池拒绝次数，即线程不够用
	
	> 红：失败或异常数量
	
	> 灰：最后10秒错误率
- **host**：各节点每秒的平均请求吞吐量
- **cluster**：集群每秒的请求吞吐量
- **circuit**：断路器状态

## 5.3 Turbine
### 5.3.1 Turbine介绍：
### 以上是对单个服务的监控，如果我们要监控多个服务，则在Dashboard之前需要一个收集器Turbine
![](https://i.imgur.com/Coa74VO.png)

### 5.3.2 Turbine简单使用：
### 5.3.2.1 添加依赖
![](https://i.imgur.com/TMWDcvl.png)
### 5.3.2.2 配置文件
![](https://i.imgur.com/wchI0Ap.png)
### 5.3.2.3 添加注解
![](https://i.imgur.com/1ToRseo.png)
### 监控整个集群所有服务实例：
![](https://i.imgur.com/9OJd9yS.png)

---
# <a name="chapter6">6.Sleuth & Zipkin服务链式追踪</a>
## 6.1 Sleuth
### 6.1.1 Sleuth介绍：
### 服务追踪分析：一个调用可能需要多个后台服务协同完成，随着服务的增多对调用链的分析也会越来越复杂。针对服务链路追踪的问题，Google发表了Dapper论文，介绍了他们如何进行服务追踪分析。其基本思路是在服务调用的请求和响应中加入ID，标明上下游请求的关系。利用这些信息，可以可视化地分析服务调用链路和服务间的依赖关系。

![](https://i.imgur.com/yoEA7Ph.png)

## 6.2 Zipkin
### 6.2.1 Zipkin介绍：
### 对应Dpper的开源实现是Zipkin，Spring Cloud Sleuth是对Zipkin的一个封装，对于Span、Trace等信息的生成、接入HTTP Request，以及向Zipkin Server发送采集信息等全部自动完成。

![](https://i.imgur.com/bulM0h4.png)

## 6.3 Sleuth简单使用：
## 6.3.1 添加依赖
![](https://i.imgur.com/wzItGms.png)
## 6.3.2 配置文件
![](https://i.imgur.com/i1jlgIP.png)
## 6.3.3 添加注解
![](https://i.imgur.com/hPQmqn5.png)

## 6.4 Sleuth Web UI介绍：
![](https://i.imgur.com/piChthW.png)
![](https://i.imgur.com/sJzD7Ho.png)

## 6.5 Span的存储方式
### 在Zipkin Server里面有很多种存储方式，但是比较主流的有这两种：
- 放在内存中存储
- 放在mysql中存储：
	>放在内存中的随着服务端的启动会出清空历史数据，如果想持久化保留这些数据，可以选择mysql的方式存储 

---
# <a name="chapter7">7.Zuul服务网关</a>
### 7.1 Zuul介绍：
### Spring Cloud Zuul就是一个提供负载均衡、反向代理和权限认证的API Gateway
![](https://i.imgur.com/aNQ07wN.png)

### 应用场景：
- 安全处理，包括加解密签名验签
- 身份认证和鉴权，黑白名单
- 日志打印
- 动态路由和统一的异常处理
- 流量控制，灰度发布

### 7.2 API网关的作用：
- 连接器和适配器
- 接口标准化
- 流量聚合，零售转批发
- 客户行为数据
- 服务品质评价与备份
![](https://i.imgur.com/fX2BtG1.png)

### 7.3 Zuul原理：
### Zuul通过大量的filter对请求进行安全、认证、路由进行控制
![](https://i.imgur.com/tn7Thhn.png)

- **PRE Filters**：是在把请求路由到目标节点前执行。如：认证、加载目标服务节点、打印日志
- **ROUTING Filters**：是把请求路由到目标服务的节点。到目标的请求就在这些filter中被创建，并通过Apache HttpClient或 Netflix Ribbon转发到目标节点
- **POST Filters**：是目标节点请求结束并返回到zuul后执行。可以把HTTP headers添加到返回给客户端的response中，并可以收集统计信息和健康信息，以及把目标节点的业务数据返回给客户
- **ERROR Filters**：任何一个步骤出错都会调用当前类型的filter

### 默认的核心过滤器：
![](https://i.imgur.com/3mbvhm2.png)

### 7.4 Zuul架构：
![](https://i.imgur.com/Ahn5oY5.png)

### 7.5 Zuul的启动过程：
![](https://i.imgur.com/5XQB5J6.png)

### 7.6 Zuul简单使用：
### 7.6.1 添加依赖
![](https://i.imgur.com/m16aqGM.png)
### 7.6.2 配置文件
![](https://i.imgur.com/HpKPiu4.png)
### 7.6.3 添加注解
![](https://i.imgur.com/4RU0uyX.png)

### Zuul源码：
![](https://i.imgur.com/gSiwCLE.png)
![](https://i.imgur.com/OOQLp2I.png)

### 自定义filter：
### 继承ZuulFilter

---
# <a name="chapter8">8.Spring Cloud Config集中配置管理</a>
### 8.1 Spring Cloud Config介绍：
### Spring Cloud Config分为两部分：
- **config-server**：服务端，管理配置信息
- **config-client**：客户端，客户端调用server端暴露接口获取配置信息
![](https://i.imgur.com/G2yPv8P.png)

### 8.2 Spring Cloud Config简单使用：
### 8.2.1 Spring Cloud Config Client添加依赖
![](https://i.imgur.com/qiocF3q.png)
### 8.2.2 Spring Cloud Config Client配置文件
![](https://i.imgur.com/ovGZNpA.png)
### 8.2.3 Spring Cloud Config Client添加注解
![](https://i.imgur.com/3PJIQKE.png)
### 8.2.4 Spring Cloud Config Server添加依赖
![](https://i.imgur.com/MfCC9l4.png)
### 8.2.5 Spring Cloud Config Server配置文件
![](https://i.imgur.com/cKk43O7.png)
### 8.2.6 Spring Cloud Config Server添加注解
![](https://i.imgur.com/zcRVHC3.png)
### 8.2.7 修改配置文件后调用对应实例的refresh接口进行刷新
![](https://i.imgur.com/GajTZUS.png)

---
# <a name="chapter9">9.Spring Cloud Bus & RabbitMq消息总线</a>
### 9.1 Spring Cloud Bus介绍：
### Spring Cloud Bus配合Spring Cloud Config来实现动态的配置更新
![](https://i.imgur.com/lv1oHGp.jpg)

### 9.2 Spring Cloud Bus简单使用：
### 9.2.1 添加依赖
![](https://i.imgur.com/z1oJGcK.png)
### 9.2.2 配置文件
![](https://i.imgur.com/0bGsdqR.png)
### 9.2.3 启动RabbitMQ：
### 切换到RabbitMQ安装目录的sbin目录下D:\RabbitMQ Server\rabbitmq_server-3.6.12\sbin，执行rabbitmq-plugins enable rabbitmq_management命令启动
### 9.2.4 Git PUSH修改配置文件
### 9.2.5 调用Spring Cloud Config Server的/bus/refresh接口刷新所有实例
![](https://i.imgur.com/JUomj9k.png)

### 9.3 RabbitMQ介绍：
### RabbitMQ是一个由erlang开发的基于AMQP（Advanced Message Queue ）协议的开源实现。用于在分布式系统中存储转发消息。
> AMQP，是应用层协议的一个开放标准，为面向消息的中间件设计。消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，同样，消息使用者也不用知道发送者的存在。AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

![](https://i.imgur.com/jm2xabk.png)

- Publisher：Message的生产者
- Consumer：Message的消费者，Publisher产生的Message最终要到达Consumer
- Exchange：指定Message按什么规则路由到哪个Queue，Message先要到达Exchange
- Queue：Message的容器，等待被消费出去
- Routing key：Exchange就是根据这些定义好的Routing key将Message送到对应的Queue中去，是Exchange和Queue之间的桥梁
- Broker：Broker就是接收和分发消息的应用，RabbitMQ Server就是Message Broker
- VirtualHost：虚拟主机，一个Broker可以开多个VirtualHost，它的作用是用作不同用户的权限分离
- Connection：是Publisher／Consumer和Broker之间的TCP连接。断开连接的操作只会在Publisher/Consumer端进行，Broker不会断开连接，除非出现网络故障或者Broker服务出现问题，Broker服务宕机
- Channel: 如果每一次访问RabbitMQ就建立一个Connection，那在消息量大的时候建立TCP Connection的开销就会很大

### RabbitMQ流程：
1. Publisher获取Conection，接着获取Channel
2. 定义Exchange，Queue
3. 使用RoutingKey将Queue一个个Binding到Exchange上
4. Publisher通过指定的Exchange和RoutingKey将消息发送到对应的Queue上
5. Consumer获取Connection，接着获取Channel，最后消费到指定Queue中的消息

### 9.4 RabbitMQ的基本概念[1]：
### 9.4.1 ConnectionFactory：
### ConnectionFactory为Connection的制造工厂。

### 9.4.2 Connection：
### Connection是RabbitMQ的socket链接，它封装了socket协议相关部分逻辑。

### 9.4.3 Channel：
### Channel是我们与RabbitMQ打交道的最重要的一个接口，我们大部分的业务操作是在Channel这个接口中完成的，包括定义Queue、定义Exchange、绑定Queue与Exchange、发布消息等。

### 9.4.4 Queue：
### Queue（队列）是RabbitMQ的内部对象，用于存储消息。
![](https://i.imgur.com/Sy9fnC7.png)
### 多个消费者可以订阅同一个Queue，这时Queue中的消息会被平均分摊给多个消费者进行处理，而不是每个消费者都收到所有的消息并处理。
![](https://i.imgur.com/qIbFZJA.png)

### 9.4.5 Message acknowledgment：
### 在实际应用中，可能会发生消费者收到Queue中的消息，但没有处理完成就宕机（或出现其他意外）的情况，这种情况下就可能会导致消息丢失。为了避免这种情况发生，我们可以要求消费者在消费完消息后发送一个回执给RabbitMQ，RabbitMQ收到消息回执（Message acknowledgment）后才将该消息从Queue中移除；如果RabbitMQ没有收到回执并检测到消费者的RabbitMQ连接断开，则RabbitMQ会将该消息发送给其他消费者（如果存在多个消费者）进行处理。

### 9.4.6 Message durability：
### 如果我们希望即使在RabbitMQ服务重启的情况下，也不会丢失消息，我们可以将Queue与Message都设置为可持久化的（durable），这样可以保证绝大部分情况下我们的RabbitMQ消息不会丢失。

### 9.4.7 Prefetch count：
### 前面我们讲到如果有多个消费者同时订阅同一个Queue中的消息，Queue中的消息会被平摊给多个消费者。这时如果每个消息的处理时间不同，就有可能会导致某些消费者一直在忙，而另外一些消费者很快就处理完手头工作并一直空闲的情况。我们可以通过设置prefetchCount来限制Queue每次发送给每个消费者的消息数，比如我们设置prefetchCount=1，则Queue每次给每个消费者发送一条消息；消费者处理完这条消息后Queue会再给该消费者发送一条消息。
![](https://i.imgur.com/sp1FNHf.png)

### 9.4.8 Exchange：
### 在上一节我们看到生产者将消息投递到Queue中，实际上这在RabbitMQ中这种事情永远都不会发生。实际的情况是，生产者将消息发送到Exchange（交换器，下图中的X），由Exchange将消息路由到一个或多个Queue中（或者丢弃）。 
![](https://i.imgur.com/6Ay1k1a.png)

### 9.4.9 routing key：
###	生产者在将消息发送给Exchange的时候，一般会指定一个routing key，来指定这个消息的路由规则，而这个routing key需要与Exchange Type及binding key联合使用才能最终生效。在Exchange Type与binding key固定的情况下（在正常使用时一般这些内容都是固定配置好的），我们的生产者就可以在发送消息给Exchange时，通过指定routing key来决定消息流向哪里。RabbitMQ为routing key设定的长度限制为255 bytes。

### 9.4.10 Binding：
### RabbitMQ中通过Binding将Exchange与Queue关联起来，这样RabbitMQ就知道如何正确地将消息路由到指定的Queue了。
![](https://i.imgur.com/R3wiLy6.png)

### 9.4.11 Binding key：
### 在绑定（Binding）Exchange与Queue的同时，一般会指定一个binding key；消费者将消息发送给Exchange时，一般会指定一个routing key；当binding key与routing key相匹配时，消息将会被路由到对应的Queue中。

### 9.4.12 Exchange Types：
### RabbitMQ常用的Exchange Type有fanout、direct、topic、headers这四种。

### 9.4.13 fanout：
### fanout类型的Exchange路由规则非常简单，它会把所有发送到该Exchange的消息路由到所有与它绑定的Queue中。下图中，生产者（P）发送到Exchange（X）的所有消息都会路由到图中的两个Queue，并最终被两个消费者（C1与C2）消费。
![](https://i.imgur.com/QHTAcWg.png)

### 9.4.14 direct：
### direct类型的Exchange路由规则也很简单，它会把消息路由到那些binding key与routing key完全匹配的Queue中。以下图的配置为例，我们以routingKey=”error”发送消息到Exchange，则消息会路由到Queue1（amqp.gen-S9b…，这是由RabbitMQ自动生成的Queue名称）和Queue2（amqp.gen-Agl…）；如果我们以routingKey=”info”或routingKey=”warning”来发送消息，则消息只会路由到Queue2。如果我们以其他routingKey发送消息，则消息不会路由到这两个Queue中。
![](https://i.imgur.com/jI8EUYE.png)

### 9.4.15 topic：
### topic类型的Exchange在匹配规则上进行了扩展，它与direct类型的Exchage相似，也是将消息路由到binding key与routing key相匹配的Queue中，但这里的匹配规则有些不同，它约定：
- routing key为一个句点号“. ”分隔的字符串（我们将被句点号“. ”分隔开的每一段独立的字符串称为一个单词），如“stock.usd.nyse”、“nyse.vmw”、“quick.orange.rabbit”
- binding key与routing key一样也是句点号“. ”分隔的字符串
- binding key中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）

### 以下图中的配置为例，routingKey=”quick.orange.rabbit”的消息会同时路由到Q1与Q2，routingKey=”lazy.orange.fox”的消息会路由到Q1与Q2，routingKey=”lazy.brown.fox”的消息会路由到Q2，routingKey=”lazy.pink.rabbit”的消息会路由到Q2（只会投递给Q2一次，虽然这个routingKey与Q2的两个bindingKey都匹配）；routingKey=”quick.brown.fox”、routingKey=”orange”、routingKey=”quick.orange.male.rabbit”的消息将会被丢弃，因为它们没有匹配任何bindingKey。

![](https://i.imgur.com/F9OLmiO.png)

### 9.4.16 headers：
### headers类型的Exchange不依赖于routing key与binding key的匹配规则来路由消息，而是根据发送的消息内容中的headers属性进行匹配。 在绑定Queue与Exchange时指定一组键值对；当消息发送到Exchange时，RabbitMQ会取到该消息的headers（也是一个键值对的形式），对比其中的键值对是否完全匹配Queue与Exchange绑定时指定的键值对；如果完全匹配则消息会路由到该Queue，否则不会路由到该Queue。 

### 9.4.17 RPC：
### MQ本身是基于异步的消息处理，前面的示例中所有的生产者（P）将消息发送到RabbitMQ后不会知道消费者（C）处理成功或者失败（甚至连有没有消费者来处理这条消息都不知道）。 但实际的应用场景中，我们很可能需要一些同步处理，需要同步等待服务端将我的消息处理完成后再进行下一步处理。这相当于RPC（Remote Procedure Call，远程过程调用）。在RabbitMQ中也支持RPC。
![](https://i.imgur.com/1MjaI4Q.png)

### RabbitMQ中实现RPC的机制是：
- 客户端发送请求（消息）时，在消息的属性（MessageProperties ，在AMQP 协议中定义了14中properties ，这些属性会随着消息一起发送）中设置两个值replyTo （一个Queue 名称，用于告诉服务器处理完成后将通知我的消息发送到这个Queue 中）和correlationId （此次请求的标识号，服务器处理完成后需要将此属性返还，客户端将根据这个id了解哪条请求被成功执行了或执行失败）
- 服务器端收到消息并处理
- 服务器端处理完消息后，将生成一条应答消息到replyTo 指定的Queue ，同时带上correlationId 属性
- 客户端之前已订阅replyTo 指定的Queue ，从中收到服务器的应答消息后，根据其中的correlationId 属性分析哪条请求被执行了，根据执行结果进行后续业务处理

### 9.5 RabbitMQ Web UI：
![](https://i.imgur.com/aYM7FHw.png)
![](https://i.imgur.com/3c2TLet.png)

---
# <a name="chapter10">10.SideCar异构服务</a>
### 10.1 SideCar介绍： 
### Spring Cloud Netflix Sidecar的设计灵感来自Netflix Prana，简单的说，一个非jvm程序，如：c++、python等，想要注册到eureka，但是应用都是一堆别的语言写的，那应该如何实现呢？Sidecar的原理就是监听该应用所运行的端口，然后检测该程序的运行状态，非jvm应用需要应该实现一个健康检查，Sidecar能够以此来报告给Eureka注册中心该应用是up还是down状态。
### 报告信息：
    {
  		"status":"UP"
	}

![](https://i.imgur.com/vbPvKpp.png)

### 10.2 SideCar简单使用：
### 10.2.1 添加依赖
![](https://i.imgur.com/kh4IJlr.png)
### 10.2.2 配置文件
![](https://i.imgur.com/tojowtz.png)
### 10.2.3 添加注解
![](https://i.imgur.com/KeE91oJ.png)
![](https://i.imgur.com/O5nGmns.png)
### forSideCar.py：
![](https://i.imgur.com/PIFyLXK.png)
### Eureka Web UI：
![](https://i.imgur.com/NX9yusl.png)
### 如果关掉python程序，Eureka Web UI：
![](https://i.imgur.com/k9WOela.png)

---
# <a name="chapter11">11.总结</a>
### 在网上看到一个例子非常贴切[2]：
### *Spring Cloud*就像一栋写字楼，写字楼里有各种各样的公司为用户提供了各种各样的服务。
### 每间办公室对应着一个*docker*容器，容器内跑了程序就叫一个微服务节点。每间办公室的房间号就是每一个容器的ip和port，公司名称就是微服务的服务名，如果一家公司规模较大有好几间办公室，那么就是多个容器组成一个高可用的微服务集群
### 写字楼楼底都有一个索引牌，哪家公司提供哪些服务房间号是多少，这个索引牌就是*Spring Cloud Eureka*
### 写字楼的大门和门卫是所有公司对外的gateway，用户只能通过大门进去进行安检后保安会帮你指路，这个大门和保安就是*Spring Cloud Zuul*
### 如果保安告诉你这家公司今天没上班你不用再浪费时间上去了，下次再来吧，这就是*Spring Cloud Hystrix*
### 如果一个公司有好几间办公室，用户自己选择进哪一间获取相关服务，这个就是*Spring Cloud Ribbon*
### 如果你是有头有脸的人物，以上访问一家公司的流程不够上档次，公司会安排专门的礼仪小姐在电梯口迎接，提高用户的满意度，这就是*Spring Cloud Feign*
### 写字楼里都有摄像头，你什么时间进入哪个房间，什么时间出来哪个房间都会记录下来，这就是*Spring Cloud Sleuth*
### 这栋写字楼的物业公司会派不同的保洁阿姨每天给不同的公司换不同的地毯，卫生程度也不一样，这就是*Spring Cloud Config*
### 如果这栋写字楼的物业公司福利够好，保洁阿姨每天上下班都有巴士接送，这就是*Spring Cloud Bus*

---
# <a name="chapter12">12.引用说明</a>
### 此文档用于个人学习总结，有少部分文字跟图片引用于一些大神们的博客，如果有漏掉的您可以给我发邮件说明，以下给出引用链接：
- 每章节文字介绍引用自<a href="http://blog.didispace.com/Spring-Cloud%E5%9F%BA%E7%A1%80%E6%95%99%E7%A8%8B/">《Spring Cloud基础教程》</a>
- [1]RabbitMQ的基本概念引用自<a href="http://blog.csdn.net/yejingtao703/article/details/77688711">我为什么要选择RabbitMQ，RabbitMQ简介，各种MQ选型对比</a>
- [2]总结的例子引用自<a href="http://www.sojson.com/blog/48.html">Spring实现微服务—进阶篇</a>
- 部分图片引用自其上水印链接

# <a href="#spring_cloud_top">Jump to the top</a>

