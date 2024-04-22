insert into customers (id, email, pwd) VALUES
    (1, 'luciano@gmail.com', '12345678'),
    (2, 'aline@gmail.com', '12345678'),
    (3, 'patricia@gmail.com', '12345678'),
    (4, 'jessica@gmail.com', '12345678');

insert into roles(id, role_name, description, id_customer) values
    (1, 'VIEW_ACCOUNT', 'cant view account endpoint', 1),
    (2, 'VIEW_CARDS', 'cant view cards endpoint', 2),
    (3, 'VIEW_LOANS', 'cant view loans endpoint', 3),
    (4, 'VIEW_BALANCE', 'cant view balance endpoint', 4);