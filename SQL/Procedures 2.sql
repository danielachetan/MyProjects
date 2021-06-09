create table Magazin (
Id int,
Nume varchar(20),
primary key (Id))

create table Produs (
Id int,
Nume varchar(MAX),
Pret int,
primary key (Id))

create table Inventar (
Id_magazin int,
Id_produs int,
Cantitate_produs int,
primary key (Id_magazin, Id_produs),
foreign key (Id_magazin) references Magazin,
foreign key (Id_produs) references Produs)

insert into History values (2, 1, 'addColumn', 'Adresa', 'Strada', 'varchar(MAX)', '', '')
insert into History values (3, 2, 'modifyColumnType', 'Adresa', 'Strada', 'varchar(20)', '', '')
insert into History values (4, 3, 'addDefaultConstraint', 'Produs', 'priceConstraint', '0', 'Pret', '')
insert into History values (5, 4, 'addFkConstraint', 'Inventar', 'inv_prodConstraint', 'Id_produs', 'Produs', 'on delete cascade')
insert into History values (4, 5, 'rbAddConstraint', 'Inventar', 'inv_prodConstraint', '', '', '')
insert into History values (3, 4, 'rbAddConstraint', 'Produs', 'priceConstraint', '', '', '')
insert into History values (2, 3, 'modifyColumnType', 'Adresa', 'Strada', 'varchar(MAX)', '', '')
insert into History values (1, 2, 'rbAddColumn', 'Adresa', 'Strada', '', '', '')

exec rbAddConstraint 'Inventar', 'inv_prodConstraint'
exec rbAddConstraint 'Produs', 'priceConstraint'
exec modifyColumnType 'Adresa', 'Strada', 'varchar', 'MAX'
exec rbAddColumn 'Adresa', 'Strada'
exec rbAddTable 'Adresa'

drop table History1
update Version set Nr = 0

create table History1 (
VersionNr int identity(1,1),
Executed_procedure varchar(MAX),
Parameter1 varchar(20),
Parameter2 varchar(20),
Parameter3 varchar(20),
Parameter4 varchar(20),
Parameter5 varchar(20),
primary key (VersionNr))

alter procedure addTable (@tableName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'create table ' + @tableName + ' (Id int, primary key (Id))'
	if not exists (select * from History1 where Executed_procedure = 'addTable' and Parameter1 = @tableName)
	begin
		insert into History1 (Executed_procedure, Parameter1, Parameter2, Parameter3, Parameter4, Parameter5)
		values ('addTable', @tableName, '', '', '', '')
		update Version set Nr = (select VersionNr
								from History1 H
								where H.Executed_procedure = 'addTable' and H.Parameter1 = @tableName)
	end 
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure addColumn (@tableName varchar(10), @columnName varchar(10), @columnType varchar(15), @length varchar(3)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add ' + @columnName + ' ' + @columnType
	if @columnType like '%char%'
	begin
		set @sqlQuery = @sqlQuery + ' (' + @length + ')'
	end
	if not exists (select * from History1 where Executed_procedure = 'addColumn' and Parameter1 = @tableName and Parameter2 = @columnName)
	begin
		insert into History1 (Executed_procedure, Parameter1, Parameter2, Parameter3, Parameter4, Parameter5)
		values ('addColumn', @tableName, @columnName, @columnType, @length, '')
		update Version
		set Nr = (select VersionNr
					from History1 H
					where H.Executed_procedure = 'addColumn' and H.Parameter1 = @tableName and H.Parameter2 = @columnName and
					H.Parameter3 = @columnType and H.Parameter4 = @length)
	end
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure modifyColumnType (@tableName varchar(10), @columnName varchar(10), @newColumnType varchar(15),@length varchar(3)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' alter column ' + @columnName + ' ' + @newColumnType
	if @newColumnType like '%char%'
	begin
		set @sqlQuery = @sqlQuery + ' (' + @length + ')'
	end
	if not exists (select * from History1 where Executed_procedure = 'modifyColumnType' and Parameter1 = @tableName and 
	Parameter2 = @columnName and Parameter3 = @newColumnType and Parameter4 = @length) and not exists (select * from History1 where 
	Executed_procedure = 'addColumn' and Parameter1 = @tableName and Parameter2 = @columnName and Parameter3 = @newColumnType and
	Parameter4 = @length)
	begin
		insert into History1 (Executed_procedure, Parameter1, Parameter2, Parameter3, Parameter4, Parameter5)
		values ('modifyColumnType', @tableName, @columnName, @newColumnType, @length, '')
		update Version
		set Nr = (select VersionNr
					from History1 H
					where H.Executed_procedure = 'modifyColumnType' and H.Parameter1 = @tableName and H.Parameter2 = @columnName and
					H.Parameter3 = @newColumnType and H.Parameter4 = @length)
	end
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure addDefaultConstraint (@tableName varchar(10), @constraintName varchar(15), @value varchar(10), @columnName varchar(10)) 
as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add constraint ' + @constraintName + ' default ' + @value + ' for ' + @columnName
	if not exists (select * from History1 where Executed_procedure = 'addDefaultConstraint' and Parameter1 = @tableName and
	Parameter2 = @constraintName)
	begin
		insert into History1 (Executed_procedure, Parameter1, Parameter2, Parameter3, Parameter4, Parameter5)
		values ('addDefaultConstraint', @tableName, @constraintName, @value, @columnName, '')
		update Version
		set Nr = (select VersionNr
					from History1 H
					where H.Executed_procedure = 'addDefaultConstraint' and H.Parameter1 = @tableName and H.Parameter2 = @constraintName 
					and H.Parameter3 = @value and H.Parameter4 = @columnName)
	end
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure addFkConstraint (@tableName varchar(10), @constraintName varchar(20), @fk varchar(10), @referencedTable varchar(10),
@constraint varchar(MAX)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add constraint ' + @constraintName + ' foreign key (' + @fk + ') references ' +
	@referencedTable + ' on ' + @constraint
	if not exists (select * from History1 where Executed_procedure = 'addFkConstraint' and Parameter1 = @tableName and
	Parameter2 = @constraintName)
	begin
		insert into History1 (Executed_procedure, Parameter1, Parameter2, Parameter3, Parameter4, Parameter5)
		values ('addFkConstraint', @tableName, @constraintName, @fk, @referencedTable, @constraint)
		update Version
		set Nr = (select VersionNr
					from History1 H
					where H.Executed_procedure = 'addFkConstraint' and H.Parameter1 = @tableName and H.Parameter2 = @constraintName and
					H.Parameter3 = @fk and H.Parameter4 = @referencedTable and H.Parameter5 = @constraint)
	end 
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go


alter procedure previousVersion as
	begin
	declare @vers as int
	set @vers = (select Nr from Version)
	declare @proc as varchar(20)
	set @proc = (select Executed_procedure from History1 where VersionNr = @vers)
	declare @param1 as varchar(20)
	set @param1 = (select Parameter1 from History1 where VersionNr = @vers)
	declare @param2 as varchar(20)
	set @param2 = (select Parameter2 from History1 where VersionNr = @vers)
	declare @param3 as varchar(20)
	set @param3 = (select Parameter3 from History1 where VersionNr = @vers)
	declare @param4 as varchar(20)
	set @param4 = (select Parameter4 from History1 where VersionNr = @vers)
	if @proc = 'addTable'
	begin
		exec rbAddTable @param1
	end
	if @proc = 'addColumn'
	begin
		exec rbAddColumn @param1, @param2
	end
	if @proc like 'add%Constraint'
	begin
		exec rbAddConstraint @param1, @param2
	end
	if @proc = 'modifyColumnType'
	begin
		declare @auxType as varchar(20)
		set @auxType = (select Parameter3 from History1 where Executed_procedure = 'addColumn' and Parameter2 = @param2)
		declare @auxLength as varchar(3)
		set @auxLength = (select Parameter4 from History1 where Executed_procedure = 'addColumn' and Parameter2 = @param2)
		exec modifyColumnType @param1, @param2, @auxType, @auxLength
	end
	update Version set Nr = Nr - 1
	end
go

create procedure nextVersion as
	begin
	declare @vers as int
	set @vers = (select Nr from Version) + 1
	declare @proc as varchar(20)
	set @proc = (select Executed_procedure from History1 where VersionNr = @vers)
	declare @param1 as varchar(20)
	set @param1 = (select Parameter1 from History1 where VersionNr = @vers)
	declare @param2 as varchar(20)
	set @param2 = (select Parameter2 from History1 where VersionNr = @vers)
	declare @param3 as varchar(20)
	set @param3 = (select Parameter3 from History1 where VersionNr = @vers)
	declare @param4 as varchar(20)
	set @param4 = (select Parameter4 from History1 where VersionNr = @vers)
	declare @param5 as varchar(20)
	set @param5 = (select Parameter5 from History1 where VersionNr = @vers)
	if @proc = 'addTable'
	begin
		exec addTable @param1
	end
	if @proc = 'addColumn'
	begin
		exec addColumn @param1, @param2, @param3, @param4
	end
	if @proc = 'modifyColumnType'
	begin
		exec modifyColumnType @param1, @param2, @param3, @param4
	end
	if @proc = 'addDefaultConstraint'
	begin
		exec addDefaultConstraint @param1, @param2, @param3, @param4
	end
	if @proc = 'addFkConstraint'
	begin
		exec addFkConstraint @param1, @param2, @param3, @param4, @param5
	end
	update Version set Nr = Nr + 1
	end
go

create procedure switchVersion (@version int) as
	begin
	while (select Nr from Version) <> @version
	begin
		if @version > (select Nr from Version)
		begin
			exec nextVersion
		end
		else
		begin
			exec previousVersion
		end
	end
	end
go