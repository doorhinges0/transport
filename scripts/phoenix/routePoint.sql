drop table if exists baisha_car;

create table baisha_track_alarm(
   id VARCHAR
   ,routeid INTEGER
   ,carid INTEGER
   ,speed INTEGER
   ,longitide DOUBLE
   ,latitude DOUBLE
   ,direction INTEGER
   ,timestamp BIGINT
   ,events VARCHAR
   CONSTRAINT pk_baisha_track_alarm PRIMARY KEY (id)
);
