insert into user(id,username,password,first_name,last_name,age,email,status)
values
(1,'123','c4ca4238a0b923820dcc509a6f75849b','XY','Mike',21,null,1),
(2,'124','c81e728d9d4c2f636f067f89cc14862c','XX','Mike',22,null,1),
(3,'125','eccbc87e4b5ce2fe28308fd9f2a7baf3','XO','Mike',23,null,1);

insert into role(id, rolename, description)
values
(1, 'ROLE_ADMIN', 'this is a system administrator'),
(2, 'DEVELOPER', 'desc DEVELOPER'),
(3, 'ROLE_USER', 'desc USER'),
(4, 'TESTER', 'desc TESTER');

insert into resource(id, resource, name, description)
values
(1, '/', 'ADMIN', 'this is a system administrator'),
(2, '/Auth/users.html', 'DEVELOPER', 'desc DEVELOPER'),
(3, '/Auth/list', 'USER', 'desc USER'),
(4, '/yy', 'TESTER', 'desc TESTER');

insert into permission(id, roleid, resid)
values
(1, 1, 1),(2, 1, 2),(3, 1, 3),(4, 1, 4),
(5, 3, 1),(6, 3, 3),(7, 3, 4);
 

insert into authorise(id, roleid, userid)
values
(1,1, 1), (2,3, 2);