
TODO BEFORE RUNNING THE UNWDMI APPLICATION!

1. import library mysql-connector-java-5.0.8-bin.jar (root of app)
2. import unwdmi.sql to database (root of app)
3. create user using the following statements in mysql
	CREATE USER 'unwdmi'@'localhost' IDENTIFIED BY 'unwdmi';
	GRANT ALL PRIVILEGES ON unwdmi.* TO 'unwdmi'@'localhost';
	FLUSH PRIVILEGES;
4. run app! :)
	 