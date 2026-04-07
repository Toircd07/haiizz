
//nếu lỗi ko chạy dc do chưa cài mysqlconector9.6.0 jar, chưa  cài thì cài bằng link này https://dev.mysql.com/downloads/file/?id=547777   
// sau đó giải nén tháy file.jar trong VScode chỗ javaproject kéo xuống thấy referenced Librarie thêm  file.jar và đó
click vào file Main.java r mở new terminal 
chạy 2 lệnh sau
javac -cp "lib\mysql-connector-j-9.6.0.jar" -d . toi\model\*.java toi\dao\*.java toi\database\*.java toi\run\*.java
java -cp ".;lib\mysql-connector-j-9.6.0.jar" toi.run.Main

