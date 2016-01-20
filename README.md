#WebChat聊天室

##一.所用技术
项目使用Maven构建,后台使用的语言是JAVA
框架为Spring + Spring MVC + Mybatis
数据库使用的是Oracle 10g
前端采用[Amaze UI](http://amazeui.org/),弹窗和分页使用的是[Layer](www.layui.com),当然还有[Jquery](http://jquery.com/)...
开发工具使用的是[IntelliJ IDEA 15.0.1](http://www.jetbrains.com/idea/)

##二.数据交互格式
在WebChat中定义的前后台数据交换格式如下:
```
"message" : {
	"from" : "xxx",
	"to" : "xxx",
	"content" : "xxxxxx",
	"time" : "xxxx.xx.xx"
},
"type" : {
	"xxx"
},
"list" : {
	["xx","xx"]
}
```
其中,message是消息部分,from是发信人用户名,to是收信人用户名(如果是群聊则置空),content是消息内容,time是发送时间,这里发信时间是前台JS获取,from和to都是直接用用户名而不是昵称....都是为了偷懒

type是消息类型,分为两种,notice和message.notice是提示类型,比如xxx用户加入了聊天室,xxx用户离开了聊天室;message是消息类型,就是用户之间发送的消息

list是当前在线的用户名的列表,只在后台触发onopen和onclose时返回list,然后更新前台的在线列表,实现实时的列表展示

##三.实现功能
用户登录,用户信息展示,用户信息修改,用户头像修改,修改密码,在线聊天,在线列表展示,群聊和私聊,日志记录展示等.

##四.使用方法
数据库:在resources/sql/webchat.dmp或者webchat.sql
JAR包:在resources/jar,这里放了所有用到的JAR包,方便不会maven的同学,不过还是建议学一学maven
项目直接导入eclipse或者idea即可,注意是maven构建的,非maven自行下载jar包并构建项目即可(oracle的jar包在我本地的maven,因为版权问题你懂的,自行解决)