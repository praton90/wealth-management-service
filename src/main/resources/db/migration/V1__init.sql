create table customer
(
    id             integer      not null
        constraint customer_pk
            primary key,
    email          varchar(255) not null,
    date_of_birth  date         not null,
    risk_level     integer      not null,
    retirement_age integer      not null
);

alter table customer
    owner to username;

create unique index customer_email_uindex
    on customer (email);

create table strategy
(
    id                      integer not null
        constraint strategy_pk
            primary key,
    min_risk_level          integer not null,
    max_risk_level          integer not null,
    min_years_to_retirement integer not null,
    max_years_to_retirement integer not null,
    stock_percentage        integer not null,
    cash_percentage         integer not null,
    bond_percentage         integer not null
);

alter table strategy
    owner to username;

create function get_years_until_retirement(date, numeric) returns integer
    language sql
    as
$$
select date_part('year', age(($1 + ($2 * '1 year'::interval)), current_date))::int
$$;

alter function get_years_until_retirement(date, numeric) owner to username;
