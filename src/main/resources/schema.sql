create table if not exists Product (
  id identity,
  name varchar(25) not null,
  desc varchar(100) not null
);

insert into Product(name,desc) values('Soap','Daily bath use');
insert into Product (name,desc) values('Milk','Basic Drink' );
insert into Product (name,desc) values('Rice','Basic Food' );
insert into Product (name,desc) values('baseProduct','dont know what');