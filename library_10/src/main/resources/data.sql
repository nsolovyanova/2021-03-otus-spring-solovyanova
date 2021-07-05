insert into authors (name) values ('Чернышевский Н.');
insert into authors (name) values ('Илья Ильф');
insert into authors (name) values ('И. С. Тургенева');
insert into genres (name) values ('Роман');
insert into genres (name) values ('Комедия');
insert into books (name, author_id, genre_id) values ('Что делать?', 1, 1);
insert into books (name, author_id, genre_id) values ('12 стульев', 2, 2);
insert into books (name, author_id, genre_id) values ('Отцы и дети', 3, 1);
insert into comments (book_id, text_comment) values (1, 'Трудная книга');
insert into comments (book_id, text_comment) values (1, 'Жизненная книга');
insert into comments (book_id, text_comment) values (2, 'Замечательная книга, обожаю Эллочку');
insert into comments (book_id, text_comment) values (2, 'Классика на все времена');
insert into comments (book_id, text_comment) values (3, 'Ниосилил');
insert into comments (book_id, text_comment) values (3, 'В школе читал');
insert into comments (book_id, text_comment) values (3, 'Проблема актуальна');
insert into users (login, password) values ('admin', 'admin');
insert into users (login, password) values ('diasoft', 'diasoft');
insert into usersaccess (userid, access) values (1, 'ADMIN')
insert into usersaccess (userid, access) values (2, 'USER')



