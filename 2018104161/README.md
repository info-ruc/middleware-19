仿天猫系统

1.1目的和背景

天猫系统一个大型网上购物平台.在天猫上，用户可以选择和查找自己想要的商品，加入购物车或者直接购买，带给人们一种全新的购物方式
  
1.2项目介绍

本项目是在原有的天猫系统的基础上进行的模仿和重做，主要工作内容有:前端界面模仿,后端架构选型,实现大致功能

项目用到的知识如下：

Java:

Java基础 和 Java中级 的大部分内容

前端：

HTML, CSS, JAVASCRIPT, JQUERY,AJAX, Bootstrap

J2EE：

TOMCAT, SERVLET, JSP, Filter

框架：

Spring，Spring MVC，Mybatis，Spring+Mybatis整合，SSM整合

数据库：

MySQL

开发工具:

Eclipse,Maven

项目名称: tmall_ssm

java源代码包结构:

pojo 实体类

mapper Mapper类

interceptor 拦截器

controller 控制层

service Service层

test 测试类

util 工具类

comparator 比较类

web目录:

css css文件

img 图片资源

js js文件

admin 后台管理用到的jsp文件

fore 前台展示用到的jsp文件

include 被包含的jsp文件
 
经过这个项目，完成了如下的一系列典型场景功能

1. 购物车

立即购买，加入购物车，查看购物车页面，购物车页面操作

2. 订单状态流转

生成订单， 确认支付， 后台发货， 确认收货， 评价

3. CRUD 

后台各种功能

4. 分页

后台各种功能

5. 一类产品多属性配置

属性管理

6. 一款产品多图片维护

产品图片管理

7. 产品展示

前台首页 ，前台产品页

8. 搜索查询

搜索

9. 登录、注册

注册 登录 退出

10. 登录验证

登录状态拦截器

11. 事务管理

ForeController.对createOrder进行事务管理
等等 …
