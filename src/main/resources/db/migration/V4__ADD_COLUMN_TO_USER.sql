alter table app_user add column roles VARCHAR(255) default 'ROLE_USER';

update app_user set roles = 'ROLE_ADMIN', password = '$2a$10$sAGzQD/I8c36hz884rUvcuEE7dCTR7wBE3EDkQ5.eOjOp5YvFqgPG' where id = 1;