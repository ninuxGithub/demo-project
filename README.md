demo-project
==============

#### 项目启动：
	根据application.properties配置 MySql config对应的配置即可，启动SpringBoot JAP自动创建表     

#### 内容提示：
	1.测试各种技术， jdk 动态代理 cglib 代理        
	2.spring thymeleaf 增删改查         
	3.加入Redis 的测试以及完整的demo  
	4.加入Druid 数据源 便于观察数据库的连接情况      
	5.加入Resuful API 的查看情况           
	6.加入Aspect 统计方法Controller 方法运行消耗的时间         
	7.加入@ControllerAdvice 处理全局的异常Exception 和  自定义的异常BusinessException       
	7.加入Scheduled 定时任务demo        
	8.加入自定义的Servlet :
	在使用的时候需要通过注解：@WebServlet(name = "myServlet", urlPatterns="/myServlet/*",  
	description="通过注解实现Servlet")  value 和 urlPatterns 相互冲突好像只可以填写个即可，  
	需要在application.java 类中加入@ServletComponentScan(basePackages="com.example.demo.servlet")   
	扫描到工程中的Servlet
	9.加入mina http 访问以及回调， 访问地址：http://localhost:8080/?name=minaHttp
	10.加入httpUtils模拟发送各种http请求
#### MinaConfig
 spring-boot整合mina的配置； 处理httpRequest请求（不是content）; mina 的心跳部分不是很清楚需要进一步的研究
  访问地址：http://localhost:3003/?name=minaHttp

#### redisConfig
 spring-boot整合redis

#### Swagger2Config
 spring-boot整合Swagger2Config; 访问地址：http://localhost:8888/swagger-ui.html
 
#### DruidConfiguration
 spring-boot整合druid数据源; 访问地址：http://localhost:8888/druid/index.html
 



servlet:     
------------------
	等价于JSP request请求响应对应的request，response

filter:     
------------------
	对用户发送的请求request，response继续一种过滤， 做一些业务 过滤检查是否拥有权限访问， 然后filterChain.doFilter(request, response);继续将请求forword下去        
	Filter有如下几个用处。       
	在HttpServletRequest到达Servlet之前，拦截客户的HttpServletRequest。      
	根据需要检查HttpServletRequest，也可以修改HttpServletRequest头和数据。     
	在HttpServletResponse到达客户端之前，拦截HttpServletResponse。       
	根据需要检查HttpServletResponse，也可以修改HttpServletResponse头和数据。      

####Filter有如下几个种类
	* 用户授权的Filter：Filter负责检查用户请求，根据请求过滤用户非法请求。        
	* 日志Filter：详细记录某些特殊的用户请求。      
	* 负责解码的Filter：包括对非标准编码的请求解码。     
	* 能改变XML内容的XSLT Filter等。      
	* Filter可负责拦截多个请求或响应；一个请求或响应也可被多个请求拦截。      

listener：           
--------------------
	监听器，从字面上可以看出listener主要用来监听只用。通过listener可以监听web服务器中某一个执行动作，并根据其要求作出相应的响应。                
	通俗的语言说就是在application，session，request三个对象创建消亡或者往其中添加修改删除属性时自动执行代码的功能组件。比如spring                
	 的总监听器 会在服务器启动的时候实例化我们配置的bean对象 、 hibernate 的 session 的监听器会监听session的活动和生命周期，负责创建，关闭session等活动。                
	Servlet的监听器Listener，它是实现了javax.servlet.ServletContextListener 接口的服务器端程序，它也是随web应用的启动而启动，只初始化一次，                
	随web应用的停止而销毁。主要作用是： 做一些初始化的内容添加工作、设置一些基本的内容、比如一些参数或者是一些固定的对象等等。                


interceptor：
--------------------
	是在面向切面编程的，就是在你的service或者一个方法，前调用一个方法，或者在方法后调用一个方法，是基于JAVA的反射机制。比如动态代理就是拦截器的简单实现，                
	在你调用方法前打印出字符串（或者做其它业务逻辑的操作），也可以在你调用方法后打印出字符串，甚至在你抛出异常的时候做业务逻辑的操作。                
	    servlet、filter、listener是配置到web.xml中（web.xml 的加载顺序是：context-param -> listener -> filter -> servlet ），                
	    interceptor不配置到web.xml中，struts的拦截器配置到struts.xml中。spring的拦截器配置到spring.xml中。                

#### 多行文本框 （2个TAB）
		Class HelloWorld{
			public static void main(String[] args){
				System.out.print("hello world");
			}
	
		}


#### 我是链接
[demo-project链接](https://github.com/ninuxGithub/demo-project)<br />


#### mina教程
[系列教程:](http://www.cnblogs.com/wucao/tag/MINA/)<br/>
[深入了解：](http://shiyanjun.cn/archives/category/opensource/mina)



#### 我是效果图片Demo Picture
	
![image](https://github.com/ninuxGithub/demo-project/blob/master/demo.png)


访问地址：http://localhost:8888/orders.html

##jcob 使用介绍
	 1.在项目中build path 加入jacob.jar
	 2.在jdk的bin目录加入对应系统的dll 文件 ， 例如jacob-1.18-x64.dll
	 
## echart 绘制双线图
	 
	 
	 
	 


