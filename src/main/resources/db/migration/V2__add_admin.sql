insert into users (user_id, username, password, active)
    values (0, 'admin', 'admin', true);

insert into user_roles (user_id, roles)
    VALUES (0, 'USER'), (0, 'ADMIN');