create table Angajat (
	Nume varchar(20),
	Numar_magazin int,
	Varsta int,
	Ocupatie varchar(MAX),
	Salar int,
	primary key (Nume, Numar_magazin)
)

create function validareNumarMagazin (@numarMagazin int)
returns bit as
begin
	declare @Return as bit
	if @numarMagazin between 1 and 5
		set @Return = 1
	else
		set @Return = 0
	return @Return
end

create function validareVarsta (@varsta int)
returns bit as
begin
	declare @Return as bit
	if @varsta >= 18
		set @Return = 1
	else
		set @Return = 0
	return @Return
end

create procedure insertValues (@nume varchar(20), @numarMagazin int, @varsta int, @ocupatie varchar(MAX), @salar int) as
begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'insert into Angajat (Nume, Numar_magazin, Varsta, Ocupatie, Salar) values (' + @nume + ', ' + @numarMagazin + 
	', ' + @varsta + ', ' + @ocupatie + ', ' + @salar + ')'
	exec @sqlQuery
end
go

create procedure insertIntoAngajat (@nume varchar(20), @numarMagazin int, @varsta int, @ocupatie varchar(MAX), @salar int) as
begin
	if dbo.validareNumarMagazin(@numarMagazin) = 1 and dbo.validareVarsta(@varsta) = 1
	begin
		insert into Angajat (Nume, Numar_magazin, Varsta, Ocupatie, Salar)
		values (@nume, @numarMagazin, @varsta, @ocupatie, @salar)
	end
	if dbo.validareNumarMagazin(@numarMagazin) = 0
	begin
		raiserror ('Magazinul cu numarul dat nu exista', 10, 1)
	end
	if dbo.validareVarsta(@varsta) = 0
	begin
		raiserror ('Angajatul nu are varsta corespunzatoare', 10, 1)
	end
end
go

drop procedure insertIntoAngajat

create view [Produse mai scumpe decat pretul mediu] as
	select Nume, Pret
	from Produs
	where Pret > (select AVG(Pret) as Pret_mediu
					from Produs)

create function produseMagazin (@numeMagazin varchar(20)) returns table
as
	return
		select M.Nume as Nume_magazin, P.Nume as Nume_produs
		from Magazin M
		join Inventar I on M.Numar_magazin = I.Numar_magazin
		join Produs P on I.Cod_produs = P.Cod
		where M.Nume = @numeMagazin

drop function produseMagazin

select * from [Produse Mai scumpe decat pretul mediu]

select Nume_produs
from dbo.produseMagazin('Kaufland 5')
intersect
select Nume
from [Produse mai scumpe decat pretul mediu]

create table Logtabelle (
	Id int identity(1,1),
	Datum_zeit datetime,
	Typ varchar(1),
	Name varchar(20),
	Anzahl_betr_Tupeln int,
	primary key (Id)
)

drop table Logtabelle

create trigger insertIntoLogtabelle
on dbo.Angajat
after insert, delete, update
as
begin
	declare @c as int
	set @c = @@rowcount
	if exists (select * from inserted)
	begin
		if exists (select * from deleted)
		begin
			insert into Logtabelle (Datum_zeit, Typ, Name, Anzahl_betr_Tupeln)
			values (getdate(), 'U', 'Angajat', @c)
		end
		else
		begin
			insert into Logtabelle (Datum_zeit, Typ, Name, Anzahl_betr_Tupeln)
			values (getdate(), 'I', 'Angajat', @c)
		end
	end
	else
	begin
		insert into Logtabelle (Datum_zeit, Typ, Name, Anzahl_betr_Tupeln)
		values (getdate(), 'D', 'Angajat', @c)
	end
end

drop trigger insertIntoLogtabelle

insert into Angajat (Nume, Numar_magazin, Varsta, Ocupatie, Salar) values ('X', 2, 19, 'casier', 1800)

update Angajat set Salar = 2000 where Salar = 1800

update Angajat set Salar = 1800 where Salar = 2000

delete from Angajat where Nume = 'X'

select * from Logtabelle

create table Magazin1 (
	Id int identity(1,1),
	Nume varchar(20),
	Varsta int,
	Ocupatie varchar(MAX),
	Salar int,
	primary key (Id)
)

create procedure raiseSalary (@nume varchar(20), @varsta int, @ocupatie varchar(MAX), @salar int)
as
begin
	update Angajat
	set Salar = Salar + 10
	where Nume = @nume and Varsta = @varsta and Ocupatie = @ocupatie
end
go 

drop procedure insertIntoMagazin

declare @nume varchar(20), @varsta int, @ocupatie varchar(MAX), @salar int
declare cursorangajat cursor for
	select Nume, Varsta, Ocupatie, Salar
	from Angajat
	where Numar_magazin = 1
for read only
open cursorangajat
fetch cursorangajat into @nume, @varsta, @ocupatie, @salar
while @@fetch_status = 0
begin
	exec raiseSalary @nume, @varsta, @ocupatie, @salar
	fetch cursorangajat into @nume, @varsta, @ocupatie, @salar
end
close cursorangajat
deallocate cursorangajat

select * from Magazin1

drop table Magazin1