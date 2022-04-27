insert into kategoria(id_kat_wyzej, nazwa) values (null, 'liryka');
insert into kategoria(id_kat_wyzej, nazwa) values (null, 'epika');
insert into kategoria(id_kat_wyzej, nazwa) values (null, 'dramat');
insert into kategoria(id_kat_wyzej, nazwa) values (1, 'oda');
insert into kategoria(id_kat_wyzej, nazwa) values (1, 'piesn');
insert into kategoria(id_kat_wyzej, nazwa) values (1, 'fraszka');
insert into kategoria(id_kat_wyzej, nazwa) values (2, 'powiesc');
insert into kategoria(id_kat_wyzej, nazwa) values (2, 'nowela');
insert into kategoria(id_kat_wyzej, nazwa) values (2, 'basn');
insert into kategoria(id_kat_wyzej, nazwa) values (3, 'tragedia');
insert into kategoria(id_kat_wyzej, nazwa) values (3, 'komedia');
insert into kategoria(id_kat_wyzej, nazwa) values (5, 'milosna');
insert into kategoria(id_kat_wyzej, nazwa) values (5, 'biesiadna');
insert into kategoria(id_kat_wyzej, nazwa) values (5, 'zalobna');
insert into kategoria(id_kat_wyzej, nazwa) values (6, 'milosna');
insert into kategoria(id_kat_wyzej, nazwa) values (6, 'biesiadna');
insert into kategoria(id_kat_wyzej, nazwa) values (6, 'satyryczna');
insert into kategoria(id_kat_wyzej, nazwa) values (7, 'historyczna');
insert into kategoria(id_kat_wyzej, nazwa) values (7, 'przygodowa');
insert into kategoria(id_kat_wyzej, nazwa) values (7, 'fantastyczna');
insert into kategoria(id_kat_wyzej, nazwa) values (7, 'kryminalna');
insert into kategoria(id_kat_wyzej, nazwa) values (8, 'spoleczna');
insert into kategoria(id_kat_wyzej, nazwa) values (8, 'codzienna');
insert into kategoria(id_kat_wyzej, nazwa) values (10, 'grecka');
insert into kategoria(id_kat_wyzej, nazwa) values (10, 'rzymska');
insert into kategoria(id_kat_wyzej, nazwa) values (11, 'grecka');
insert into kategoria(id_kat_wyzej, nazwa) values (11, 'rzymska');
insert into kategoria(id_kat_wyzej, nazwa) values (20, 'low-fantasy');
insert into kategoria(id_kat_wyzej, nazwa) values (20, 'high-fantasy');
insert into kategoria(id_kat_wyzej, nazwa) values (20, 'sci-fi');

insert into ksiazka(id_kategoria, tytul) values (30, 'Dune');--Frank Herbert
insert into ksiazka(id_kategoria, tytul) values (30, 'Dune');--Frank Herbert
insert into ksiazka(id_kategoria, tytul) values (30, 'Solaris');--Stanislaw Lem
insert into ksiazka(id_kategoria, tytul) values (29, 'Piesn lodu i ognia');--George Martin
insert into ksiazka(id_kategoria, tytul) values (29, 'Piesn lodu i ognia');--George Martin
insert into ksiazka(id_kategoria, tytul) values (29, 'Piesn lodu i ognia');--George Martin
insert into ksiazka(id_kategoria, tytul) values (29, 'Wladca Pierscieni');--J.R.R Tolkien
insert into ksiazka(id_kategoria, tytul) values (29, 'Wladca Pierscieni');--J.R.R Tolkien
insert into ksiazka(id_kategoria, tytul) values (29, 'Wladca Pierscieni');--J.R.R Tolkien
insert into ksiazka(id_kategoria, tytul) values (29, 'Wladca Pierscieni');--J.R.R Tolkien
insert into ksiazka(id_kategoria, tytul) values (29, 'Wladca Pierscieni');--J.R.R Tolkien
insert into ksiazka(id_kategoria, tytul) values (28, 'The Dolls House');--rumer godden
insert into ksiazka(id_kategoria, tytul) values (28, 'The Borrowers');--Mary Norton
insert into ksiazka(id_kategoria, tytul) values (27, 'Rycerze');--Arystofanes
insert into ksiazka(id_kategoria, tytul) values (27, 'Osy');
insert into ksiazka(id_kategoria, tytul) values (26, 'Siostry');--Plaut
insert into ksiazka(id_kategoria, tytul) values (26, 'Punijczyk');
insert into ksiazka(id_kategoria, tytul) values (25, 'Roczniki');--Enniusz
insert into ksiazka(id_kategoria, tytul) values (24, 'Antygona');--Sofokles
insert into ksiazka(id_kategoria, tytul) values (24, 'Krol Edyp');
insert into ksiazka(id_kategoria, tytul) values (24, 'Krol Edyp');
insert into ksiazka(id_kategoria, tytul) values (24, 'Medea');--Eurypides
insert into ksiazka(id_kategoria, tytul) values (24, 'Ion');
insert into ksiazka(id_kategoria, tytul) values (23, 'Kamizelka');--Boleslaw Prus
insert into ksiazka(id_kategoria, tytul) values (23, 'Kamizelka');--Boleslaw Prus
insert into ksiazka(id_kategoria, tytul) values (22, 'Latarnik');--Henryk Sienkiewicz
insert into ksiazka(id_kategoria, tytul) values (21, 'Ojciec chrzestny');--Mario Puzo
insert into ksiazka(id_kategoria, tytul) values (21, 'Sherlock Holmes');--Arthur Doyle
insert into ksiazka(id_kategoria, tytul) values (19, 'W pustyni i w puszczy');--H.S.
insert into ksiazka(id_kategoria, tytul) values (18, 'Quo vadis');
insert into ksiazka(id_kategoria, tytul) values (18, 'Quo vadis');
insert into ksiazka(id_kategoria, tytul) values (18, 'Krzyzacy tom 1');
insert into ksiazka(id_kategoria, tytul) values (18, 'Krzyzacy tom 1');
insert into ksiazka(id_kategoria, tytul) values (18, 'Krzyzacy tom 1');
insert into ksiazka(id_kategoria, tytul) values (17, 'O doktorze Hiszpanie');--J.Kochanowski

insert into wydawnictwo(nazwa) values ('Polskie wydawnictwo');
insert into wydawnictwo(nazwa) values ('ELiS');
insert into wydawnictwo(nazwa) values ('DKC');
insert into wydawnictwo(nazwa) values ('Amerykanskie wydawnictwo');

insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (1, 1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (1, 2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (1, 3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (2,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (3,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (4,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (5,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (6,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (7,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (8,2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (9,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (10,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (11,2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (12,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (13,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (14,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (15,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (16,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (17,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (18,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (18,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (19,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (19,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (20,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (21,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (22,2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (22,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (23,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (24,2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (25,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (26,4);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (27,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (28,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (29,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (30,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (31,1);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (32,2);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (33,3);
insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (34,3);

insert into autor(imie, nazwisko) values ('Frank','Herbert');
insert into autor(imie, nazwisko) values ('Stanislaw','Lem');
insert into autor(imie, nazwisko) values ('George','Martin');
insert into autor(imie, nazwisko) values ('John','Tolkien');
insert into autor(imie, nazwisko) values ('Rumer','Godden');
insert into autor(imie, nazwisko) values ('Mary','Norton');
insert into autor(imie, nazwisko) values ('Arystofanes','');
insert into autor(imie, nazwisko) values ('Plaut','');
insert into autor(imie, nazwisko) values ('Enniusz','');
insert into autor(imie, nazwisko) values ('Sofokles','');
insert into autor(imie, nazwisko) values ('Eurypides','');
insert into autor(imie, nazwisko) values ('Boleslaw','Prus');
insert into autor(imie, nazwisko) values ('Henryk','Sienkiewicz');
insert into autor(imie, nazwisko) values ('Mario','Puzo');
insert into autor(imie, nazwisko) values ('Jan','Kochanowski');
insert into autor(imie, nazwisko) values('Arthur','Doyle');

insert into ksiazka_autora(id_ksiazka, id_autor) values(1,1);
insert into ksiazka_autora(id_ksiazka, id_autor) values(2,1);
insert into ksiazka_autora(id_ksiazka, id_autor) values(3,2);
insert into ksiazka_autora(id_ksiazka, id_autor) values(4,3);
insert into ksiazka_autora(id_ksiazka, id_autor) values(5,3);
insert into ksiazka_autora(id_ksiazka, id_autor) values(6,3);
insert into ksiazka_autora(id_ksiazka, id_autor) values(7,4);
insert into ksiazka_autora(id_ksiazka, id_autor) values(8,4);
insert into ksiazka_autora(id_ksiazka, id_autor) values(9,4);
insert into ksiazka_autora(id_ksiazka, id_autor) values(10,4);
insert into ksiazka_autora(id_ksiazka, id_autor) values(11,4);
insert into ksiazka_autora(id_ksiazka, id_autor) values(12,5);
insert into ksiazka_autora(id_ksiazka, id_autor) values(13,6);
insert into ksiazka_autora(id_ksiazka, id_autor) values(14,7);
insert into ksiazka_autora(id_ksiazka, id_autor) values(15,7);
insert into ksiazka_autora(id_ksiazka, id_autor) values(16,8);
insert into ksiazka_autora(id_ksiazka, id_autor) values(17,8);
insert into ksiazka_autora(id_ksiazka, id_autor) values(18,9);
insert into ksiazka_autora(id_ksiazka, id_autor) values(19,10);
insert into ksiazka_autora(id_ksiazka, id_autor) values(20,10);
insert into ksiazka_autora(id_ksiazka, id_autor) values(21,10);
insert into ksiazka_autora(id_ksiazka, id_autor) values(22,11);
insert into ksiazka_autora(id_ksiazka, id_autor) values(23,11);
insert into ksiazka_autora(id_ksiazka, id_autor) values(24,12);
insert into ksiazka_autora(id_ksiazka, id_autor) values(25,12);
insert into ksiazka_autora(id_ksiazka, id_autor) values(26,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(27,14);
insert into ksiazka_autora(id_ksiazka, id_autor) values(28,16);
insert into ksiazka_autora(id_ksiazka, id_autor) values(29,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(30,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(31,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(32,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(33,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(34,13);
insert into ksiazka_autora(id_ksiazka, id_autor) values(35,15);

insert into czytelnik(imie, nazwisko) values ('Adam','Abacki');
insert into czytelnik(imie, nazwisko) values ('Bartek','Backi');
insert into czytelnik(imie, nazwisko) values ('Celina','Cabacka');
insert into czytelnik(imie, nazwisko) values ('Dawid','Dawidowski');
insert into czytelnik(imie, nazwisko) values ('Ernest','Kowalski');
insert into czytelnik(imie, nazwisko) values ('Filip','Frackowiak');
insert into czytelnik(imie, nazwisko) values ('Grzegoch','Groch');
insert into czytelnik(imie, nazwisko) values ('Anna','Kowal');
insert into czytelnik(imie, nazwisko) values ('Julia','Nowak');
insert into czytelnik(imie, nazwisko) values ('Andrzej','Ogorek');
insert into czytelnik(imie, nazwisko) values ('mONika', 'dziuBEK'); 

insert into status_rezerwacji(nazwa) values('Oczekiwanie');
insert into status_rezerwacji(nazwa) values('Zakonczono');
insert into status_rezerwacji(nazwa) values('Anulowano');


insert into wypozyczenie(id_czytelnik, tytul)  values (1, 'Dune');
insert into wypozyczenie(id_czytelnik, tytul)  values (1, 'Dune');
insert into wypozyczenie(id_czytelnik, tytul)  values (1, 'Dune');
