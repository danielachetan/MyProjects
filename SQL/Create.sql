create table Zug (
	zugId int,
	name varchar(20),
	zugtyp int,
	primary key (zugId)
)

create table Route (
	routeId int,
	name varchar(MAX),
	zug int,
	primary key (routeId)
)

create table Bahnhof (
	bahnhofId int,
	name varchar(50),
	primary key (bahnhofId)
)

create table Stundenplan (
	route int,
	bahnhof int,
	ankunftszeit time,
	abfahrtszeit time,
	primary key (route,bahnhof)
)

create table Zugtyp (
	zugtypId int,
	beschreibung varchar(MAX),
	primary key (zugtypId)
)

alter table Zug
add constraint FK_Zug_Zugtyp
foreign key (zugtyp)
references Zugtyp

alter table Route
add constraint FK_Route_Zug
foreign key (zug)
references Zug

alter table Stundenplan
add constraint FK_Stundenplan_Route
foreign key (route)
references Route

alter table Stundenplan
add constraint FK_Stundenplan_Bahnhof
foreign key (bahnhof)
references Bahnhof

