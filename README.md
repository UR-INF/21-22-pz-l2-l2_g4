Programowanie zespołowe laboratorium _**2**_ grupa _**4**_

# Dokumentacja projetu: **System do zarządzania zadaniami w hurtowni budowlanej**


## Zespoł projetowy:
<b>Konrad Pelc</b> - Lider, Project Manager oraz Scrum Master, organizacja pracy zespołu, rozdzielenie zadań, organizacja spotkań sprintowych, dokumentacja Sprint i Manual.  
<b>Tomasz Pitak</b> - Lider Techniczny. Odpowiedzialny za kod, logikę biznesową zarządzania uprawnieniami użytkowników, generowanie plików PDF, implementacja logiki CRUD w aplikacji.  
<b>Michał Moskal</b> - Developer oraz Tester. Panel logowania i rejestracji oraz implementacja logiki CRUD w aplikacji.  
<b>Sebastian Leń</b> - Developer oraz Tester. Diagramy UML oraz implementacja logiki CRUD w aplikacji.  
<b>Paweł Niziołek</b> - Developer oraz Tester. Stworzenie przykładowych widoków wraz oraz ich implementacja w SceneBuilder JavaFX.  
<b>Patryk Mokrzycki</b> - Developer oraz Bazy danych. Stworzenie bazy danych, diagramu ERD oraz wypełnienie bazy danych przykładowymi danymi, odpowiedzialność za skrypt do utworzenia struktury bazy danych.  


## Opis programu / systemu
Aplikacja desktopowa powinna umożliwiać zarządzanie zasobami bazy danych hurtowni materiałów budowlanych. 

W aplikacji wyróżnione będą dwie role: 
-	administrator – posiadający dostęp do wszystkich funkcji systemu,
-	pracownik – mający ograniczony dostęp do funkcji systemu w zależności od przydzielonych uprawnień.

System powinien w łatwy sposób umożliwiać pracownikom wykonywanie operacji dodawania, przeglądania, edytowania i usuwania informacji w lokalnej bazie danych oraz generowania niezbędnych raportów, w tym generowania faktury z zamówienia oraz generowanie raportu dostawy. Aplikacja powinna obsługiwać system udzielania rabatów.

Po uruchomieniu aplikacji pierwszym widokiem będzie panel logowania, gdzie można się zalogować korzystając z wbudowanego konta administratora lub korzystając z konta utworzonego przez administratora systemu.

Podstawowy login i hasło wbudowanego konta administratora to:   
Login: admin   
Hasło: admin 

## Cel projektu 
Celem projektu jest zaprojektowanie i stworzenie opisanego powyżej systemu. Zakłada się dostarczenie gotowego produktu wraz z instalatorem, podręcznikiem użytkownika i dokumentacją techniczną.


## Wymagania stawiane aplikacji / systemowi 
Wymagania funkcjonalne
1.	System powinien mieć kilka modułów
-	Moduł administracji użytkownikami 
-	Moduł generowania raportów
-	Moduł konfiguracji
-	Moduł zarządzania zasobami bazy danych
2.	System powinien umożliwiać generowanie raportów PDF
-	Faktura z zamówienia
-	Raport dostawy
-	Raport z przefiltrowanej tabeli (niski stan magazynowy, raporty statystyczne, itp.)
3.	System powinien współpracować z lokalną bazą danych
4.	System powinien umożliwiać udzielanie kodów rabatowych
5.	System powinien umożliwiać logowanie użytkowników

Wymagania niefunkcjonalne
1.	System ma być dostępny w systemie 24/7/365.
2.	Brak limitu ilości użytkowników korzystających jednocześnie z aplikacji.
3.	Czas odpowiedzi aplikacji powinien być akceptowalny.
4.	Aplikacja nie powinna obciążać zasobów sprzętowych urządzenia.
5.	Aplikację powinna cechować wspólna kolorystyka.

Podczas pierwszego uruchomienia aplikacji nastąpi próba połączenia się z domyślną bazą danych. 

## Panele / zakładki systemu, które będą oferowały potrzebne funkcjonalności 
1.	Panel administratora

Główne narzędzie administratorów systemu umożliwiające wykonanie wszystkich czynności potrzebnych do zarządzania systemem i zasobami bazy danych, w tym dodawanie, edycja, usuwanie użytkowników, nadawanie i usuwanie uprawnień.

2.	Panel pracownika

Główne narzędzie pracowników umożliwiające wykonanie wszystkich czynności potrzebnych do zarządzania zasobami bazy danych. Dostępne funkcje panelu zależne są od uprawnień użytkownika.

3.	Panel importu danych

Umożliwia import danych z plików CSV. Pliki powinny posiadać ściśle określoną strukturę.

4.	Panel opcji

Pozwala na dynamiczną zmianę parametrów połączenia z bazą danych.

5.	Panel logowania

Umożliwia zalogowanie do systemu.


## Przepływ informacji w środowisku systemu 
System implementuje wzorzec Controller-Service-Repository, w którym:
-	Controller – komponenty z adnotacjami @Component, warstwa odpowiedzialna za zarządzanie interfejsem użytkownika zaprojektowanym w technologii JavaFX,
-	Service – komponenty z adnotacjami @Service, warstwa implementująca logikę biznesową,
-	Repository – komponenty z adnotacjami @Repository, warstwa odpowiedzialna za pobieranie i utrwalanie danych.

Ponadto system implementuje wzorzec DTO (Data Transfer Object). Obiekty DTO służą do przesyłania danych poza część aplikacji odpowiedzialną za logikę biznesową.

<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/3507f6d7-c95c-406a-b0a2-2348836ff34f">
</p>


## Użytkownicy aplikacji i ich uprawnienia 
1.	Administrator

– zarządzanie systemem (zarządzanie użytkownikami i ich uprawnieniami) 

– zarządzanie wszystkimi zasobami bazy danych

– zmiana źródła danych

2.	Pracownik 

– zarządzanie wszystkimi zasobami bazy danych

– w zależności od uprawnień:

&emsp;•	generowanie raportów - pozwala na generowanie raportów z przefiltrowanych widoków tabel, raportu dostawy oraz faktury z zamówienia
   
&emsp;•	udzielanie rabatów - pozwala na udzielanie i modyfikację udzielonego rabatu


## Interesariusze 
Interesariusze wewnętrzni
1.	Członkowie zespołu projektowego
2.	Product Owner
Interesariusze zewnętrzni
1.	Użytkownicy systemu


## Diagramy UML
- ###### [Diagram przypadków użycia]
<p align="center">
 <img src="https://user-images.githubusercontent.com/83063779/226652408-3e2ed8d4-041b-43d6-ae31-5c3ae1746d81.png">
</p>

- ###### [Diagram aktywności]
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665165-1e471afa-35a5-459d-a7c0-542da360ea00.PNG">
</p>
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665261-11f0e924-d6dd-439d-be39-e8191de05691.PNG">
</p>

- ###### [Diagram sekwencji]
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665359-6b56d81a-dd43-45ed-b353-e897e7a16bf5.PNG">
</p>
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665419-4d739cf3-ef4d-4d9c-9b61-1a20dbe00d34.PNG">
</p>

- ###### [Diagram stanów]
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665593-7dbcd5af-0370-4242-a8e9-0c9b9acf9989.PNG">
</p>
<p align="center">
 <img src="https://user-images.githubusercontent.com/103111656/226665652-69c3ddc6-5c97-453b-9a84-fafd79d0c4ce.PNG">
</p>

- ###### [Diagram klas dla pakietu customer]
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/ef951505-6461-47c2-ac2b-6d076d4532c1">
</p>

## Baza danych

###### Diagram ERD
<p align="center">
  <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/82969695/b358e538-e6fe-454c-a2fd-dea069698efa">
</p>

###### Skrypt do utworzenia struktury bazy danych
Aplikacja utworzy domyślną bazę danych o nazwie ‘hurtownia’ podczas pierwszego uruchomienia.
Dane konfiguracyjne domyślnej bazy danych:

·	Nazwa hosta: localhost/127.0.0.1

·	Port: 3306

·	Nazwa bazy danych: hurtownia

·	Nazwa użytkownika: root

·	Hasło:


###### Opis bazy danych
Tabela ‘Uzytkownik’ zawiera informacje dotyczące kont użytkowników. Hasło przechowywane jest w postaci zaszyfrowanej. Pole ‘isAdmin’ pozwala określić typ użytkownika a pola ‘generowanieRaportów’ i ‘udzielanieRabatów’ przydzielone uprawnienia. 

Tabela ‘Klient’ zawiera informacje na temat klientów składających zamówienia.

Tabela ‘Dostawca’ zawiera informacje na temat dostawców towarów do hurtownii.

Tabela ‘Produkt’ zawiera informacje na temat produktów oferowanych przez hurtownię. Kolumny ‘ilosc’ oraz ‘maxIlosc’, które zawierają informacje o tym, jaki jest stan magazynowy produktu. Na podstawie tych danych określane będzie zapotrzebowanie magazynu na dostawę danego produktu. Na podstawie odpowiednich obliczeń w aplikacji zostanie wyświetlona informacja o stanie magazynowym danego produktu, tj. wysoki, umiarkowany lub niski.

Tabela ‘Zamowienie’ zawiera informacje na temat zamówień. Kolumna ‘stanZamowienia’ określa jeden z możliwych stanów: w przygotowaniu, gotowe, odebrane. Kolumna ‘rabat’ informuje o tym, czy został udzielony rabat i w jakiej wysokości.

Tabela ‘ElementZamowienia’ zawiera informacje na temat elementów zamówień. Kolumna ‘cenaZaJednostke’ określa cenę danego produktu w momencie złożenia zamówienia.


## Wykorzystane technologie 
1.	Java (17)
2.	JavaFX (17)
3.	Spring Boot (2.7.11)
4.	IText (7.2.3)
5.	Baza danych MySQL (8.0.32) 


## Pliki instalacyjne wraz z opisem instalacji i konfiguracji wraz pierwszego uruchomienia

1.	W celu zainstalowania aplikacji należy uruchomić instalator Hurtownia Setup.exe.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/97a88ae2-6cc8-4303-820c-7d76eccb0c3a">
</p>

2.	Po uruchomieniu pliku Hurtownia Setup.exe ukaże się okno z możliwością wyboru typu instalacji.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/c6dbe738-2a14-4486-b610-5a8b00524cd5">
</p>

3.	Kolejnym krokiem jest wybór języka podczas instalacji.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/d63defff-d174-4171-bce7-a1b8043aad97">
</p>

4.	Następnie należy zaakceptować umowę licencyjną.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/3e324bc4-2781-47da-a7b5-13c6c960e500">
</p>

5.	Kolejnym krokiem jest wybór lokalizacji instalacji.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/f259ac5e-c0ef-4300-a8cb-7eb3fa74679f">
</p>
 
6.	W następnym kroku należy wskazać lokalizację folderu Menu Start lub zaznaczyć opcję „Nie twórz folderu w Menu Start”.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/d12909d9-9840-47b2-ba12-a6e15a92f1e8">
</p>

7.	Następnie należy zdecydować, czy instalator ma utworzyć skrót na pulpicie.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/6f5755d5-b1f9-47e0-ac2c-de4ac9dc78fa">
</p>

8.	W ostatnim kroku instalator poinformuje o gotowości do instalacji. W celu instalacji należy kliknąć przycisk Instaluj. W celu dokonania zmian w konfiguracji należy kliknąć przycisk Wstecz.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/7619a110-cb6a-43d0-9730-cedffd9c8b64">
</p>

9.	Program zostanie zainstalowany.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/dd609ce5-2767-4560-ad01-059c24439eb0">
</p>

10.	Instalator poinformuje o zakończeniu instalacji oraz wyświetli opcję otwarcia aplikacji po jego zamknięciu. 
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/170d07f0-767e-492b-a4de-5481f9220510">
</p>

11.	Wygląd skrótu na pulpicie oraz w Menu Start.
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/cf33e715-f5a0-4bc1-9d67-b5da34c78cd8">
</p>
<p align="center">
 <img src="https://github.com/UR-INF/21-22-pz-l2-l2_g4/assets/101945722/bb7ce12a-12c7-447f-9309-cb2602b1e06a">
</p>
