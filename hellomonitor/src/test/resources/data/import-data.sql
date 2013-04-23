INSERT INTO user_group(id, name, parent_user_group_id) VALUES (1, '默认用户组', null);
INSERT INTO role(id, description, name, is_default) VALUES (1, '系统管理人员', '管理员', 1);
INSERT INTO role(id, description, name, is_default) VALUES (2, '普通用户，系统的一般人员角色', '普通用户', 1);
INSERT INTO user(id, created_date, email, last_login_date, password, username, phone, user_group_id, role_id, salt, is_default) VALUES (3, '2013-03-02 12:07:34', 'madofu@163.com', '2013-04-07 09:44:47', '1e8dba710c138fdc1b4c4f36c3ce6a7db1e21da5', 'admin', null, 1, 1, '7d9ce46e190d5afd', 1);