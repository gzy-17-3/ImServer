1. 基本命令
	1. 连接远程服务器 
		
			ssh userName@host
			

	2. 远程拷贝到本地
		
			scp root@104.207.132.96:/root/fileName.ex /Users/lxf/Documents/test
			
			scp root@rdxer.com:/home/wwwroot/xxsync.rdxer.com/xxsync_server/storage/logs/laravel.log ~/

	3. 本地上传
	
			scp /Users/lxf/Documents/test/fileName.ex root@104.207.132.96:/root/
			
			scp /Users/Rdxer/Dropbox\ \(个人\)/project/Mylife/mylife.rdxer.com/Nginx/2_mylife.rdxer.com.key root@rdxer.com:/root/key/
			
	4. 列出所有端口
	
			netstat -ntlp
        netstat -atunlp  
        
        netstat -anp | grep 8080     : 查看8080端口


	5. 杀进程

			kill -9 XXX

	6. 查找文件
	
			find / -name httpd.conf
2. 防火墙
	1. centos7之后的防火墙使用动态防火墙[firewall](https://fedoraproject.org/wiki/FirewallD/zh-cn)
		1. 设置public端口

			```			
			firewall-cmd --permanent --zone=public --add-port=13169/tcp
firewall-cmd --permanent --zone=public --add-port=13169/udp
firewall-cmd --reload
			```
		2. [centos 7 firewalld基本配置](http://blog.feehi.com/linux/473.html)
		3. 一般应用
			- 获取 firewalld 状态

					firewall-cmd --state
			- 	列出全部启用的区域的特性
			
					firewall-cmd --list-all-zones
	2. iptables
3. 网络
	1. [curl](http://blog.csdn.net/foxman209/article/details/6278093/)
		1. 下载 
			
				  curl -o source.tar.gz https://cn.wordpress.org/wordpress-4.5.3-zh_CN.tar.gz
		 
		2. 解压
					
					tar -zxvf source.tar.gz -C /tmp/


4. 文件文件夹操作
	1. 新建文件夹

	>mkdir 文件名
	新建一个名为test的文件夹在home下
	view source1 mkdir /home/test
	
	2. 新建文本
	
	>在home下新建一个test.sh脚本
	 vi /home/test.sh
	
	3. 删除文件或文件夹
	
	>1、删除home目录下的test目录
	 rm /home/test
	
	>2、这种不带参数的删除方法经常会提示无法删除，因为权限不够。
	 rm -r /home/test
	
	>3、-r是递归的删除参数表中的目录及其子目录。 目录将被清空并且删除。 当删除目录包含的具有写保护的文件时用户通常是被提示的。
	rm -rf /home/test
	
	>-4、f是不提示用户，删除目录下的所有文件。请注意检查路径，输成别的目录就悲剧了。
	 rm -ir /home/test
	
	>5、-i是交互模式。使用这个选项，rm命令在删除任何文件前提示用户确认。
	
	4. 移动文件或文件夹
	
	mv [options] 源文件或目录 目标文件或目录
	
# java 

    $nohup java -jar im-0.0.2-SNAPSHOT.jar &
    
    nohup java -jar XXX.jar &
    
 




