/*
Afiseaza numele magazinelor care colaboreaza cu furnizorul 'Dynamis Impex' sau 'Alcom'.
*/
select distinct M.Nume
from Magazin M
join Chitanta C on M.Numar_magazin = C.Numar_magazin
join Factura F on C.Numar_factura = F.Numar
join Furnizor Fu on F.ID_furnizor = Fu.ID
where Fu.Nume = 'Dynamis Impex' or Fu.Nume = 'Alcom'


/*
Afiseaza adresa furnizorului care furnizeaza produsul cu pretul mai mare sau egal decat preturile tuturor produselor, al caror nume
incepe cu 'C'.
*/
select *
from Adresa A
join Furnizor F on A.ID = F.Adresa
join Produs P on F.ID = P.ID_furnizor
where P.Pret >= all (select Pret
					from Produs
					where Nume like 'C%')


/*
Afiseaza numele produselor care se gasesc in magazinele Kaufland1 si Kaufland2.
*/
select P.Nume
from Produs P
join Inventar I on P.Cod = I.Cod_produs
join Magazin M on I.Numar_magazin = M.Numar_magazin
where M.Nume = 'Kaufland 1'
intersect
select P.Nume
from Produs P
join Inventar I on P.Cod = I.Cod_produs
join Magazin M on I.Numar_magazin = M.Numar_magazin
where M.Nume = 'Kaufland 2'


/*
Afiseaza numele furnizorilor care furnizeaza produse care nu apar pe nicio comanda, cu exceptie cazul in care numele produsului este
'Cheddar'.
*/
select F.Nume
from Furnizor F
join Produs P on F.ID = P.ID_furnizor
left outer join Comanda C on P.Cod = C.Cod_produs
where C.Cod_produs is null
except
select F.Nume
from Furnizor F
join Produs P on F.ID = P.ID_furnizor
where P.Nume = 'Cheddar'


/*
Afiseaza angajatul responsabil pentru chitante cu cel mai mare salar pentru fiecare zi in care s-a emis o factura.
*/
select F.Data, max(A.Salar) as Salar_responsabil
from Angajat A
join Chitanta C on A.ID = C.ID_responsabil
join Factura F on C.Numar_factura = F.Numar
group by F.Data


/*
Afiseaza produsele expirate din magazinele Kaufland 1 si Kaufland 3.
*/
select M.Nume, min(P.Data_expirarii) as Data_expirarii
from Produs P
join Inventar I on P.Cod = I.Cod_produs
join Magazin M on I.Numar_magazin = M.Numar_magazin
group by M.Nume
having min(P.Data_expirarii) < '2019-11-06'


/*
Afiseaza valoarea totala a facturilor din fiecare zi de dupa 29.10.2019.
*/
select F.Data, sum(P.Pret * C.Cantitate_produs)
from Produs P
join Comanda C on P.Cod = C.Cod_produs
join Factura F on C.Numar_factura = F.Numar
group by F.Data
having sum(P.Pret * C.Cantitate_produs) > 266000



/*
Afiseaza toate magazinele din Bucuresti.
*/
select *
from Magazin M
where M.Adresa in (select A.ID
					from Adresa A
					where A.Oras = 'Bucuresti')


/*
Afiseaza primii doi angajati cu salarul cel mai mare care nu lucreaza la Kaufland 4.
*/
select top(2) with ties *
from Angajat A
where A.Numar_magazin not in (select M.Numar_magazin
								from Magazin M
								where M.Nume = 'Kaufland 4')
order by A.Salar desc


/*
Afiseaza toate produsele cu pretul mai mare decat cel al unui produs furnizat de Profy.
*/
select P.Nume, P.Pret
from Produs P
where P.Pret > any (select P.Pret
					from Produs P, Furnizor F
					where P.ID_furnizor = F.ID and F.Nume = 'Profy')

/*
Afiseaza produsele care sunt in Kaufland 4 sau Kaufland 5.
*/
select P.*, M.Nume
from Produs P
join Inventar I on P.Cod = I.Cod_produs
join Magazin M on I.Numar_magazin = M.Numar_magazin
where I.Numar_magazin = any (select M.Numar_magazin
							from Magazin M
							where M.Nume = 'Kaufland 4'
							union
							select M.Numar_magazin
							from Magazin M
							where M.Nume = 'Kaufland 5')

select A.Nume
from Angajat A, Magazin M
where M.Nume='Kaufland 1' and M.Numar_magazin=A.Numar_magazin