create or replace function aktualizuj_ilosc_ksiazek()
    returns trigger
    as $$
    declare
        rec record;
        i integer;
    begin
        select count(*) into rec from ksiazka where tytul = new.tytul;
        i := rec.count;
        update ksiazka set ilosc_kopii = i where tytul = new.tytul;
        return new;
    end; $$ language 'plpgsql';

create trigger aktualizuj_ilosc_ksiazek_wyzw after insert or update of tytul on ksiazka
    for each row execute procedure aktualizuj_ilosc_ksiazek();

-- zapisuje imie i nazwisko aby zaczynaly sie od wielkiej litery nie wazne jak sie je wpisze
create or replace function normalizacja_osob()
    returns trigger
    as $$
    begin
        if new.imie is not null then
            new.imie := lower(new.imie);
            new.imie := initcap(new.imie);
        end if;
        if new.nazwisko is not null then
            new.nazwisko := lower(new.nazwisko);
            new.nazwisko := initcap(new.nazwisko);
        end if;
        return new;
    end; $$ language 'plpgsql';

create trigger normalizacja_czytelnik_wyzw before insert or update on czytelnik
    for each row execute procedure normalizacja_osob();
    
create trigger normalizacja_autor_wyzw before insert or update on autor
    for each row execute procedure normalizacja_osob();

-- umozliwia wpisywanie nowych wypozyczen tylko przez podanie id_czytelnika i tytulu
-- np. insert into wypozyczenie values(id_czytelnik, tytul) values (1,'tytul')
create or replace function wypozyczenie_ksiazki()
    returns trigger
    as $$
    declare
        rec record;
        rec2 record;
    begin
        select * into rec from ksiazka where tytul = new.tytul limit 1;
        -- czy jest ksiazka o takim tytule
        if found then   
            select * into rec from ksiazka where tytul = new.tytul and dostepnosc = 'true' limit 1;
            -- czy jest dostepna ksiazka o takim tytule
            if found then
                new.id_ksiazka := rec.id_ksiazka;
                select CURRENT_DATE as time_now into rec2;
                new.data_wypozyczenia := rec2.time_now;
                if new.data_zwrotu is null then
                    update ksiazka set dostepnosc = 'false' where id_ksiazka = rec.id_ksiazka;
                else
                    update ksiazka set dostepnosc = 'true' where id_ksiazka = rec.id_ksiazka;
                end if;
                return new;
            end if;
            -- nie dostepna ksiazka o takim tytule, nastepuje jej rezerwacja
            select * into rec from ksiazka where tytul = new.tytul limit 1;
            insert into rezerwacja(id_ksiazka, id_czytelnik, id_status_rezerwacji, data_rezerwacji)
            values (rec.id_ksiazka, new.id_czytelnik, 1, CURRENT_DATE);
        end if;
        return null;
    end; $$ language 'plpgsql';

create trigger wypozyczenie_ksiazki_wyzw before insert on wypozyczenie
    for each row execute procedure wypozyczenie_ksiazki();

-- po oddaniu ksiazki zostanie ona dostepna lub wypozyczona jesli ktos ja zarezerowal
-- powinno uzywac sie to w taki sposob:
-- update wypozyczenie set data_zwrotu = current_date where 
-- id_wypozyczenie=(select id_wypozyczenie from wypozyczenie where id_ksiazka=
-- 1 and id_czytelnik=1 and data_zwrotu is null limit 1)
create or replace function oddanie_ksiazki()
    returns trigger
    as $$
    declare
        rec record;
        rec2 record;
        query text;
        m_tytul text;
    begin
        -- czy ksiazka jest zwracana
        if new.data_zwrotu is not null then
            -- ksiazka bedzie dostepna
            update ksiazka set dostepnosc = 'true' where id_ksiazka = new.id_ksiazka;
            select * into rec from ksiazka where id_ksiazka=new.id_ksiazka;
            m_tytul := rec.tytul;
            query := 'select * from ksiazka where tytul = $1';
            -- czy ktos rezerwowal ten tytul ksiazki
            for rec in execute query using m_tytul loop
                select * into rec2 from rezerwacja where id_ksiazka = rec.id_ksiazka and id_status_rezerwacji = 1 order by data_rezerwacji limit 1;
                if found then
                    -- jesli ktos rezerwowal zostanie mu wypozyczona
                    insert into wypozyczenie(id_czytelnik, tytul) values (rec2.id_czytelnik, m_tytul);
                    update rezerwacja set id_status_rezerwacji = 2 where id_rezerwacja=rec2.id_rezerwacja;
                    exit;
                end if;
            end loop;
        end if;
        return new;
    end; $$ language 'plpgsql';

create trigger oddanie_ksiazki_wyzw after update on wypozyczenie
    for each row execute procedure oddanie_ksiazki();

create or replace function informacje_o_ksiazkach(tryb int)
    returns table(id int, tytul text, autor text, wydawnictwo text, kategoria text)
    as $$
    declare
        query text;
        rec record;
    begin
        if tryb = 0 then
        -- informacje o ksiazkach bez kategorii
            query := 'select k.id_ksiazka, k.tytul, a.imie||'' ''|| a.nazwisko as imnaz, w.nazwa from ksiazka k, autor a, wydawnictwo w, ksiazka_autora ka, ksiazka_wydawnictwa kw
            where k.id_ksiazka = ka.id_ksiazka and ka.id_autor = a.id_autor and k.id_ksiazka = kw.id_ksiazka and w.id_wydawnictwo = kw.id_wydawnictwo';
            for rec in execute query loop
                id := rec.id_ksiazka;
                tytul := rec.tytul;
                autor := rec.imnaz;
                wydawnictwo := rec.nazwa;
                return next;
            end loop;
        elsif tryb = 1 then
        -- informacje o ksiazkach z pelna nazwa kategorii
            query := 'with recursive kategoria_rek as(
                    select ks.id_ksiazka, tytul, nazwa, id_kat_wyzej from kategoria k, ksiazka ks where k.id_kategoria = ks.id_kategoria
                    union all
                    select rek.id_ksiazka, rek.tytul, k.nazwa||''->''||rek.nazwa, k.id_kat_wyzej from kategoria_rek rek, kategoria k where rek.id_kat_wyzej = k.id_kategoria and rek.id_kat_wyzej is not null
                )
                select k.id_ksiazka, k.tytul, a.imie||'' ''|| a.nazwisko as imnaz, w.nazwa, rek.nazwa as kat
                    from ksiazka k, autor a, wydawnictwo w, ksiazka_autora ka, ksiazka_wydawnictwa kw, kategoria_rek rek
                    where k.id_ksiazka = ka.id_ksiazka and ka.id_autor = a.id_autor 
                    and k.id_ksiazka = kw.id_ksiazka and w.id_wydawnictwo = kw.id_wydawnictwo and rek.id_kat_wyzej is null and rek.id_ksiazka = k.id_ksiazka';
            for rec in execute query loop
                id := rec.id_ksiazka;
                tytul := rec.tytul;
                autor := rec.imnaz;
                wydawnictwo := rec.nazwa;
                kategoria := rec.kat;
                return next;
            end loop;
        elsif tryb = 2 then
        -- informacje o ksiazkach z skrotowa nazwa kategorii
            query := 'select k.id_ksiazka, k.tytul, a.imie||'' ''|| a.nazwisko as imnaz, w.nazwa, kat.nazwa as kat from
            ksiazka k, autor a, wydawnictwo w, ksiazka_autora ka, ksiazka_wydawnictwa kw, kategoria kat
            where k.id_ksiazka = ka.id_ksiazka and ka.id_autor = a.id_autor and k.id_ksiazka = kw.id_ksiazka 
            and w.id_wydawnictwo = kw.id_wydawnictwo and kat.id_kategoria = k.id_kategoria';
            for rec in execute query loop
                id := rec.id_ksiazka;
                tytul := rec.tytul;
                autor := rec.imnaz;
                wydawnictwo := rec.nazwa;
                kategoria := rec.kat;
                return next;
            end loop;
        end if; 
    end; $$ language 'plpgsql';

-- zwraca informacje o kategoriach
create or replace function kategoria_fun() returns table(id_kategoria int, nazwa text) as $$
    declare
        rec record;
        query text;
    begin
        query := 'with recursive kategoria_rek as(
            select id_kategoria, nazwa, id_kat_wyzej from kategoria k
            union all
            select rek.id_kategoria, k.nazwa||''->''||rek.nazwa, k.id_kat_wyzej from kategoria_rek rek, kategoria k where rek.id_kat_wyzej = k.id_kategoria and rek.id_kat_wyzej is not null
        )
        select * from kategoria_rek where id_kat_wyzej is null';
    
        for rec in execute query loop
            id_kategoria := rec.id_kategoria;
            nazwa := rec.nazwa;
            return next;
        end loop;
    end; $$ language 'plpgsql';


-- dodaje nowa ksiazke i autora/wydawnictwo jesli podanego jeszcze nie ma
create or replace function dodaj_ksiazke_i_zwiazane(ktytul text, kid_kat int, aimie text, anazwisko text, wnazwa text)
returns void as $$
    declare
        rec record;
        rec2 record;
    begin
        insert into ksiazka(tytul, id_kategoria) values (ktytul, kid_kat);
        select max(id_ksiazka) into rec2 from ksiazka;

        select * into rec from autor a where imie=aimie and nazwisko=anazwisko;
        if not found then
            insert into autor(imie, nazwisko) values (aimie, anazwisko);
        end if;
        select * into rec from autor a where imie=aimie and nazwisko=anazwisko;

        insert into ksiazka_autora(id_ksiazka, id_autor) values (rec2.max, rec.id_autor);

        select * into rec from wydawnictwo w where nazwa = wnazwa;
        if not found then 
            insert into wydawnictwo(nazwa) values (wnazwa);
        end if;
        select * into rec from wydawnictwo w where nazwa = wnazwa;

        insert into ksiazka_wydawnictwa(id_ksiazka, id_wydawnictwo) values (rec2.max, rec.id_wydawnictwo);

    end; $$ language 'plpgsql';


-- przed usunieciem czytelnika zmienia jego wypozyczenia i rezerwacje na bezosobowe (id_czytelnika bedzie rowne 0 w tych encjach)
create or replace function usun_powiazane_z_czytelnikiem()
returns trigger as $$
    declare
        rec record;
    begin   
        update wypozyczenie set id_czytelnik = null where id_czytelnik = old.id_czytelnik;
        update rezerwacja set id_czytelnik = null where id_czytelnik = old.id_czytelnik;
        return old;
    end; $$ language 'plpgsql';

create trigger usun_powiazane_z_czytelnikiem_trigger before delete on czytelnik
    for each row execute procedure usun_powiazane_z_czytelnikiem();


-- sprawdza czy czytelnik nie ma wypozyczonych ksiazek obecnie jesli nie anuluje jego rezerwacje 
-- nalezy uzywac tylko jesli zaraz chce sie usunac czytelnika i trzeba przed kazdym usunieciem uzyć tej funkcji
create or replace function czy_do_usuniecia(id_czytelnik_arg int)
    returns text as $$
    declare
        rec record;
    begin
        select * into rec from czytelnik where id_czytelnik = id_czytelnik_arg;
        if found then
            select * into rec from wypozyczenie where id_czytelnik = id_czytelnik_arg and data_zwrotu is null;
            if found then
                return 'not returned';
            end if;
            update rezerwacja set id_status_rezerwacji = 3 where id_czytelnik = id_czytelnik_arg and id_status_rezerwacji = 1;
            return 'can be deleted';
        end if;
        return 'not found';
    end; $$ language 'plpgsql';

-- sprawdza czy jest czytelnik o danym id
create or replace function czy_jest_czytelnik(id_czytelnik_arg int)
    returns text as $$
    declare
        rec record;
    begin
        select * into rec from czytelnik where id_czytelnik = id_czytelnik_arg;
        if found then
            return 'found';
        end if;
        return 'not found';
    end; $$ language 'plpgsql';

-- sprawdza czy jest ksiazka o danym tytule
create or replace function czy_jest_ksiazka(tytul_arg text)
    returns text as $$
    declare
        rec record;
    begin
        select * into rec from ksiazka where tytul = tytul_arg;
        if found then
            return 'found';
        end if;
        return 'not found';
    end; $$ language 'plpgsql';

-- sprawdza czy jest wypozyczona obecnie dana ksiazka przez dana osobe
create or replace function czy_jest_wypozyczenie(id_czytelnik_arg int, id_ksiazka_arg int)
    returns text as $$
    declare
        rec record;
    begin
        select * into rec from wypozyczenie where id_czytelnik = id_czytelnik_arg
        and id_ksiazka = id_ksiazka_arg and data_zwrotu is null;
        if found then
            return 'found';
        end if;
        return 'not found';
    end; $$ language 'plpgsql';


-- sprawdza czy ksiazka jest mozliwa do wypozyczenia
create or replace function czy_jest_do_wypozyczenia(tytul_arg text)
    returns text as $$
    declare
        rec record;
    begin
        select * into rec from ksiazka where tytul = tytul_arg and dostepnosc = 'true';
        if found then
            return 'found';
        end if;
        return 'not found';
    end; $$ language 'plpgsql';