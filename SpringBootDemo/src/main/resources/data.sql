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

insert into bug(id,priority,title,short_desc,classification_id,product_id,version_id,os_id,	bug_status,reporter,creation_ts,change_ts,rank)
values(1,1,'test777','bbbbbbbbbbbbbb',1,1,1,1,1,'123','2016-08-20 12:20:30','2016-08-21 12:20:30',0);
insert into bug(id,priority,title,short_desc,classification_id,product_id,version_id,os_id,	bug_status,reporter,creation_ts,change_ts,rank)
values(2,2,'test888','bbbbbbbbbbbbbb',2,1,1,1,1,'123','2016-08-20 12:20:30','2016-08-21 12:20:30',0);
insert into bug(id,priority,title,short_desc,classification_id,product_id,version_id,os_id,	bug_status,reporter,creation_ts,change_ts,rank)
values(3,3,'test999','bbbbbbbbbbbbbb',2,1,2,2,1,'123','2016-08-20 12:20:30','2016-08-21 12:20:30',0);


insert into product(id, productName, status)
values(1, 'MS Office',1);
insert into product(id, productName, status)
values(2, 'Eclipse',1);

insert into os(id, osName)
values(1, 'windows');
insert into os(id, osName)
values(2, 'Linux');

insert into version(id, pid, oid, versiond,status)
values(1, 1, 1, 'v10.1',1);
insert into version(id, pid, oid, versiond,status)
values(2, 1, 1, 'v11.1',1);
insert into version(id, pid, oid, versiond,status)
values(3, 2, 2, 'v9.10',1);
