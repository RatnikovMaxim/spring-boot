INSERT INTO users(login, password) VALUES ('vasya', '$argon2id$v=19$m=4096,t=3,p=1$IaAGwPAFOcOzhg+bkgSeQQ$wm2E6lcsfSR1ORWefDRBUdj5lKGIoO0SJ6QyoSRpaVA');

INSERT INTO user_roles(user_id, role) SELECT id, 'ROLE_ADMIN' FROM users WHERE login = 'vasya';
INSERT INTO user_roles(user_id, role) SELECT id, 'ROLE_USER' FROM users WHERE login = 'vasya';

INSERT INTO posts(author_id, content, geo_lat, geo_lng)
SELECT id, 'first post', 55.00, 45.00 FROM users WHERE login = 'vasya';