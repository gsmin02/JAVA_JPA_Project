show databases;
use db_sb_a;
show tables;

DESC user;
select * from user;

create database db_sb_a;
use db_sb_a;

show tables ;
select * from user;

insert into user(user.address, user.email, user.fullname, user.pw, user.reg_date, user.mod_date)
values('nowon', 'id1@induk.ac.kr', 'induk1', 'cometrue', now(), now());
insert into user(user.address, user.email, user.fullname, user.pw, user.reg_date, user.mod_date)
values('seongbuk', 'id2@induk.ac.kr', 'induk2', 'cometrue', now(), now());
insert into user(user.address, user.email, user.fullname, user.pw, user.reg_date, user.mod_date)
values('gangbuk', 'id3@induk.ac.kr', 'induk3', 'cometrue', now(), now());
insert into user(user.address, user.email, user.fullname, user.pw, user.reg_date, user.mod_date)
values('joongrang', 'id4@induk.ac.kr', 'induk4', 'cometrue', now(), now());

select * from user;

/*
 Transaction :
    트랜잭션 - C.R.U.D. 처리, 논리적인 작업 단위를 하나로 묶어서 나누어질 수 없도록 만든 것, 하나라도 실패 시 기존 상태로 복원되는 특징
A.C.I.D :
    Atomicity - 원자성, 모두 실행하거나 모두 실행하지 않거나의 두 가지 상태만 가짐
    Consistency - 일관성, 트랜잭션 이전과 이후에 데이터베이스는 항상 일관된 상태여야 함
    Isolation - 고립성, 여러 트랜잭션이 동시에 실행될 때에도, 혼자 실행되는 것처럼 동작하게 만들어야 함
    Durability - 지속성, 커밋된 트랜잭션의 결과는 데이터베이스에 영구적으로 저장되어야 함('영구적 저장' == '비휘발성 메모리(HDD, SSD 등)에 저장')
 */