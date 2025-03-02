# IdepgramDemo
接入idepgram的demo

前端：my-vue-app
后端：ideogramdemo


后端使用springboot实现，需要在application.properties文件中指定ideogram的key，并且配置数据库，数据库建表语句在src\main\resources\schema.sql中

前端使用vuejs实现，需要修改指定的后端服务器地址

先在美国服务器部署后端，然后将前端部署在中国服务器，部署方法可以问deepseek