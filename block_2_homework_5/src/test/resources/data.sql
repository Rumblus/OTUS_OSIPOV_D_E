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
values ('Sherlock Holmes', 2, 3);