create sequence hibernate_sequence start 1 increment 1;
create sequence sq_general start 1 increment 1;

create table base_quote (
    currency_id int8 not null,
    date varchar(255),
    name varchar(255),
    primary key (currency_id)
);

create table converted_currency (
    id int8 not null,
    amount_to_convert varchar(255),
    created_at date,
    first_currency varchar(255),
    result varchar(255),
    second_currency varchar(255),
    user_fid int8, primary key (id)
);

create table quotes (
    id int8 not null,
    char_code varchar(255),
    name varchar(255),
    nominal int4 not null,
    num_code int4 not null,
    value float8,
    valute_id varchar(255),
    currency_fid int8,
    user_fid int8,
    primary key (id)
);

create table user_roles (
    user_id int8 not null,
    roles varchar(255)
);

create table users (
    user_id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (user_id)
);

alter table if exists converted_currency
    add constraint converted_currency_user_fk
        foreign key (user_fid) references users;

alter table if exists quotes
    add constraint quotes_currency_fk
    foreign key (currency_fid) references base_quote;

alter table if exists quotes
    add constraint quotes_user_fk
    foreign key (user_fid) references users;

alter table if exists user_roles
    add constraint user_roles_user_fk
        foreign key (user_id) references users;