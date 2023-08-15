drop table if exists `direction`;

create table `direction` (
                             `id` bigint(20) not null auto_increment,
                             `created_date` datetime(6) default null,
                             `modified_date` datetime(6) default null,
                             `distance` double not null,
                             `input_address` varchar(255) collate utf8mb4_unicode_ci default null,
                             `input_latitude` double not null,
                             `input_longitude` double not null,
                             `target_address` varchar(255) collate utf8mb4_unicode_ci default null,
                             `target_latitude` double not null,
                             `target_longitude` double not null,
                             `target_place` varchar(255) collate utf8mb4_unicode_ci default null,
                             primary key (`id`)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci;

lock tables `direction` write;

unlock tables;
