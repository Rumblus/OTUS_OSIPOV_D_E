insert into AUTHORS (author)
values ('Robert Lewis Stevenson');
insert into AUTHORS (author)
values ('Lev Tolstoi');
insert into AUTHORS (author)
values ('Alexander Pushkin');

insert into GENRES (genre)
values ('Novel');
insert into GENRES (genre)
values ('Fairy Tale');
insert into GENRES (genre)
values ('Detective');
insert into GENRES (genre)
values ('Adventure');
insert into GENRES (genre)
values ('Horror');

insert into BOOKS (title, author_id, genre_id)
values ('Black Arrow', 1, 1);
insert into BOOKS (title, author_id, genre_id)
values ('Treasure Island', 1, 4);

insert into COMMENTS (book_id, data)
values (1, 'Very interesting book!');

-- Security Info
INSERT INTO roles (name) VALUES ('USER');
INSERT INTO roles (name) VALUES ('ADMIN');

INSERT INTO users (username, password) VALUES ('user1', '$2a$10$uJKSVbEPSJUVQuz1pauL0.khQ.9qNGmkVi/GR3DoibXKPzMTd404e'); -- user1Pass
INSERT INTO users (username, password) VALUES ('user2', '$2a$10$URVfvmN5momZGQeP293X4.6Y7bAZMv4RwvsIQ9TbAExfEBTbfLOTi'); -- user2Pass
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$ybRcENQBRo30OqNj5iqZMuCnGXQRuzXqQE2pMW7dLT26EvuX68NIy'); -- adminPass

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 - USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1); -- user2 - USER
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2); -- admin - ADMIN