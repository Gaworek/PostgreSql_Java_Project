CREATE TABLE status_rezerwacji (
  id_status_rezerwacji SERIAL  NOT NULL ,
  nazwa TEXT   NOT NULL   ,
PRIMARY KEY(id_status_rezerwacji));




CREATE TABLE wydawnictwo (
  id_wydawnictwo SERIAL  NOT NULL ,
  nazwa TEXT    NOT NULL  ,
PRIMARY KEY(id_wydawnictwo));




CREATE TABLE kategoria (
  id_kategoria SERIAL  NOT NULL ,
  id_kat_wyzej INTEGER   ,
  nazwa TEXT     NOT NULL ,
PRIMARY KEY(id_kategoria)  ,
  FOREIGN KEY(id_kat_wyzej)
    REFERENCES kategoria(id_kategoria));


CREATE INDEX kategoria_FKIndex1 ON kategoria (id_kat_wyzej);


CREATE INDEX IFK_Rel_11 ON kategoria (id_kat_wyzej);


CREATE TABLE autor (
  id_autor SERIAL  NOT NULL ,
  imie TEXT   NOT NULL ,
  nazwisko TEXT     NOT NULL ,
PRIMARY KEY(id_autor));




CREATE TABLE czytelnik (
  id_czytelnik SERIAL  NOT NULL ,
  imie TEXT   NOT NULL ,
  nazwisko TEXT     NOT NULL ,
PRIMARY KEY(id_czytelnik));




CREATE TABLE ksiazka (
  id_ksiazka SERIAL  NOT NULL ,
  tytul TEXT   NOT NULL ,
  id_kategoria INTEGER   NOT NULL ,
  ilosc_kopii INTEGER  DEFAULT 1  ,
  dostepnosc BOOL   DEFAULT TRUE   ,
PRIMARY KEY(id_ksiazka)  ,
  FOREIGN KEY(id_kategoria)
    REFERENCES kategoria(id_kategoria));


CREATE INDEX ksiazka_FKIndex1 ON ksiazka (id_kategoria);


CREATE INDEX IFK_Rel_12 ON ksiazka (id_kategoria);


CREATE TABLE wypozyczenie (
  id_wypozyczenie serial,
  id_ksiazka INTEGER   NOT NULL ,
  id_czytelnik INTEGER   NULL ,
  tytul text NOT NULL,
  data_wypozyczenia DATE    ,
  data_zwrotu DATE      ,
PRIMARY KEY(id_wypozyczenie)    ,
  FOREIGN KEY(id_ksiazka)
    REFERENCES ksiazka(id_ksiazka),
  FOREIGN KEY(id_czytelnik)
    REFERENCES czytelnik(id_czytelnik));


CREATE INDEX ksiazka_has_czytelnik_FKIndex1 ON wypozyczenie (id_ksiazka);
CREATE INDEX ksiazka_has_czytelnik_FKIndex2 ON wypozyczenie (id_czytelnik);


CREATE INDEX IFK_Rel_05 ON wypozyczenie (id_ksiazka);
CREATE INDEX IFK_Rel_06 ON wypozyczenie (id_czytelnik);


CREATE TABLE ksiazka_autora (
  id_ksiazka INTEGER   NOT NULL ,
  id_autor INTEGER   NOT NULL   ,
PRIMARY KEY(id_ksiazka, id_autor)    ,
  FOREIGN KEY(id_ksiazka)
    REFERENCES ksiazka(id_ksiazka),
  FOREIGN KEY(id_autor)
    REFERENCES autor(id_autor));


CREATE INDEX ksiazka_has_autor_FKIndex1 ON ksiazka_autora (id_ksiazka);
CREATE INDEX ksiazka_has_autor_FKIndex2 ON ksiazka_autora (id_autor);


CREATE INDEX IFK_Rel_05 ON ksiazka_autora (id_ksiazka);
CREATE INDEX IFK_Rel_06 ON ksiazka_autora (id_autor);


CREATE TABLE ksiazka_wydawnictwa (
  id_ksiazka INTEGER   NOT NULL ,
  id_wydawnictwo INTEGER   NOT NULL   ,
PRIMARY KEY(id_ksiazka, id_wydawnictwo)    ,
  FOREIGN KEY(id_ksiazka)
    REFERENCES ksiazka(id_ksiazka),
  FOREIGN KEY(id_wydawnictwo)
    REFERENCES wydawnictwo(id_wydawnictwo));


CREATE INDEX ksiazka_has_wydawnictwo_FKIndex1 ON ksiazka_wydawnictwa (id_ksiazka);
CREATE INDEX ksiazka_has_wydawnictwo_FKIndex2 ON ksiazka_wydawnictwa (id_wydawnictwo);


CREATE INDEX IFK_Rel_03 ON ksiazka_wydawnictwa (id_ksiazka);
CREATE INDEX IFK_Rel_04 ON ksiazka_wydawnictwa (id_wydawnictwo);


CREATE TABLE rezerwacja (
  id_rezerwacja serial,
  id_ksiazka INTEGER   NOT NULL ,
  id_czytelnik INTEGER   NULL ,
  id_status_rezerwacji INTEGER   NOT NULL ,
  data_rezerwacji DATE      ,
PRIMARY KEY(id_rezerwacja)      ,
  FOREIGN KEY(id_ksiazka)
    REFERENCES ksiazka(id_ksiazka),
  FOREIGN KEY(id_czytelnik)
    REFERENCES czytelnik(id_czytelnik),
  FOREIGN KEY(id_status_rezerwacji)
    REFERENCES status_rezerwacji(id_status_rezerwacji));


CREATE INDEX ksiazka_has_czytelnik_FKIndex1 ON rezerwacja (id_ksiazka);
CREATE INDEX ksiazka_has_czytelnik_FKIndex2 ON rezerwacja (id_czytelnik);
CREATE INDEX rezerwacja_FKIndex3 ON rezerwacja (id_status_rezerwacji);


CREATE INDEX IFK_Rel_07 ON rezerwacja (id_ksiazka);
CREATE INDEX IFK_Rel_08 ON rezerwacja (id_czytelnik);
CREATE INDEX IFK_Rel_10 ON rezerwacja (id_status_rezerwacji);