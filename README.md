Programowanie zespołowe laboratorium _**2**_ grupa _**4**_

# Dokumentacja projetu: **System do zarządzania zadaniami w hurtowni budowlanej**

## Zespoł projetowy:
imie i nazwisko z oznaczeniem lidera, pełniona rola, zakres czynności/odpowiedzialność 
Konrad Pelc - lider, Project Manager oraz Scrum Master, organizacja pracy zespołu, rozdzielenie zadań, organizacja spotkań sprintowych, dokumentacja Sprint i Manual.
Michał Moskal - Developer oraz Tester. Panel logowania i rejestracji oraz implementacja logiki CRUD w aplikacji.
Sebastian Leń - Developer oraz Tester. Diagramy UML oraz implementacja logiki CRUD w aplikacji.
Paweł Niziołek - Developer oraz Tester. Stworzenie przykładowych widoków oraz ich implementacja w SceneBuilder JavaFX.
Patryk Mokrzycki - Developer oraz Bazy danych. Stworzenie bazy danych, diagramu ERD oraz wypełnienie bazy danych przykładowymi danymi, odpowiedzialność za skrypt do utworzenia struktury bazy danych.
Tomasz Pitak - Developer oraz Tester. Odpowiedzialny za logikę biznesową zarządzania uprawnieniami użytkowników, generowanie plików PDF, implementacja logiki CRUD w aplikacji.

Wszyscy developerzy są zobowiązani do napisania testów jednostkowych JUnit dla zaimplementowanego przez siebie kodu aplikacji.

## Opis programu / systemu
Aplikacja desktopowa hurtowni budowlanej umożliwająca obsługę i zarządzanie sprzedażą materiałów budowlanych.

## Cel projektu 
Umożliwienie obsługi i zarządzania sprzedażą materiałów budolanych.

## Zakres projektu 
Baza danych.
Aplikacja dektopowa.
Dokumentacja techniczna.
Podręcznik użytkownika.

## Wymagania stawiane aplikacji / systemowi 
- System będzie zawierał panel logowania
- System powinien mieć kilka modułów 
  - Moduł administracji użytkownikami (role) 
  - Moduł raportów 
  - Moduł konfiguracji?
  - Moduł zarządzania 
- System powinien umożliwiać dodawanie/aktualizację/usuwanie oraz odczytywanie danych znajdujących się w bazie danych
- System powinien wyświetlać stan magazynowy danego produkty wraz z jego szczegółowymi danymi
- System powinien umożliwiać wykorzystywanie kodów rabatowych
- System powinien umożliwiać generowanie raportów PDF
- System powinien współpracować z bazą danych
...

## Panele / zakładki systemu, które będą oferowały potrzebne funkcjonalności 
- Panel administratora 
  - Główne narzędzie administratorów systemu umożliwiające wykonanie wszystkich czynności potrzebnych do zarządzania systemem np. dodawanie, edycja, usuwanie użytkowników, tworzenie i modyfikacja grup, zarządzanie innymi administratorami. Operacje CRUD dla wszystkich tabel w aplikacji.
- Panel pracownika 
  -  Operacje CRUD dla tabel Produkty, Zamówienia, Elementy Zamówienia w aplikacji.
  - ... kolejna funkcjonalność
...
- Zakładka raportów 
  - Generowanie raportów z przefiltrowanymi danymi z tabeli
  - Generowanie faktury sprzedaży
- Zakładka ustawień 
...

## Typy wymaganych dokumentów w projekcie oraz dostęp do nich 
- Raporty PDF 
  - rodzaje raportów
- Inne dokumenty:
  - ...

## Przepływ informacji w środowisku systemu 
Scentralizowany oparty na bazie danych.

## Użytkownicy aplikacji i ich uprawnienia 
- Administrator 
  - uprawnienie 1 
  - uprawnienie 2
  - ...
- Kierownik 
  - uprawnienie 1 
  - uprawnienie 2
  - ...
- Użytkownik
  - uprawnienie 1 
  - uprawnienie 2
  - ...

## Interesariusze 
- Interesariusze wewnętrzni 
  - Zarząd hurtowni
  - Administrator systemu
  - Pracownicy
- Interesariusze zewnętrzni 
  - 

## Diagramy UML
- ###### [Diagram przypadków użycia]
  Wstawić rys. diagramu UML
- ###### [Diagram aktywności]
Wstawić rys. diagramu UML
- ###### [Diagram sekwencji]
Wstawić rys. diagramu UML
- ###### [Diagram klas]
  Wstawić rys. diagramu UML

## Baza danych
###### Diagram ERD
![image](https://user-images.githubusercontent.com/76397174/223494468-1402ec51-7244-42fc-bc88-bae9bdd09709.png)

###### Skrypt do utworzenia struktury bazy danych
create table Klienci (
kl_id INT,
kl_imie VARCHAR(50),
kl_nazwisko VARCHAR(50),
kl_miejscowosc VARCHAR(50),
kl_ulica VARCHAR(50),
kl_nrMieszkania VARCHAR(20),
kl_nrTelefonu VARCHAR(20),
kl_email VARCHAR(20)
);
create table Dostawcy (
dost_id INT,
dost_nazwa VARCHAR(50),
dost_miejscowosc VARCHAR(50),
dost_ulica VARCHAR(50),
dost_kraj VARCHAR(20),
dost_email VARCHAR(20)
);
create table Produkty (
prod_id INT,
dost_id INT,
prod_nazwa VARCHAR(50),
prod_cena VARCHAR(50),
prod_waluta VARCHAR(20),
prod_kraj VARCHAR(20)
);
create table Zamowienia (
zam_numer INT,
zam_data DATE,
kl_id INT
);
create table Elementyzamowienia (
zam_id INT,
zam_numer INT,
zam_elem INT,
prod_id INT,
ilosc INT,
cena_elem DECIMAL(5,2),
waluta VARCHAR(20)
);

###### Opis bazy danych

## Wykorzystane technologie 
- Język Java 17
  - Spring Boot
  - JavaFX
  - JDBC
  - SceneBuilder
- Baza danych MySQL
- Inne z opisem

## Pliki instalacyjne wraz z opisem instalacji i konfiguracji wraz pierwszego uruchomienia
