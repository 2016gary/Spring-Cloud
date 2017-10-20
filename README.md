# Spring Cloud入门教程
![](https://i.imgur.com/wTqZcJC.png)
### by Gary

---
# Index：
### 1.MicroService
### 2.Spring Cloud & Spring Boot
### 3.Eureka
### 4.Ribbon & Feign
### 5.Hystrix & Hystrix Dashboard & Turbine
### 6.Zipkin & Sleuth
### 7.Zuul
### 8.Spring Cloud Config
### 9.Spring Cloud Bus & RabbitMQ
### 10.SideCar
### 11.引用说明

---
# 1.MicroService
## 微服务架构：
### 微服务架构是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务与服务间采用轻量级的通信机制互相沟通（通常是基于HTTP协议的RESTful API）。每个服务都围绕着具体业务进行构建，并且能够被独立的部署到生产环境、类生产环境等。另外，应当尽量避免统一的、集中式的服务管理机制，对具体的一个服务而言，应根据业务上下文，选择合适的语言、工具对其进行构建。——Martin Fowler
### 总结：
- 小, 且专注于做一件事情
- 独立的进程中
- 轻量级的通信机制
- 松耦合、独立部署

---
# 2.Spring Cloud & Spring Boot
## 2.1 Spring Cloud
### 2.1.1 Spring Cloud介绍：
### Spring Cloud是一个基于Spring Boot实现的云应用开发工具，它为基于JVM的云应用开发中涉及的配置管理、服务发现、断路器、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理等操作提供了一种简单的开发方式
### Spring Cloud Netflix项目是Spring Cloud的子项目之一，主要内容是对Netflix公司一系列开源产品的包装，它为Spring Boot应用提供了自配置的Netflix OSS整合。通过一些简单的注解，开发者就可以快速的在应用中配置一下常用模块并构建庞大的分布式系统。它主要提供的模块包括：服务发现（Eureka），断路器（Hystrix），智能路由（Zuul），客户端负载均衡（Ribbon）等
### 2.1.2 Spring Cloud版本命名：
![](https://i.imgur.com/ijzwPdf.png)

- 按伦敦站点，迭代发布版本Angel -> Brixton -> Camden [卡姆登区]-> Dalston– [多尔斯顿]
- 每个版本修复一轮严重 bug ，就会发布一个 “service releases” 版本，用 .SRX 来表示，X 是版本号
### 2.1.2 Spring Cloud架构图：
![](https://i.imgur.com/BmBQ2tQ.png)

## 2.2 Spring Boot
### 2.2.1 Spring Boot介绍：
![](https://i.imgur.com/RkLhd13.png)

---
# 3.Eureka服务注册与发现
### 3.1 Eureka介绍：
- Eureka以RESTful API的方式为服务实例提供了注册、管理和查询等操作
- 可以运行多个Eureka实例构建集群达到高可用性
- Eureka Server提供可视化的监控页面
- Spring Cloud为服务治理做了一层抽象接口，所以在Spring Cloud应用中可以支持多种不同的服务治理框架，比如：Netflix Eureka、Consul、Zookeeper

![](https://i.imgur.com/0MOUcf2.png)

### 3.1.1 Eureka Server WebUI介绍：
![](https://i.imgur.com/X89D82N.png)
![](https://i.imgur.com/5ZQinxv.png)

### 3.2 Eureka原理：
### Eureka包括Eureka Server和Eureka Client，Eureka client再分为Service Provider和Service Consumer：
- Eureka Server：服务的注册中心，负责维护注册的服务列表
- Service Provider：服务提供方，作为一个Eureka Client，向Eureka Server做服务注册、续约和下线等操作，注册的主要数据包括服务名、机器ip、端口号、域名等
- Service Consumer：服务消费方，作为一个Eureka Client，向Eureka Server获取Service Provider的注册信息，并通过远程调用与Service Provider进行通信

### 3.2 Eureka简单使用：
### 3.2.1 添加依赖：
![](https://i.imgur.com/5ImANns.png)
### 3.2.2 配置文件：
![](https://i.imgur.com/ArpLdDp.png)
### 3.2.3 添加注解：
![](https://i.imgur.com/7ITZCpV.png)
---
# 4.Ribbon & Feign服务调用和客户端负载均衡
## 4.1 Ribbon
### 4.1.1 Ribbon介绍：
### Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。它是一个基于HTTP和TCP的客户端负载均衡器。它可以通过在客户端中配置ribbonServerList来设置服务端列表去轮询访问以达到均衡负载的作用
### 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务实例列表。同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动
![](https://i.imgur.com/HXACZzz.png)
### 4.1.2 Ribbon原理：
### 负载均衡策略的抽象类-AbstractLoadBanlancerRule：
- RandomRule - 随机策略
- RoundRobinRule - 线性轮循策略
- WeightedResponseTimeRule - 加权响应时间策略
- BestAvailableRule - 选最空闲实例策略
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
# 5.Hystrix & Hystrix Dashboard & Turbine
## 5.1 Hystrix
### 5.1.1 Hystrix介绍：
### Spring Cloud Hystrix中实现了线程隔离、断路器等一系列的服务保护功能。它也是基于Netflix的开源框架 Hystrix实现的，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控等强大功能
### 5.1.2 Hystrix原理：
![](https://i.imgur.com/G5l5Sp5.png)
### 5.1.3 Hystrix简单使用：
### 5.1.3.1 添加依赖：
### 5.1.3.2 配置文件：
### 5.1.3.3 添加注解：

## 5.2 Hystrix Dashboard
### 5.2.1 Hystrix Dashboard介绍：
### Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题
![](https://i.imgur.com/23HaBAn.png)

## 5.3 Turbine
### 5.3.1 Turbine介绍：
### 以上是对单个服务的监控，如果我们要监控多个服务，则在Dashboard之前需要一个收集器Turbine
![](https://i.imgur.com/Coa74VO.png)

---
# 6.Sleuth & Zipin服务链式追踪
## 6.1 Sleuth
### 6.1.1 Sleuth介绍：
### (1)服务追踪分析：一个调用可能需要多个后台服务协同完成，随着服务的增多对调用链的分析也会越来越复杂。针对服务链路追踪的问题，Google发表了Dapper论文，介绍了他们如何进行服务追踪分析。其基本思路是在服务调用的请求和响应中加入ID，标明上下游请求的关系。利用这些信息，可以可视化地分析服务调用链路和服务间的依赖关系。

![](https://i.imgur.com/yoEA7Ph.png)

## 6.2 Zipin
### 6.2.1 Zipin介绍：
### 对应Dpper的开源实现是Zipkin，Spring Cloud Sleuth是对Zipkin的一个封装，对于Span、Trace等信息的生成、接入HTTP Request，以及向Zipkin Server发送采集信息等全部自动完成。

![](https://i.imgur.com/bulM0h4.png)

---
# 7.Zuul服务网关
### 7.1 Zuul介绍：
### Spring Cloud Zuul就是一个提供负载均衡、反向代理和权限认证的API Gateway
![](https://i.imgur.com/aNQ07wN.png)

### 7.2 Zuul原理：
### Zuul将自己作为一个微服务注册到Eureka上，就获取了所有微服务的实例信息

### 7.3 Zuul使用：
- 1.添加依赖
- 2.配置文件
- 3.启动类

---
# 8.Spring Cloud Config集中配置管理
### 8.1 Spring Cloud Config介绍：
### Spring Cloud Config分为两部分：
- config-server：服务端，管理配置信息
- config-client：客户端，客户端调用server端暴露接口获取配置信息
### 需要配合Spring Cloud Bus来实现动态的配置更新
![](https://i.imgur.com/G2yPv8P.png)

---
# 9.Spring Cloud Bus & RabbitMq消息总线
### 9.1 Spring Cloud Bus介绍：
![](https://i.imgur.com/SqKCK6s.png)

---
# 10.SideCar异构服务

---
# 11.引用说明
### 此文档主要用于个人总结，所以大部分引用或参考于一些大神们的博客，如果有漏掉的您可以给我发邮件加上或者删除出自您的部分，以下给出链接：
- <a href="http://blog.didispace.com/Spring-Cloud%E5%9F%BA%E7%A1%80%E6%95%99%E7%A8%8B/">翟永超老师的《Spring Cloud基础教程》</a>



