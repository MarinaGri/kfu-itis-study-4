insert into author(first_name, last_name) values ('Автор 1 - имя', 'Автор 1 - фамилия');
insert into author(first_name, last_name) values ('Автор 2 - имя', 'Автор 2 - фамилия');
insert into author(first_name, last_name) values ('Автор 3 - имя', 'Автор 3 - фамилия');
insert into author(first_name, last_name) values ('Автор 4 - имя', 'Автор 4 - фамилия');

insert into post(title, text, created_at, state, author_id)
values ('Заголовок 1', 'Текст текст текст', current_timestamp, 'PUBLISHED', 1);
insert into post(title, text, created_at, state, author_id)
values ('Заголовок 2', 'Текст текст текст', current_timestamp, 'PUBLISHED', 1);
