create database ATM;
use ATM;
create table users(
    id int primary key auto_increment,
    card_number int(10),
    name varchar(50),
    pin int(4),
    balance double
);
insert into users(card_number, name, pin, balance) values
(1234567890, 'Swapnil', 1234, 1000.0);
