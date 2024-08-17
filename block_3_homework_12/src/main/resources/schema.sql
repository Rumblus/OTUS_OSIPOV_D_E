drop table if exists authors cascade;

drop table if exists books cascade;

drop table if exists comments cascade;

drop table if exists genres cascade;

create table authors (
                         id bigint generated by default as identity,
                         author varchar(255),
                         primary key (id)
);
    
create table books (
                       author_id bigint,
                       genre_id bigint,
                       id bigint generated by default as identity,
                       title varchar(255),
                       primary key (id)
);
    
create table comments (
                          book_id bigint,
                          id bigint generated by default as identity,
                          data varchar(255),
                          primary key (id)
);
    
create table genres (
                        id bigint generated by default as identity,
                        genre varchar(255),
                        primary key (id)
);
    
alter table if exists books 
       add constraint FKfjixh2vym2cvfj3ufxj91jem7 
       foreign key (author_id) 
       references authors;

alter table if exists books 
       add constraint FK9hsvoalyniowgt8fbufidqj3x 
       foreign key (genre_id) 
       references genres;

alter table if exists comments 
       add constraint FK1ey8gegnanvybix5a025vepf4 
       foreign key (book_id) 
       references books;

-- Security Info
CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);

CREATE TABLE users (  -- Измените имя таблицы на "users"
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);