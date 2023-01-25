select * from user;

show tables;

desc user;

delete from user;

select no, name from user where email = 'dooly@gmail.com' and password=password('dooly');

update user set name = '둘리4', password = password('1234'), gender = 'male' where no = 3;

desc board;

select * from board;

select * from user;

select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s') from board b join user u on b.user_no = u.no;

insert into board values(null, '바빠요', '바쁩니다링', 0, now(), 1, 1, 0, 3);
insert into board values(null, '바빠요', '바쁩니다링', 0, now(), 1, 2, 1, 4);

insert into board values(null, '나는둘리당', '에베베베', 0, now(), (SELECT IFNULL(MAX(g_no) + 1, 1) FROM board b), 1, 0, 3);

select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth from board b join user u on b.user_no = u.no order by b.g_no desc, b.o_no asc;