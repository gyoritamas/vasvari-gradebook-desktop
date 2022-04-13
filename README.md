# Gradebook Desktop

## Az alkalmazás célja
Az alkalmazás a Gradebook API-val együtt biztosítja, hogy a felhasználó egy asztali alkalmazáson keresztül
hozzáférjen az elektronikus napló tartalmához.

## Az alkalmazás indítása
A gradebook-desktop projekt gyökérkönyvtárából adjuk ki az
```
mvn clean install -DskipTests
```
majd a
```
java -jar target/gradebook-desktop-0.0.1-SNAPSHOT.jar
```
parancsot.

(A parancs használatához Apache Maven szükséges, a telepítés lépései [itt](https://maven.apache.org/install.html) találhatók.)

## Az alkalmazás használata

### Bejelentkezés

| felhasználónév    | jelszó       | szerepkör     |
|-------------------|--------------|---------------|
| admin             | admin        | rendszergazda |
| fazekasmarianna76 | 9MqBNJacj6N6 | tanár         |


## Alkalmazott technológia
- Spring Boot
- JavaFX