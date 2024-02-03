:: 关闭打印
@echo off
:: 延时60s执行
:: timeout /T 60 /NOBREAK
:: 以下三行表示后台
if "%1" == "h" goto begin 
mshta vbscript:createobject("wscript.shell").run("%~nx0 h",0)(window.close)&&exit 
:begin 
:: 以下是执行的命令
C:\Home\other_ws\nacos\bin\startup.cmd
taskkill /f /im cmd.exe
exit