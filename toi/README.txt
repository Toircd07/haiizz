lệnh chạy 

javac -cp "lib\mysql-connector-j-9.6.0.jar" -d . toi\model\*.java toi\dao\*.java toi\database\*.java toi\run\*.java

java -cp ".;lib\mysql-connector-j-9.6.0.jar" toi.run.Main