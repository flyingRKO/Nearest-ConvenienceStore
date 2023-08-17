drop table if exists store;

create table store(
    id bigint(20) not null auto_increment,
    created_date datetime(6) default null,
    modified_date datetime(6) default null,
    latitude double not null,
    longitude double not null,
    store_address varchar(255) collate utf8mb4_unicode_ci default null,
    store_name varchar(255) collate utf8mb4_unicode_ci default null,
    primary key (id)
) engine=InnoDB auto_increment=84 default charset=utf8mb4 collate=utf8mb4_unicode_ci;

lock tables store write;


unlock tables ;

