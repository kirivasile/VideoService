@echo off
echo Debug lint HTML report will be written to app\build\reports\lint-results.html
echo Debug test HTML report will be written to app\build\reports\tests\testDebugUnitTest\index.html
echo Debug code coverage HTML report will be written to app\build\reports\jacoco\jacocoTestDebugUnitTestReport\html\index.html
gradlew clean build jacocoTestReport
pause