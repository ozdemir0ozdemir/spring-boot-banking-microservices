create sequence account_id_seq start with 1 increment by 1;

create table accounts
(
    account_id        bigint        not null default nextval('account_id_seq'),
    account_number    varchar(255)  not null,
    account_type      varchar(40)   not null,
    account_status    varchar(40)   not null,
    available_balance numeric(9, 3) not null,
    user_email        varchar(100)  not null,
    created_at        timestamp     not null,
    primary key (account_id),
    constraint unique_account_number unique (account_number),
    constraint allowed_account_types check
        (account_type in ('SAVINGS_ACCOUNT', 'FIXED_DEPOSIT', 'LOAN_ACCOUNT') ),
    constraint allowed_account_statuses check
        (account_status in ('PENDING', 'ACTIVE', 'BLOCKED', 'CLOSED') )
);