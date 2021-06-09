create table Ta (
	idA int identity (1,1),
	a2 int unique,
	a3 int,
	primary key (idA)
)
drop table Ta
drop table Tc
drop table Tb

create table Tb (
	idB int identity (1,1),
	b2 int,
	primary key (idB)
)

create table Tc (
	idC int identity (1,1),
	idA int,
	idB int,
	primary key (idC)
)

alter table Tc
add constraint Tc_Ta_FK
foreign key (idA)
references Ta
on delete cascade
on update cascade

alter table Tc drop constraint Tc_Ta_FK

alter table Tc
add constraint Tc_Tb_FK
foreign key (idB)
references Tb
on delete cascade
on update cascade

create procedure insertIntoTa
as
begin
	declare @count as int
	set @count = 0
	while @count < 10000
	begin
		insert into Ta (a2,a3)
		values (@count + 1,@count + 1)
		set @count = @count + 1
	end
end
go
drop procedure insertIntoTa

create procedure insertIntoTb
as
begin
	declare @count as int
	set @count = 0
	while @count < 3000
	begin
		insert into Tb (b2)
		values (@count + 1)
		set @count = @count + 1
	end
end
go
drop procedure insertIntoTb

create procedure insertIntoTc
as
begin
	declare @i as int
	set @i = 0
	while @i < 10000
	begin
		declare @j as int
		set @j = 0
		while @j < 3
		begin
			insert into Tc (idA,idB)
			values (@i + 1, @j + 1)
			set @j = @j + 1
		end
		set @i = @i + 1
	end
end
go

create procedure insertIntoTables
as
begin
	exec insertIntoTa
	exec insertIntoTb
	exec insertIntoTc
end
go

exec insertIntoTables

select *
from sys.indexes
where object_id = OBJECT_ID('Ta')

select *
from sys.indexes
where object_id = OBJECT_ID('Tb')

select *
from sys.indexes
where object_id = OBJECT_ID('Tc')

select *
from Tc
where idA between 1000 and 2000

select *
from Tb
where idB > 2995

select a2
from Ta
order by a2 desc

select *
from Ta
where a2 = 2019

select a3
from Ta
where a2 = 10

select * from Ta
select count(*) from Tb
select count(*) from Tc

select *
from Tb
where b2 = 1

create index Index_Tb_b2
on Tb (b2);

alter index Index_Tb_b2
on Tb disable

alter index Index_Tb_b2
on Tb rebuild

select *
from Tc C
inner join Tb B on C.idB = B.idB
where B.idB = 22

create index Index_Tc_idB
on Tc (idB);

alter index Index_Tc_idB
on Tc disable

alter index Index_Tc_idB
on Tc rebuild

select *
from Tc C
join Ta A on C.idA = A.idA
where A.idA = 7000

create index Index_Tc_idA
on Tc (idA);

alter index Index_Tc_idA
on Tc disable

alter index Index_Tc_idA
on Tc rebuild