:: 关闭打印
@echo off
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin 
:: 以下是执行的命令
java -Dserver.port=8485 -Dcsp.sentinel.dashboard.server=localhost:8485 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar
:: 其他
taskkill /f /im cmd.exe
exit