insert into user(id,username,password,first_name,last_name,age,email,status)
values
(1,'123','1','XY','Mike',21,null,1),
(2,'124','2','XX','Mike',22,null,1),
(3,'125','3','XO','Mike',23,null,1);

insert into role(id, roleName, description)
values
(1, 'ADMIN', 'this is a system administrator'),
(2, 'DEVELOPER', 'desc DEVELOPER'),
(3, 'USER', 'desc USER'),
(4, 'TESTER', 'desc TESTER');

insert into resource(id, resource, name, description)
values
(1, '/', 'ADMIN', 'this is a system administrator'),
(2, '/user', 'DEVELOPER', 'desc DEVELOPER'),
(3, '/xx', 'USER', 'desc USER'),
(4, '/yy', 'TESTER', 'desc TESTER');

insert into permission(id, roleid, resid)
values
(1, 1, 1),(1, 1, 2),(1, 1, 3),(1, 1, 4),
(1, 3, 2),(1, 3, 3),(1, 3, 4);

insert into authorise(id, roleid, userid)
values
(1, 1, 1), (2, 3, 2);