# Gradebook Desktop

## Az alkalmazás célja
Az alkalmazás a Gradebook API-val együtt biztosítja, hogy a felhasználó egy asztali alkalmazáson keresztül
hozzáférjen az elektronikus napló tartalmához.

## Az alkalmazás indítása
Adjuk ki az 
```
mvn clean install -DskipTests
```
parancsot az alkalmazás gyökérkönyvtárából. Indítsuk el a Gradebook API-t, majd futtassuk a programot a 
```
java -jar ./target/gradebook-0.0.1-SNAPSHOT.jar
```
paranccsal.

## Az alkalmazás használata
### Regisztráció és bejelentkezés
Az alkalmazás jelenleg bejelentkezett állapotban indul. A *Kijelentkezés* gomb a bejelentkezési ablakba navigálja
a felhasználót. Itt lehetőségünk van bejelentkezni, vagy regisztrálhatunk egy új felhasználót. Regisztrációhoz egy
legalább 8 karakter hosszú, legalább egy nagybetűt és egy számot tartalmazó jelszó megadása szükséges.
(A felhasználó adatainak mentése jelenleg nem az adatbázisba történik, így az az alkalmazás újraindítása után elveszik.)

### Navigálás az alkalmazásban
A bal oldali menü gombokkal navigálhatunk a különböző "lapok" között.
*Tanulók* alatt megjelennek az adatbázisban szereplő tanulók adatai. A jobb oldalon található *Teszt* gomb egy új tanulót
ad a táblázathoz, előre megadott példa értékekkel. A *Frissítés* gombbal újra lekérdezhetjük az adatokat.
*Tantárgyak* alatt az adatbázisban tárolt tantárgyak jelennek meg. A *Teszt* és *Frissítés* gombok hasonló feladatot
látnak el, mint a *Tanulóknál*.

## Tervezett funkciók
Az alkalmazás kezdeti fejlesztési fázisban van. Elsődleges feladat lenne az adatbázis további két táblájában
szereplő adatok megjelenítése, majd űrlapok felvétele új adatok beviteléhez, valamint szerkesztés és törlés megvalósítása
különböző szűrési lehetőségek az adatok megjelenítésénél.

## Alkalmazott technológia
- Spring Boot
- JavaFX