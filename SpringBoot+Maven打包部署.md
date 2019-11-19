1. 如果加载依赖慢


> project pom.xml 元素下添加

```xml
    <pluginRepositories>
        <pluginRepository>
            <id>alimaven spring plugin</id>
            <name>alimaven spring plugin</name>
            <url>https://maven.aliyun.com/repository/spring-plugin</url>
        </pluginRepository>
    </pluginRepositories>
```

或者

> project pom.xml 元素下添加

```xml
    <pluginRepositories>
        <pluginRepository>
            <id>alimaven spring plugin</id>
            <name>alimaven spring plugin</name>
            <url>https://maven.aliyun.com/repository/spring-plugin</url>
        </pluginRepository>
    </pluginRepositories>
```


2. 自动运行测试用例的问题



<properties>
<maven.test.skip>true</maven.test.skip>
    <!--<skipTests>true</skipTests>-->
</properties>

3. 执行 packge 命令
    
    java -jar xx.jar &
或者
    nohup java -jar xxxx.jar & 

4. 启动失败  杀进程

        netstat -anp | grep 8080   #  : 查看8080端口
		kill -9 XXX


