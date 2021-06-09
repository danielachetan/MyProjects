create procedure checkRoute (@route int, @bahnhof int, @ankunftszeit time, @abfahrtszeit time)
as
	if exists (select * from Stundenplan where route = @route and bahnhof = @bahnhof)
	begin
		update Stundenplan
		set ankunftszeit = @ankunftszeit, abfahrtszeit = @abfahrtszeit
		where route = @route and bahnhof = @bahnhof
	end
	else
	begin
		insert into Stundenplan (route,bahnhof,ankunftszeit,abfahrtszeit)
		values (@route,@bahnhof,@ankunftszeit,@abfahrtszeit)
	end
go

exec checkRoute 1,12,'14:40','14:45'
exec checkRoute 1,5,'20:35','21:00'

create function getBahnhofe (@zeitpunkt time)
returns table
as
	return
		select distinct *
		from Bahnhof B
		join Stundenplan S on B.BahnhofId = S.Bahnhof
		where S.ankunftszeit <= @zeitpunkt and S.abfahrtszeit >= @zeitpunkt

select *
from getBahnhofe('14:32')

create view minRouten as
	select top(2) R.name
	from Route R
	join Stundenplan S on R.routeId = S.route
	group by R.routeId, R.name
	having count(S.bahnhof) < 5
	order by count(S.bahnhof) 

select *
from minRouten


