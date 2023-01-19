select * from user;

show tables;

desc user;

delete from user;

select no, name from user where email = 'dooly@gmail.com' and password=password('dooly');

update user set name = '둘리4', password = password('1234'), gender = 'male' where no = 3;