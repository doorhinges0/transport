drop table if exists baisha_car;

create table baisha_car(
   id bigint not null
   ,code varchar
   ,car_type char(1)
   ,status char(1)
   ,organ bigint
   ,device bigint
   ,cam_num tinyint
   ,contact varchar
   ,code_type varchar
   ,register_time bigint
   ,weight double
   ,height double
   ,length double
   ,second_contact varchar
   ,code_color integer
   ,car_purchase_date bigint
   ,driver2 bigint
   ,driver1 bigint
   ,check_time bigint
   ,service_type varchar
   ,pack_height double
   ,pack_width double
   ,pack_length double
   ,zy_car_totsmoke double
   ,zy_car_numdoor integer
   ,zy_car_idcode varchar
   ,zy_gps_install_time bigint
   ,position_time bigint
   ,stop_remakrs varchar
   ,trailer integer
   ,vehicle_type varchar
   ,car_brand varchar
   ,CONSTRAINT pk_baisha_car PRIMARY KEY (id));