:: 关闭打印
@echo off
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin 
:: 以下是执行的命令
java -jar zipkin.jar --server.port=8484 --zipkin.storage.type=mysql --zipkin.storage.mysql.db=service_demo --zipkin.storage.mysql.username=root --zipkin.storage.mysql.password=Mysql@314159... --zipkin.storage.mysql.host=localhost --zipkin.storage.mysql.port=3306 --zipkin.collector.rabbitmq.addresses=localhost:5672 --zipkin.collector.rabbitmq.username=scott --zipkin.collector.rabbitmq.password=123456
:: 其他
taskkill /f /im cmd.exe
exit