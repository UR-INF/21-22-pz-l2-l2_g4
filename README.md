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

<b>Dla wszystkich ról:</b>  
Uwierzytelniony(zalogowany) użytkownik przechodzi następnie do panelu głównego aplikacji. Zawiera on z prawej strony listę dostępnych tabel wraz z polami służącymi do dodawania wiersza do tabeli. Lewa część panelu głównego zawiera strukturę bazy danych wraz z wypełnionymi danymi wierszami danej tabeli (np. tabeli produkty). Nad wyświetlaną tabelą znajdować się będą przyciski oraz pola do wyszukiwania, aktualizacji oraz usuwania danych.  

Stosunek długości listy tabel do struktury obecnie wyświetlanej tabeli powinien wynosić 30:70.  

<b>Dla roli ADMIN:</b>  
Powyżej panelu głównego znajdują się dwie zakładki - Panel główny oraz Zarządzanie uprawnieniami.  
W zakładce 'Zarządzanie uprawnieniami' Administrator ma możliwość nadania oraz odebrania uprawnień poszczególnym użytkownikom wybieranym z rozwijanej listy. Nadanie oraz odebranie uprawnienia polega na zmianie roli wybranego przez Administratora użytkownika oraz zaznaczeniu/odznaczeniu poszczególnych checkbox'ów np. Uprawnienie do generowania raportów, uprawnienie do nadawania rabatów.  

<b>Uprawnienia:</b>  
<b>Uprawnienie do generowania raportów</b>  
Poniżej struktury bazy danych znajduje się przycisk "Drukuj", po jego naciśnięciu i wpisaniu tytułu raportu zostanie wygenerowany plik PDF z aktualnego widoku danej tabeli (możliwość zastosowania konkretnych filtrów przed wygenerowaniem raportu).

<b>Uprawnienie do używania kodów rabatowych</b>  
Poniżej struktury bazy danych znajdują się dwa pola z etykietą "Kod rabatowy", należą do nich pola "ID zamówienia" - któremu chcemy dodać kod rabatowy oraz pole "Kod rabatowy" - do którego należy wpisać kod rabatowy. Należy uwzględnić wykorzystanie maksymalnie jednego kodu rabatowego dla każdego zamówienia. Udzielenie kodu rabatowego będzie możliwe tylko dla użytkowników z takim uprawnieniem.  

<b>Uprawnienie do zamawiania materiałów przez hurtownię</b>  
Realizacja zamówień do hurtowni (zakup materiałów przez hurtownię) możliwa jest dla użytkowników z rolą ADMIN. Wykonywana zostaje poprzez zaznaczenie (np. Checkboxów) odpowiednich produktów w tabeli Produkty, wpisanie ilości którą hurtownia chce zamówić oraz naciśnięcie przycisku generującego plik. W wygenerowanym pliku znajdą się wskazane przez pracownika produkty wraz z ilością pożądaną przez hurtownię.  

Ceny każdego produktu będą zapisywane w kolumnie:  
<b>Cena</b> – typu double w zaokrągleniu do dwóch miejsc po przecinku  
Wszystkie ceny pokazywane będą w walucie Polskich Złotych (PLN, zł).

Przewidujemy również możliwość nadawania kodów rabatowych przez użytkowników z rolą ADMIN lub z rolą EMPLOYEE mających upoważnienie do używania kodów rabatowych.  
Kod rabatowy używany będzie w tabeli zamówienia i będzie zmieniał cenę konkretnego zamówienia o przypisany do kodu rabatowego procent zniżki.  

Kwota złożonego do realizacji zamówienia (zapisanego w tabeli Zamówienia oraz Elementy zamówienia), nie może ulec zmianie poprzez zmianę bieżącej ceny produktu. Zmiana kwoty złożonego zamówienia jest możliwa tylko poprzez użycie kodów rabatowych.  

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

## Panele / zakładki systemu, które będą oferowały potrzebne funkcjonalności 
- Panel główny 
  - Dostępny dla ról ADMIN oraz EMPLOYEE
  - Operacje CRUD dla tabel Produkty, Zamówienia, Elementy Zamówienia w aplikacji. 
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
 ![obraz](https://user-images.githubusercontent.com/83063779/226652408-3e2ed8d4-041b-43d6-ae31-5c3ae1746d81.png)
- ###### [Diagram aktywności]
![1](https://user-images.githubusercontent.com/103111656/226665165-1e471afa-35a5-459d-a7c0-542da360ea00.PNG)
![2](https://user-images.githubusercontent.com/103111656/226665261-11f0e924-d6dd-439d-be39-e8191de05691.PNG)
- ###### [Diagram sekwencji]
![3](https://user-images.githubusercontent.com/103111656/226665359-6b56d81a-dd43-45ed-b353-e897e7a16bf5.PNG)
![4](https://user-images.githubusercontent.com/103111656/226665419-4d739cf3-ef4d-4d9c-9b61-1a20dbe00d34.PNG)
- ###### [Diagram stanów]
![5](https://user-images.githubusercontent.com/103111656/226665593-7dbcd5af-0370-4242-a8e9-0c9b9acf9989.PNG)
![6](https://user-images.githubusercontent.com/103111656/226665652-69c3ddc6-5c97-453b-9a84-fafd79d0c4ce.PNG)


## Baza danych


###### Diagram ERD
![image](https://user-images.githubusercontent.com/82969695/227926709-24010d34-6872-447c-8eb9-155136576186.png)



###### Skrypt do utworzenia struktury bazy danych


###### Opis bazy danych
Każdy wiersz w tabeli ma swój indywidulany numer identyfikacyjny (ID) oraz nadany przez producenta towaru numer seryjny. Dzięki wykorzystaniu numeru seryjnego unikniemy problemu z rozróżnieniem np. Czerwonej i zielonej cegły lub odmiany paneli podłogowych. W tabelach nie będą znajdowały się zdjęcia, jedyną możliwością rozróżnienia podobnych produktów będzie weryfikacja numeru seryjnego.  

Tabela 'Produkt' zawiera kolumny 'Ilosc' oraz 'MaxIlosc', ktore zawierają informacje o tym, jak dużo danego produktu jest na stanie hurtowni oraz jak dużo tego produktu hurtownia jest w stanie pomieścić. Na postawie tych danych, określane będzie zapotrzebowanie magazynu na dostawę danego produktu. Informacja w interfejsie graficznym będzie zawierała jeden ze stanów 'Wysoki', 'Umiarkowany', 'Niski' albo 'Brak' w zależności od procentowego wypełnienia miejsca przeznaczonego na dany produkt.  

Tabela 'Zamówienia' zawiera kolumnę 'StanZamówienia' która określa jednym ze statusów 'W przygotowaniu', 'Przygotowane', 'Wysłane' albo 'Zakończone' etap realizacji zamówienia.  

Tabela 'dostawca' zawiera informacje odnośnie dostawców, którzy dostarczają towary do hurtowni.

Tabela 'uzytkownik' zawiera informacje dotyczące kont użytkowników. Dodatkowo parametr w kolumnie 'isAdmin' określa dostęp do funkcji, jakie ma dany użytkownik.

Tabela 'element_zamowienia' zawiera pola 'cena' oraz 'cena za jednostke'. Wartosci te po złożonym zamówieniu nie będą się aktualizowały z aktualną ceną produktu. Jest to funkcjonalność, która pozwoli nam sprawdzić ceny w danym zamówieniu przed podwyżką/zmianą cen (nie będą one aktualizowane).

## Wykorzystane technologie 
- Język Java 17
  - Spring Boot
  - JavaFX
  - Hibernate
  - SceneBuilder
- Baza danych MySQL
- Inne z opisem

## Pliki instalacyjne wraz z opisem instalacji i konfiguracji wraz pierwszego uruchomienia
