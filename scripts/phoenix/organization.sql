drop table if exists baisha_organ;

create table baisha_organ(
   id bigint not null
   ,type varchar
   ,parentid bigint 
   ,name varchar
   ,CONSTRAINT pk_baisha_organ PRIMARY KEY (id));