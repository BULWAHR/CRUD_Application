call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openBrowser
echo.
goto fail

:openBrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.