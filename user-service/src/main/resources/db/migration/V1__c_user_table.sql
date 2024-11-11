create sequence user_id_seq start with 1 increment by 1;
create sequence user_profile_id_seq start with 1 increment by 1;

create table user_profiles
(
    user_profile_id bigint      not null default nextval('user_profile_id_seq'),
    first_name      varchar(50) not null,
    last_name       varchar(50) not null,
    gender          varchar(20) not null,
    primary key (user_profile_id),
    constraint allowed_gender_values check (gender in('MALE', 'FEMALE'))
);

create table users
(
    user_id               bigint       not null default nextval('user_id_seq'),
    email                 varchar(255) not null,
    contact_no            varchar(50)  not null,
    status                varchar(40)  not null,
    user_profile_id       bigint       not null,
    created_at            timestamp    not null,
    updated_at            timestamp,
    primary key (user_id),
    constraint allowed_user_status_types check (status in ('PENDING', 'APPROVED', 'DISABLED', 'REJECTED')),
    constraint fk_user_profile_id foreign key (user_profile_id) references user_profiles (user_profile_id),
    constraint unique_user_profile_id unique (user_profile_id),
    constraint unique_user_email unique (email),
    constraint unique_user_contact_no unique (contact_no)
);

