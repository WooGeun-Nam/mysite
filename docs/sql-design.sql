select * from user;

show tables;

desc user;

delete from user;

select no, name from user where email = 'dooly@gmail.com' and password=password('dooly');

update user set name = '둘리4', password = password('1234'), gender = 'male' where no = 3;

desc board;

select * from board;

select * from guestbook;

select * from user;

select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth, u.no 
from board b join user u on b.user_no = u.no 
where title like '%테스트%' or contents like '%테스트%' 
order by b.g_no desc, b.o_no asc 
limit 0,5 ;

insert into board values(null, '바빠요', '바쁩니다링', 0, now(), 1, 1, 0, 3);
insert into board values(null, '바빠요', '바쁩니다링', 0, now(), 1, 2, 1, 4);

insert into board values(null, '나는둘리당', '에베베베', 0, now(), (SELECT IFNULL(MAX(g_no) + 1, 1) FROM board b), 1, 0, 3);

select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth from board b join user u on b.user_no = u.no order by b.g_no desc, b.o_no asc;

update board set title = 'title', contents = 'contents' where no = 7;

update user set email = 'ddochi@gmail.com' where no = 3;

select no, title, contents, hit, date_format(reg_date, '%Y-%m-%d') as regDate, g_no, o_no, depth, user_no as userNo
from board where no = 26 and user_no = 1;

select count(*)
from board;

select * from user;

desc user;

desc board;

alter table user add column role enum("ADMIN", "USER") default "USER" after gender;
insert into user values (null, '관리자', 'admin@mysite.com', password('1234'), 'male', 'ADMIN', now());

desc site;

insert into site values(null, 'MySite', '안녕하세요. 남우근의 mysite에 오신 것을 환영합니다.', '/assets/images/profile.png', '이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n메뉴는 사이트 소개, 방명록, 게시판이 있구요. Java 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.');

			select title, welcome, profile, description 
			from site
			order by no asc
			limit 0,1;
            
update site 
set title='YourSite'
where no=1;