drop table if exists baisha_driver;

create table baisha_driver(
   id bigint not null
   ,name varchar
   ,status varchar
   ,address varchar
   ,phone_num varchar
   ,updated_time bigint
   ,organ bigint
   ,driver_license varchar
   ,identity_card varchar
   ,registration_time bigint
   ,is_gaoshi_leader char(1)
   ,zy_member_type varchar
   ,zy_car_status varchar
   ,zy_check_time bigint
   ,stop_time bigint
   ,check_time bigint
   ,dob char(8)     
   ,gender tinyint   
   ,CONSTRAINT pk_baisha_driver PRIMARY KEY (id));