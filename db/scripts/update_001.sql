create table if not exists users (
                                     id serial primary key,
                                     username varchar(100) not null,
                                     email varchar(100) not null unique,
                                     password varchar(100) not null
);

create table if not exists carBodies (
                                      id serial primary key,
                                      name varchar(100) not null
);

create table if not exists photo (
                                     id serial primary key,
                                     path varchar(300) not null
);

create table if not exists brands (
                                     id serial primary key,
                                     name varchar(100) not null
);

CREATE TABLE IF NOT EXISTS ads (
                                     id serial primary key,
                                     name varchar(100) NOT NULL,
                                     description varchar(500) NOT NULL,
                                     sold boolean,
                                     user_id integer NOT NULL REFERENCES users(id),
                                     carBody_id integer NOT NULL REFERENCES carBodies(id),
                                     brand_id integer NOT NULL REFERENCES brands(id)
);

CREATE TABLE IF NOT EXISTS ads_photo (
                                     id serial primary key,
                                     ad_id integer NOT NULL REFERENCES ads(id),
                                     photo_id integer NOT NULL UNIQUE REFERENCES photo(id)
);