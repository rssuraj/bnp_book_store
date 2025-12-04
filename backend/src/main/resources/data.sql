insert into authors (id, first_name, last_name, about) values (1, 'Plato', 'Plato', 'Philosophy, political theory');
insert into authors (id, first_name, last_name, about) values (2, 'Homer', 'Homer', 'Epic poem, ancient Greek adventures');
insert into authors (id, first_name, last_name, about) values (3, 'William', 'Shakespeare', 'Tragedy, revenge');
insert into authors (id, first_name, last_name, about) values (4, 'Jane', 'Austen', 'Romance, social commentary');
insert into authors (id, first_name, last_name, about) values (5, 'Scott', 'Fitzgerald', 'American Dream, wealth');

insert into books (id, isbn, title, price, available_quantity, about) values (1, 123456, 'The Republic', 10, 10, 'Philosophy, political theory');
insert into books (id, isbn, title, price, available_quantity, about) values (2, 223456, 'The Odyssey', 10, 10, 'Epic poem, ancient Greek adventures');
insert into books (id, isbn, title, price, available_quantity, about) values (3, 323456, 'Hamlet', 10, 10, 'Tragedy, revenge');
insert into books (id, isbn, title, price, available_quantity, about) values (4, 423456, 'Pride and Prejudice', 10, 10, 'Romance, social commentary');
insert into books (id, isbn, title, price, available_quantity, about) values (5, 523456, 'The Great Gatsby', 10, 10, 'American Dream, wealth');

insert into book_author (author_id, user_id) values (1, 1);
insert into book_author (author_id, user_id) values (2, 2);
insert into book_author (author_id, user_id) values (3, 3);
insert into book_author (author_id, user_id) values (4, 4);
insert into book_author (author_id, user_id) values (5, 5);