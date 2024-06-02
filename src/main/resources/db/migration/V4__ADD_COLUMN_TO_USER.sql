alter table app_user add column roles VARCHAR(255) default 'ROLE_USER';

update app_user set roles = 'ROLE_ADMIN' where id = 1;