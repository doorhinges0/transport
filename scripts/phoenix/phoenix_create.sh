psql_script=/usr/hdp/current/phoenix-client/bin/psql.py  
$psql_script localhost:2181:/hbase-unsecure organization.sql -t BAISHA_ORGAN -d "|" organizations.csv
$psql_script localhost:2181:/hbase-unsecure driver.sql -t BAISHA_DRIVER -d "|" drivers.csv
$psql_script localhost:2181:/hbase-unsecure car.sql -t BAISHA_CAR -d "|" cars.csv
$psql_script localhost:2181:/hbase-unsecure route.sql -t BAISHA_ROUTE -d "|" routes.csv
