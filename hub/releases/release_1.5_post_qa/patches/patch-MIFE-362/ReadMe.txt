Incident: MIFE-362
Steps to install

1. Replace 'hibernate.cfg.xml' in mifesandbox/WEB-INF/classes with hibernate.cfg.xml provided in the patch
2. Restart the server

NOTE --------------------->
To fix this issue we have to increase the hibernate connection pool size in hibernate.cfg.xml. At the same time we have to increase the connection pool in the server. those connection pool sizes need to be match with each other. Otherwise there will be a exception like com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Data source rejected establishment of connection, message from server: "Too many connections".

