
#Installing OneAPI JAR
```
mvn install:install-file -Dfile=D:\Dialog\MIFE\Javalib\oneapi-validation-lib-1.0-SNAPSHOT.jar  -DgroupId=com.axiata.dialog.oneapi -DartifactId=oneapi-validation-lib -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```

#Installing DBUtil Payment
```
mvn install:install-file -Dfile=D:\Dialog\MIFE\Javalib\paydbutils-1.0-SNAPSHOT.jar  -DgroupId=com.axiata.dialog -DartifactId=PaymentDbServiceb -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```


Please update dbconfig.properties Database details in Paymentdbutil JAR file using archive manager

ideabiz-logger-library.jar file in ext-lib folder. install it
```
mvn install:install-file -Dfile=ext-lib\ideabiz-logger-library.jar  -DgroupId=lk.dialog.ideabiz-ideabiz-logger -DartifactId=library -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```