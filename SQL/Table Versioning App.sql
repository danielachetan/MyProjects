exec addTable 'Adresa'
select * from History1
select * from Version

exec addColumn 'Adresa', 'Strada', 'varchar', 'MAX'
select * from History1
select * from Version


exec modifyColumnType 'Adresa', 'Strada', 'varchar', '10'
select * from History1
select * from Version

exec addDefaultConstraint 'Produs', 'priceConstraint', '0', 'Pret'
select * from History1
select * from Version

exec addFkConstraint 'Inventar', 'inv_prodConstraint', 'Id_produs', 'Produs', 'delete cascade'
select * from History1
select * from Version

exec switchVersion 5
select * from History1
select * from Version
