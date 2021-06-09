create table Version (Nr int)

create procedure addTable (@tableName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'create table ' + @tableName + ' (Id int) insert into History (Executed_procedure,Parameter1,Parameter2,Parameter3,
	Parameter4,Parameter5) values (addTable,' + @tableName + ','','','','')'
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec addTable 'Magazin'

create procedure addColumn (@tableName varchar(10), @columnName varchar(10), @columnType varchar(15)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + 'add ' + @columnName + ' ' + @columnType
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec addColumn 'Magazin', 'Nume', 'varchar(20)'

alter procedure addColumn (@tableName varchar(10), @columnName varchar(10), @columnType varchar(15)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add ' + @columnName + ' ' + @columnType
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec addColumn 'Magazin', 'Nume', 'varchar(20)'

create procedure modifyColumnType (@tableName varchar(10), @columnName varchar(10), @newColumnType varchar(15)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' drop column ' + @columnName + ' add ' + @columnName + ' ' + @newColumnType
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec modifyColumnType 'Magazin', 'Nume', 'varchar(MAX)'

alter procedure modifyColumnType (@tableName varchar(10), @columnName varchar(10), @newColumnType varchar(15)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' alter column ' + @columnName + ' ' + @newColumnType
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec modifyColumnType 'Magazin', 'Nume', 'varchar(MAX)'

create procedure addDefaultConstraint (@tableName varchar(10), @constraintName varchar(15), @value varchar(10), @columnName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add constraint ' + @constraintName + ' default ' + @value + ' for ' + @columnName
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec addDefaultConstraint 'Magazin', 'defaultNume', 'null', 'Nume'
insert into Magazin (Id) values (1)

exec addColumn 'Magazin', 'Adresa', 'int'

alter procedure addTable (@tableName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'create table ' + @tableName + ' (Id int, primary key (Id))' 
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

drop table Magazin
exec addTable 'Magazin'
exec addColumn 'Magazin', 'Nume', 'varchar(20)'
exec modifyColumnType 'Magazin', 'Nume', 'varchar(MAX)'
create table Angajat (
Id int,
Nume varchar(20),
Id_magazin int,
primary key (Id),
foreign key (Id_magazin) references Magazin)

create procedure addFkConstraint (@constraintName varchar(20), @tableName varchar(10), @fk varchar(10), @referencedTable varchar(10),
@constraint varchar(MAX)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add constraint ' + @constraintName + ' foreign key ' + @fk + ' references ' +
	@referencedTable + ' ' + @constraint
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec addFkConstraint 'Angajat','Angajat_magazin', 'Id_magazin', 'Magazin', 'on delete cascade on update no action'

alter procedure addFkConstraint (@tableName varchar(10), @constraintName varchar(20), @fk varchar(10), @referencedTable varchar(10),
@constraint varchar(MAX)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' add constraint ' + @constraintName + ' foreign key (' + @fk + ') references ' +
	@referencedTable + ' ' + @constraint
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

insert into Magazin (Id, Nume) values (1, 'Kaufland')
insert into Angajat (Id, Nume, Id_magazin) values (1, 'A', 1)
delete from Magazin where Id = 1

create procedure rbAddConstraint (@tableName varchar(10), @constraintName varchar(20)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' drop constraint ' + @constraintName
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec rbAddConstraint 'Angajat', 'Angajat_Magazin'

create procedure rbAddColumn (@tableName varchar(10), @columnName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'alter table ' + @tableName + ' drop column ' + @columnName
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec rbAddColumn 'Angajat', 'Nume'

create procedure rbAddTable (@tableName varchar(10)) as
	begin
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'drop table ' + @tableName
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec rbAddTable 'Angajat'
exec rbAddTable 'Magazin'

create table History (
Current_version int,
Previous_version int,
Executed_procedure varchar(MAX),
Parameter1 varchar(20),
Parameter2 varchar(20),
Parameter3 varchar(20),
Parameter4 varchar(20),
Parameter5 varchar(20),
primary key (Current_version))

create table Version (
Nr int,
primary key (Nr))

insert into Version values (0)

insert into History (Current_version) values (0)

insert into History (Current_version, Previous_version, Executed_procedure, Parameter1)
values (1, 0, 'addTable', 'Adresa')

create function getColumnValue(@columnName varchar(10), @currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = @columnName
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

alter function getColumnValue(@columnName varchar(MAX), @currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = @columnName
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getExecProcedure(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Executed_procedure
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getParameter1(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Parameter1
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getParameter2(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Parameter2
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getParameter3(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Parameter3
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getParameter4(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Parameter4
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create function getParameter5(@currentVersion varchar(2), @newVersion varchar(2)) returns varchar(MAX) as
	begin
	declare @Return varchar(MAX)
	select @Return = Parameter5
	from History
	where Current_version = @newVersion and Previous_version = @currentVersion
	return @Return
	end

create procedure switchVersion (@currentVersion varchar(2), @newVersion varchar(2)) as
	begin
	declare @getExecutedProcedure as varchar(MAX)
	set @getExecutedProcedure = dbo.getColumnValue('Executed_procedure', @currentVersion, @newVersion)
	declare @getParameter1 as varchar(20)
	set @getParameter1 = dbo.getColumnValue('Parameter1', @currentVersion, @newVersion)
	declare @getParameter2 as varchar(20)
	set @getParameter2 = dbo.getColumnValue('Parameter2', @currentVersion, @newVersion)
	declare @getParameter3 as varchar(20)
	set @getParameter3 = dbo.getColumnValue('Parameter3', @currentVersion, @newVersion)
	declare @getParameter4 as varchar(20)
	set @getParameter4 = dbo.getColumnValue('Parameter4', @currentVersion, @newVersion)
	declare @getParameter5 as varchar(20)
	set @getParameter5 = dbo.getColumnValue('Parameter5', @currentVersion, @newVersion) 
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'update Version set Nr = ' + @newVersion + ' ' + @getExecutedProcedure + ' ' + @getParameter1 + ' ' +
	@getParameter2 + ' ' + @getParameter3 + ' ' + @getParameter4 + ' ' + @getParameter5
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure switchVersion (@currentVersion varchar(2), @newVersion varchar(2)) as
	begin
	declare @getExecutedProcedure as varchar(MAX)
	set @getExecutedProcedure = dbo.getExecProcedure(@currentVersion, @newVersion)
	declare @getParameter1 as varchar(20)
	set @getParameter1 = dbo.getColumnValue('Parameter1', @currentVersion, @newVersion)
	declare @getParameter2 as varchar(20)
	set @getParameter2 = dbo.getColumnValue('Parameter2', @currentVersion, @newVersion)
	declare @getParameter3 as varchar(20)
	set @getParameter3 = dbo.getColumnValue('Parameter3', @currentVersion, @newVersion)
	declare @getParameter4 as varchar(20)
	set @getParameter4 = dbo.getColumnValue('Parameter4', @currentVersion, @newVersion)
	declare @getParameter5 as varchar(20)
	set @getParameter5 = dbo.getColumnValue('Parameter5', @currentVersion, @newVersion) 
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'update Version set Nr = ' + @newVersion + ' ' + @getExecutedProcedure + ' ' + @getParameter1 + ' ' +
	@getParameter2 + ' ' + @getParameter3 + ' ' + @getParameter4 + ' ' + @getParameter5
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

alter procedure switchVersion (@currentVersion varchar(2), @newVersion varchar(2)) as
	begin
	declare @getExecutedProcedure as varchar(MAX)
	set @getExecutedProcedure = dbo.getExecProcedure(@currentVersion, @newVersion)
	declare @getParameter1 as varchar(20)
	set @getParameter1 = dbo.getParameter1(@currentVersion, @newVersion)
	declare @getParameter2 as varchar(20)
	set @getParameter2 = dbo.getParameter2(@currentVersion, @newVersion)
	declare @getParameter3 as varchar(20)
	set @getParameter3 = dbo.getParameter3(@currentVersion, @newVersion)
	declare @getParameter4 as varchar(20)
	set @getParameter4 = dbo.getParameter4(@currentVersion, @newVersion)
	declare @getParameter5 as varchar(20)
	set @getParameter5 = dbo.getParameter5(@currentVersion, @newVersion) 
	declare @sqlQuery as varchar(MAX)
	set @sqlQuery = 'update Version set Nr = ' + @newVersion + ' where Nr = ' + @currentVersion + ' exec ' +
	@getExecutedProcedure + ' ' + @getParameter1 + ' ' +
	@getParameter2 + ' ' + @getParameter3 + ' ' + @getParameter4 + ' ' + @getParameter5
	print(@sqlQuery)
	exec(@sqlQuery)
	end
go

exec switchVersion '0', '1'

insert into History values (0, 1, 'rbAddTable', 'Adresa', '', '', '', '')

drop table History

create table History (
Current_version int,
Previous_version int,
Executed_procedure varchar(MAX),
Parameter1 varchar(20),
Parameter2 varchar(20),
Parameter3 varchar(20),
Parameter4 varchar(20),
Parameter5 varchar(20))

insert into History (Current_version) values (0)
insert into History values (1, 0, 'addTable', 'Adresa', '', '', '', '')
insert into History values (0, 1, 'rbAddTable', 'Adresa', '', '', '', '')

exec switchVersion '1', '0'
exec switchVersion '2','1'
exec switchVersion '3','2'
exec switchVersion '4','3'
exec switchVersion '5','4'
exec switchVersion '4','5'
exec switchVersion '3','4'
exec switchVersion '2','3'
exec switchVersion '1','2'
exec switchVersion '0','1'

drop procedure switchVersion

