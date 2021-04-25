insert into tag (name) values ('one'),('two'),('three');

insert into gift_certificate (name, description, price, duration, create_date) values
('name one', 'description one', 1, 1, '2021-03-21T20:00:00.0000'),
('name two', 'description two', 2, 2, '2021-03-24T20:00:00.0000'),
('name three', 'description three', 3, 3, '2021-03-25T20:00:00.0000'),
('name four', 'description four', 4, 4, '2021-03-29T20:00:00.0000');

insert into gift_certificate_tag (certificate, tag) values
(1, 1),(2, 2),(3, 3),(4, 1),(4, 2),(4, 3);

insert into user (login) values
('login one'), ('login two');

insert into order_certificate (cost, purchase_time, certificate_id, user_id) values
(1, '2021-04-01T20:00:00.0000', 1, 1),
(1, '2021-04-01T20:00:00.0000', 4, 1),
(2, '2021-04-01T20:00:00.0000', 2, 2),
(2, '2021-04-01T20:00:00.0000', 4, 2);