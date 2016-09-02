drop table if exists baisha_route;

create table baisha_route(
   id bigint not null
   ,hy_attemper_no varchar
   ,operation_type varchar
   ,car bigint
   ,plan_date bigint
   ,contract_no varchar
   ,start_place varchar 
   ,dest_prov varchar
   ,dest_city varchar
   ,dest_place varchar
   ,product_num integer
   ,product_info varchar
   ,hy_pc_no varchar
   ,driver1 bigint
   ,driver2 bigint
   ,tran_ok_status char(1)   
   ,start_time bigint
   ,arrive_ok_status char(1)
   ,arrival_time  bigint
   ,finish_ok_status char(1)
   ,finish_time bigint     
   ,CONSTRAINT pk_baisha_route PRIMARY KEY (id));