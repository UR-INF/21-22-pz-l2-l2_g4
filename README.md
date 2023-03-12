Programowanie zespołowe laboratorium _**2**_ grupa _**4**_

# Dokumentacja projetu: **System do zarządzania zadaniami w hurtowni budowlanej**

## Zespoł projetowy:
<b>Konrad Pelc</b> - Lider, Project Manager oraz Scrum Master, organizacja pracy zespołu, rozdzielenie zadań, organizacja spotkań sprintowych, dokumentacja Sprint i Manual.  
<b>Tomasz Pitak</b> - Lider Techniczny. Odpowiedzialny za kod, logikę biznesową zarządzania uprawnieniami użytkowników, generowanie plików PDF, implementacja logiki CRUD w aplikacji.  
<b>Michał Moskal</b> - Developer oraz Tester. Panel logowania i rejestracji oraz implementacja logiki CRUD w aplikacji.  
<b>Sebastian Leń</b> - Developer oraz Tester. Diagramy UML oraz implementacja logiki CRUD w aplikacji.  
<b>Paweł Niziołek</b> - Developer oraz Tester. Stworzenie przykładowych widoków wraz oraz ich implementacja w SceneBuilder JavaFX.  
<b>Patryk Mokrzycki</b> - Developer oraz Bazy danych. Stworzenie bazy danych, diagramu ERD oraz wypełnienie bazy danych przykładowymi danymi, odpowiedzialność za skrypt do utworzenia struktury bazy danych.  
 
Wszyscy developerzy są zobowiązani do napisania testów jednostkowych JUnit dla zaimplementowanego przez siebie kodu aplikacji.

## Opis programu / systemu
Aplikacja desktopowa hurtowni budowlanej umożliwająca obsługę i zarządzanie sprzedażą materiałów budowlanych.  
Aplikacja zostanie napisana w języku Java 17 wraz z frameworkiem Spring Boot oraz Hibernate i z wykorzystaniem bazy danych MySQL.  
Wymiary aplikacji to 800.0h x 1550.0w.  
Przechowywanie danych wpisanych do aplikacji  powierzone jest bazie danych ściśle powiązanej z aplikacją graficznego interfejsu użytkownika (GUI).

W aplikacji rozróżniamy dwie role które w kodzie będą zapisane w klasie enum Role  
 - ADMIN
 - EMPLOYEE

Po uruchomieniu aplikacji początkowym panelem jest panel logowania w którym można się zalogować lub przejść do rejestracji nowego konta.  
Rola ADMIN
Uwierzytelniony(zalogowany) użytkownik z rolą ADMIN przechodzi następnie do panelu głównego aplikacji który zawiera z prawej strony listę dostępnych tabel, a z lewej strukturę bazy danych wraz z wypełnionymi danymi wierszami danej tabeli (np. tabeli produkty). Stosunek długości listy tabel do struktury obecnie wyświetlanej tabeli powienien wynosić 30:70. Powyżej panelu głównego znajdują się dwie zakładki - Panel główny oraz Zarządzanie uprawnieniami.  
W zakładce 'Zarządzanie uprawnieniami' Administrator ma możliwość nadania oraz odebrania uprawnień poszczególnym użytkownikom wybieranym z rozwijanej listy. Nadanie oraz odebranie uprawnienia polega na zmianie roli wybranego przez Administratora użytkownika oraz zaznaczeniu/odznaczeniu poszczególnych checkbox'ów np. Uprawnienie do generowania raportów.

Rola EMPLOYEE  
Uwierzytelniony(zalogowany) użytkownik z rolą EMPLOYEE przechodzi następnie do panelu głównego aplikacji który zawiera z prawej strony listę dostępnych tabel, a z lewej strukturę bazy danych wraz z wypełnionymi danymi wierszami danej tabeli (np. tabeli produkty). Stosunek długości listy tabel do struktury obecnie wyświetlanej tabeli powienien wynosić 30:70.  

Uprawnienie do generowania raportów
Poniżej struktury bazy danych znajduje się przycisk "Drukuj", po jego naciśnięciu i wpisaniu tytułu raportu zostanie wygenerowany plik PDF z aktualnego widoku danej tabeli (możliwość zastosowania konkretnych filtrów przed wygenerowaniem raportu).

## Cel projektu 
Umożliwienie obsługi i zarządzania sprzedażą materiałów budowlanych wraz z umożliwieniem generowania raportów.

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
- Panel główny 
  - Dostępny dla ról ADMIN oraz EMPLOYEE
  - Operacje CRUD dla tabel Produkty, Zamówienia, Elementy Zamówienia w aplikacji. 
  - Główne narzędzie administratorów systemu umożliwiające wykonanie wszystkich czynności potrzebnych do zarządzania systemem np. dodawanie, edycja, usuwanie użytkowników, tworzenie i modyfikacja grup, zarządzanie innymi administratorami. Operacje CRUD dla wszystkich tabel w aplikacji.
- Panel zarządzania użytkownikami
  - Tylko dla użytkowników z rolą ADMIN
  - Możliwość zmiany roli oraz uprawnień użytkownikowi wybranemu z listy

## Typy wymaganych dokumentów w projekcie oraz dostęp do nich 
- Raporty PDF 
  - Generowany z aktualnego widoku danej tabeli (możliwość zastosowania konkretnych filtrów przed wygenerowaniem raportu).

## Przepływ informacji w środowisku systemu 
Scentralizowany oparty na bazie danych MySQL. Wszystkie operacje CRUD w bazie danych będą możliwe do wykonania z poziomu GUI aplikacji. 

## Użytkownicy aplikacji i ich uprawnienia 
- Administrator (Administrator Systemu/Kierownik/Dyrektor)
  - Zarządzanie uprawnieniami użytkowników 
  - Możliwości CRUD w aplikacji - Odczytywanie zawartości głównego panelu oraz zmiany zawartości tabel (tworzenie, aktualizacja oraz usuwanie wierszy w tabelach)
	
- Pracownik 
  - Możliwości CRUD w aplikacji - Odczytywanie zawartości głównego panelu oraz zmiany zawartości tabel (tworzenie, aktualizacja oraz usuwanie wierszy w tabelach)

## Interesariusze 
- Interesariusze wewnętrzni 
  - Zarząd hurtowni
  - Administrator systemu
  - Pracownicy
- Interesariusze zewnętrzni 
  - Brak interesariuszy zewnętrznych

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
  - Hibernate
  - SceneBuilder
- Baza danych MySQL
- Inne z opisem

## Pliki instalacyjne wraz z opisem instalacji i konfiguracji wraz pierwszego uruchomienia
