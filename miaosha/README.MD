#高并发秒杀解决方案源码案例
##第一章 项目框架搭建
1、Spring Boot环境搭建

2、集成Thymeleaf,Result结果封装

3、集成Mybatis+Druid

4、集成Jedis+Redis封装+通用缓存Key封装

    springboot的封装好的redisTemplate:   https://mp.weixin.qq.com/s/tHxnLI3yWqHjk_zzf_k8Dg

##第二章 实现登录功能

1、数据库设计

2、明文密码两次MD5处理

    1.客户端：PASS = MD5(明文+固定salt)            防止用户的密码在网络上进行传输时泄漏
    2.服务端：PASS = MD5(用户输入+随机Salt)         防止数据库泄漏

3、JSR303参数校验+全局异常处理器

4、分布式Session

##第三章 实现秒杀功能

1、数据库设计

2、商品列表页

3、商品详情页

4、订单详情页

##第四章 JMeter压测

1、JMeter入门

2、自定义变量模拟多用户

3、JMeter命令行使用

4、Spring Boot打war包

##第五章 页面优化技术

1、页面缓存+URL缓存+对象缓存

2、页面静态化、前后端分离

3、静态资源优化

4、CDN优化

##第六章 接口优化

1、Redis预减库存减少数据库访问

2、内存标记减少Redis访问

3、RabbitMQ队列缓冲、异步下单、增强用户体验

4、RabbitMQ安装与Spring Boot集成

5、访问Nginx水平扩展

##第七章 安全优化

1、秒杀接口地址隐藏

2、数学公式验证码

3、接口限流防刷


以上仅供自己参考和学习！！！


















