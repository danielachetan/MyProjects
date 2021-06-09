/*
Andert zu 2000 den Gehalt der Angestellten, die im Store 3 arbeiten und den Gehalt kleiner als 2000 haben.
*/
update Employee 
set Salary = 2000
where Store_number = 3 and Salary < 2000

/*
Loscht die Produkte, die keine Quantitat haben.
*/
delete from Product 
where Quantity is null

/*
Addiert 10 zum Gehalt der Angestellten, die fur dem Product mit Kode 'A1' verantwortlich sind.
*/
update Employee
set Salary = Salary + 10
where Name in (select Employee_name
				from Task
				where Product_code = 'A1') 

/*
Macht ein 50% Discount fur die Produkte, die den Preis zwischen 10 und 20 haben.
*/
update Product
set Price = Price/2
where Price between 10 and 20

/*
Stellt zu 'Brot' den Namen der Produkte, deren Kode mit 'B' anfangt.
*/
update Product
set Name = 'Brot'
where Code like 'B%'