# Getting Started

### Rövid leírás

Megvalósultak a kötelezően elvárt funkciók, illetve a nem kötelezőek közül a fájlból való 
feltöltés. Adatbázis az ajánlott postgres. Cím létrehozás az partner létrehozással együtt történik.
Csak PartnerService-hez és AddressServicehez lett írva unit teszt. Fájl feltöltéshez egy mintafájl 
resource/template mappában található.

### Futtatáshoz szükséges rövid leírás

* java 21, SpringBoot 3.4.2, maven
* Alkalmazás indítása során (mvn spring-boot:run vagy ideaból) Spring docker compose komponenssel elindítja az adatbázis is
* liquibase adatbázis migrációhoz
* swagger url: http://localhost:8080/swagger-ui/index.html#/
* Először szükséges bejelentkezni:
  * auth-controller/login funkcióval user/pw megadás, generálódik egy jwt token, majd ezt a token szükséges bemásolni 
  swagger Athorize-be
  * előre létrehozott userek/pw párosok:
    * admin/Admin123
    * user/User123
  * két role lett definiálva: ADMIN és USER
  * létrehozás és frissítés funkciók csak ADMIN role-al engedélyezett, míg keresések USER role-al is

  

