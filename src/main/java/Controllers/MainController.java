package Controllers;

import DatabaseAccess.DbAccess;
import DatabaseQueries.*;
import Entities.*;
import Singleton.SingletonConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler głównego okna aplikacji.
 */
public class MainController implements Initializable {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Elementy FXML
        //Tab Dostawcy
            //Przyciski
            @FXML private Button dostawcyBtnDodaj; @FXML private Button dostawcyBtnOdswiez; @FXML private Button dostawcyBtnPokaz; @FXML private Button dostawcyBtnSzukaj; @FXML private Button dostawcyBtnUsun;

            //Pola
            @FXML private TextField dostawcyInformacje; @FXML private TextField dostawcyPoleEmail; @FXML private TextField dostawcyPoleKraj; @FXML private TextField dostawcyPoleMiejscowosc; @FXML private TextField dostawcyPoleNazwa; @FXML private TextField dostawcyPoleNip; @FXML private TextField dostawcyPoleUlica;

            //Tabela
            @FXML private TableView<Dostawca> dostawcyTabela; @FXML private TableColumn<Dostawca, String> dostawcyKolEmail; @FXML private TableColumn<Dostawca, String> dostawcyKolId; @FXML private TableColumn<Dostawca, String> dostawcyKolKraj; @FXML  private TableColumn<Dostawca, String> dostawcyKolMiejscowosc; @FXML private TableColumn<Dostawca, String> dostawcyKolNazwa; @FXML private TableColumn<Dostawca, String> dostawcyKolUlica; @FXML private TableColumn<Dostawca, Void> dostawcyKolUsun;

            //Filtry
            @FXML private TextField dostawcySzukajEmail; @FXML private TextField dostawcySzukajId; @FXML private TextField dostawcySzukajKraj; @FXML private TextField dostawcySzukajMiejscowosc; @FXML private TextField dostawcySzukajNazwa; @FXML private TextField dostawcySzukajUlica;

        //Tab Elementy zamowienia
            //Przyciski
            @FXML private Button elemZamBtnDodaj; @FXML private Button elemZamBtnOdswiez; @FXML private Button elemZamBtnPokaz; @FXML private Button elemZamBtnSzukaj; @FXML private Button elemZamBtnUsun;

            //Pola
            @FXML private TextField elemZamInformacje; @FXML private TextField elemZamPoleIdProduktu; @FXML private TextField elemZamPoleIdZamowienia; @FXML private TextField elemZamPoleIlosc;

            //Tabela
            @FXML private TableView<ElementZamowienia> elemZamTabela; @FXML private TableColumn<ElementZamowienia, String> elemZamKolCenaEl; @FXML private TableColumn<ElementZamowienia, String> elemZamKolCenaJM; @FXML private TableColumn<ElementZamowienia, String> elemZamKolId; @FXML private TableColumn<ElementZamowienia, String> elemZamKolIdProduktu; @FXML private TableColumn<ElementZamowienia, String> elemZamKolIdZamowienia; @FXML private TableColumn<ElementZamowienia, String> elemZamKolIlosc; @FXML private TableColumn<ElementZamowienia, Void> elemZamKolUsun;

            //Filtry
            @FXML private TextField elemZamSzukajCenaEl; @FXML private TextField elemZamSzukajCenaJM; @FXML private TextField elemZamSzukajId; @FXML private TextField elemZamSzukajIdProduktu; @FXML private TextField elemZamSzukajIdZamowienia; @FXML private TextField elemZamSzukajIlosc;

        //Tab Klienci
            //Przyciski
            @FXML private Button klienciBtnDodaj; @FXML private Button klienciBtnOdswiez; @FXML private Button klienciBtnPokaz; @FXML private Button klienciBtnSzukaj; @FXML private Button klienciBtnUsun;

            //Pola
            @FXML private TextField klienciInformacje; @FXML private TextField klienciPoleEmail; @FXML private TextField klienciPoleImie; @FXML private TextField klienciPoleMiejscowosc; @FXML private TextField klienciPoleNazwisko; @FXML private TextField klienciPoleNrBud; @FXML private TextField klienciPoleNrMieszkania; @FXML private TextField klienciPoleNrTel; @FXML private TextField klienciPolePesel; @FXML private TextField klienciPoleUlica;

            //Tabela
            @FXML private TableView<Klient> klienciTabela; @FXML private TableColumn<Klient, String> klienciKolEmail; @FXML private TableColumn<Klient, String> klienciKolId; @FXML private TableColumn<Klient, String> klienciKolImie; @FXML private TableColumn<Klient, String> klienciKolMiejscowosc; @FXML private TableColumn<Klient, String> klienciKolNazwisko; @FXML private TableColumn<Klient, String> klienciKolNrBud; @FXML private TableColumn<Klient, String> klienciKolNrMieszk; @FXML private TableColumn<Klient, String> klienciKolNrTel; @FXML private TableColumn<Klient, String> klienciKolPesel; @FXML private TableColumn<Klient, String> klienciKolUlica; @FXML private TableColumn<Klient, Void> klienciKolUsun;

            //Filtry
            @FXML private TextField klienciSzukajEmail; @FXML private TextField klienciSzukajId; @FXML private TextField klienciSzukajImie; @FXML private TextField klienciSzukajMiejscowosc; @FXML private TextField klienciSzukajNazwisko; @FXML private TextField klienciSzukajNrBud; @FXML private TextField klienciSzukajNrMieszk; @FXML private TextField klienciSzukajNrTel; @FXML private TextField klienciSzukajPesel; @FXML private TextField klienciSzukajUlica;

        //Tab Produkty
            //Przyciski
            @FXML private Button produktyBtnDodaj; @FXML private Button produktyBtnOdswiez; @FXML private Button produktyBtnPokaz; @FXML private Button produktyBtnSzukaj; @FXML private Button produktyBtnUsun;

            //Pola
            @FXML private TextField produktyInformacje; @FXML private TextField produktyPoleCena; @FXML private TextField produktyPoleIdDostawcy; @FXML private TextField produktyPoleIlosc; @FXML private TextField produktyPoleJM; @FXML private TextField produktyPoleKod; @FXML private TextField produktyPoleKolor; @FXML private TextField produktyPoleKraj; @FXML private TextField produktyPoleMaxIlosc; @FXML private TextField produktyPoleNazwa;

            //Tabela
            @FXML private TableView<Produkt> produktyTabela; @FXML private TableColumn<Produkt, String> produktyKolCena; @FXML private TableColumn<Produkt, String> produktyKolId; @FXML private TableColumn<Produkt, String> produktyKolIdDostawcy; @FXML private TableColumn<Produkt, String> produktyKolIlosc; @FXML private TableColumn<Produkt, String> produktyKolJM; @FXML private TableColumn<Produkt, String> produktyKolKod; @FXML private TableColumn<Produkt, String> produktyKolKolor; @FXML private TableColumn<Produkt, String> produktyKolKraj; @FXML private TableColumn<Produkt, String> produktyKolMaxIlosc; @FXML private TableColumn<Produkt, String> produktyKolStan; @FXML private TableColumn<Produkt, Void> produktyKolUsun;

            //Filtry
            @FXML private TextField produktySzukajCena; @FXML private TextField produktySzukajId; @FXML private TextField produktySzukajIdDostawcy; @FXML private TextField produktySzukajIlosc; @FXML private TextField produktySzukajJm; @FXML private TextField produktySzukajKod; @FXML private TextField produktySzukajKolor; @FXML private TextField produktySzukajKraj; @FXML private TextField produktySzukajMaxIlosc;

        //Tab Uzytkownicy
            //Przyciski
            @FXML private Button uzytkownicyBtnDodaj; @FXML private Button uzytkownicyBtnOdswiez; @FXML private Button uzytkownicyBtnPokaz; @FXML private Button uzytkownicyBtnSzukaj; @FXML private Button uzytkownicyBtnUsun;

            //Pola
            @FXML private CheckBox uzytkownicyAdmin; @FXML private TextField uzytkownicyInformacje; @FXML private TextField uzytkownicyPoleEmail; @FXML private TextField uzytkownicyPoleHaslo; @FXML private TextField uzytkownicyPoleImie; @FXML private TextField uzytkownicyPoleNazwisko; @FXML private TextField uzytkownicyPoleNrTel; @FXML private CheckBox uzytkownicyRabat;

            //Tabela
            @FXML private TableView<Uzytkownik> uzytkownicyTabela; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolAdmin; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolEmail; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolHaslo; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolId; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolImie; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolNazwisko; @FXML private TableColumn<Uzytkownik, String> uzytkownicyKolNrTel; @FXML private TableColumn<Uzytkownik, Void> uzytkownicyKolUsun;

            //Filtry
            @FXML private TextField uzytkownicySzukajAdmin; @FXML private TextField uzytkownicySzukajEmail; @FXML private TextField uzytkownicySzukajHaslo; @FXML private TextField uzytkownicySzukajId; @FXML private TextField uzytkownicySzukajImie; @FXML private TextField uzytkownicySzukajNazwisko; @FXML private TextField uzytkownicySzukajNrTel;

        //Tab Zamowienia
            //Przyciski
            @FXML private Button zamowieniaBtnDodaj; @FXML private Button zamowieniaBtnOdswiez; @FXML private Button zamowieniaBtnPokaz; @FXML private Button zamowieniaBtnSzukaj; @FXML private Button zamowieniaBtnUsun;

            //Pola
            @FXML private TextField zamowieniaIdKlienta; @FXML private TextField zamowieniaInformacje; @FXML private TextField zamowieniaPoleData; @FXML private TextField zamowieniaPoleRabat;

            //Tabela
            @FXML private TableView<Zamowienie> zamowieniaTabela; @FXML private TableColumn<Zamowienie, String> zamowieniaKolData; @FXML private TableColumn<Zamowienie, String> zamowieniaKolId; @FXML private TableColumn<Zamowienie, String> zamowieniaKolIdKlienta; @FXML private TableColumn<Zamowienie, String> zamowieniaKolRabat; @FXML private TableColumn<Zamowienie, String> zamowieniaKolStan; @FXML private TableColumn<Zamowienie, Void> zamowieniaKolUsun;

            //Filtry
            @FXML private TextField zamowieniaSzukajData; @FXML private TextField zamowieniaSzukajId; @FXML private TextField zamowieniaSzukajIdKlienta; @FXML private TextField zamowieniaSzukajRabat; @FXML private TextField zamowieniaSzukajStan;

        //Zakładki
        @FXML private Tab tabDostawcy; @FXML private Tab tabElementyZamowienia; @FXML private Tab tabKlienci; @FXML private Tab tabProdukty; @FXML private Tab tabZamowienia; @FXML private Tab tabZarządzanie;

        @FXML private Button btnWyloguj; @FXML private Button btnZakoncz;

        @FXML private Text labelTime; @FXML private Text labelUzytkownik;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Obiekty
    private DbAccess dbAccess;
    private Login loginService;
    private DostawcaMethods dostawcaMethods;
    private KlientMethods klientMethods;
    private ZamowienieMethods zamowienieMethods;
    private ElementZamowieniaMethods elementZamowieniaMethods;
    private ProduktMethods produktMethods;
    private UzytkownikMethods uzytkownikMethods;

    //Listy
    public static ObservableList<Dostawca> dostawcy = FXCollections.observableArrayList();
    public static ObservableList<Uzytkownik> uzytkownicy = FXCollections.observableArrayList();
    public static ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    public static ObservableList<Klient> klienci = FXCollections.observableArrayList();
    public static ObservableList<Zamowienie> zamowienia = FXCollections.observableArrayList();
    public static ObservableList<ElementZamowienia> elementyZamowienia = FXCollections.observableArrayList();

    private Scene scene;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: Panel logowania
        //paneLogowanie.toFront();

        uzytkownicyTabela.setPlaceholder(new Label("Brak danych w tabeli"));
        klienciTabela.setPlaceholder(new Label("Brak danych w tabeli"));
        dostawcyTabela.setPlaceholder(new Label("Brak danych w tabeli"));
        produktyTabela.setPlaceholder(new Label("Brak danych w tabeli"));
        zamowieniaTabela.setPlaceholder(new Label("Brak danych w tabeli"));
        elemZamTabela.setPlaceholder(new Label("Brak danych w tabeli"));

        dbAccess = new DbAccess();
        dbAccess.getDatabaseName();

        loginService = new Login();
        dostawcaMethods = new DostawcaMethods();
        klientMethods = new KlientMethods();
        zamowienieMethods = new ZamowienieMethods();
        elementZamowieniaMethods = new ElementZamowieniaMethods();
        produktMethods = new ProduktMethods();
        uzytkownikMethods = new UzytkownikMethods();

        setTables();
        insertData();
    }

    public void setScene(Scene scene) { this.scene = scene; }

    /**
    * Testowy insert danych
    */
    public void insertData(){
        //TODO: insert rzeczywistych danych

        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session;

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        session.save(new Uzytkownik("imie","nazwisko","123456789","email","haslo",1));
        Uzytkownik u = (Uzytkownik) session.createSQLQuery("select * from uzytkownik where id=\'" + 1 + "\'").addEntity(Uzytkownik.class).getSingleResult();

        session.save(new Dostawca("email","chiny","pekin","ulica","nazwa","NIP"));
        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id=\'" + 1 + "\'").addEntity(Dostawca.class).getSingleResult();

        session.save(new Klient("imie","nazwisko","pesel","nrtel","email","miejscowosc","ulica",10,12));
        Klient k = (Klient) session.createSQLQuery("select * from klient where id=\'" + 1 + "\'").addEntity(Klient.class).getSingleResult();

        session.save(new Produkt(d,"nazwa","sztuka",22.56,"Polska","AS2345","brak",80,100));
        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + 1 + "\'").addEntity(Produkt.class).getSingleResult();

        session.save(new Zamowienie(k,"22-02-2022", "złożone","-20%"));
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + 1 + "\'").addEntity(Zamowienie.class).getSingleResult();

        session.save(new ElementZamowienia(z,p,10, p.getCena()*10, p.getCena()));

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Ustawienie wypełniania poszczególnych kolumn w tabelach
     */
    public void setTables(){
        //Tabela Uzytkownicy
        uzytkownicyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        uzytkownicyKolImie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImie()));
        uzytkownicyKolNazwisko.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNazwisko()));
        uzytkownicyKolNrTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumerTelefonu()));
        uzytkownicyKolEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        uzytkownicyKolHaslo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHaslo()));
        uzytkownicyKolAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIsAdmin())));
        uzytkownicyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Uzytkownik, Void> call(final TableColumn<Uzytkownik, Void> param) {
                final TableCell<Uzytkownik, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Uzytkownik u = getTableView().getItems().get(getIndex());
                            if(uzytkownikMethods.deleteUzytkownik(u)) uzytkownicy.remove(u);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        uzytkownicyTabela.setItems(uzytkownicy);

        //Tabela Dostawcy
        dostawcyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        dostawcyKolNazwa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNazwa()));
        dostawcyKolEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        dostawcyKolMiejscowosc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMiejscowosc()));
        dostawcyKolUlica.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUlica()));
        dostawcyKolKraj.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKraj()));
        dostawcyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Dostawca, Void> call(final TableColumn<Dostawca, Void> param) {
                final TableCell<Dostawca, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Dostawca d = getTableView().getItems().get(getIndex());
                            if(dostawcaMethods.deleteDostawca(d)) dostawcy.remove(d);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        dostawcyTabela.setItems(dostawcy);

        //Tabela Klienci
        klienciKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        klienciKolImie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImie()));
        klienciKolNazwisko.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNazwisko()));
        klienciKolMiejscowosc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMiejscowosc()));
        klienciKolUlica.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUlica()));
        klienciKolNrBud.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumerBudynku())));
        klienciKolNrMieszk.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumerMieszkania())));
        klienciKolEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        klienciKolNrTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumerTelefonu()));
        klienciKolPesel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPesel()));
        klienciKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Klient, Void> call(final TableColumn<Klient, Void> param) {
                final TableCell<Klient, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Klient k = getTableView().getItems().get(getIndex());
                            if(klientMethods.deleteKlient(k)) klienci.remove(k);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        klienciTabela.setItems(klienci);

        //Tabela Produkty
        produktyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        produktyKolIdDostawcy.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDostawca().getId())));
        produktyKolKod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKod()));
        produktyKolCena.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCena())));
        produktyKolIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));
        produktyKolJM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJednostkaMiary()));
        produktyKolKraj.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKraj()));
        produktyKolKolor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKolor()));
        produktyKolMaxIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMaxIlosc())));
        produktyKolStan.setCellValueFactory(cellData ->  new SimpleStringProperty(obliczStan(cellData.getValue().getIlosc()/cellData.getValue().getMaxIlosc())));

        produktyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Produkt, Void> call(final TableColumn<Produkt, Void> param) {
                final TableCell<Produkt, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Produkt p = getTableView().getItems().get(getIndex());
                            if(produktMethods.deleteProdukt(p)) produkty.remove(p);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        produktyTabela.setItems(produkty);

        //Tabela Zamówień
        zamowieniaKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        zamowieniaKolIdKlienta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getKlient().getId())));
        zamowieniaKolData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData()));
        zamowieniaKolRabat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRabat()));
        zamowieniaKolStan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStanZamowienia()));
        zamowieniaKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Zamowienie, Void> call(final TableColumn<Zamowienie, Void> param) {
                final TableCell<Zamowienie, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Zamowienie z = getTableView().getItems().get(getIndex());
                            if(zamowienieMethods.deleteZamowienie(z)) zamowienia.remove(z);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        zamowieniaTabela.setItems(zamowienia);

        //Tabela Elemety zamówienia
        elemZamKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        elemZamKolIdZamowienia.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getZamowienie().getId())));
        elemZamKolIdProduktu.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProdukt().getId())));
        elemZamKolCenaEl.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaElementu())));
        elemZamKolCenaJM.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaZaJednostke())));
        elemZamKolIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));
        elemZamKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ElementZamowienia, Void> call(final TableColumn<ElementZamowienia, Void> param) {
                final TableCell<ElementZamowienia, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ElementZamowienia ez = getTableView().getItems().get(getIndex());
                            if(elementZamowieniaMethods.deleteElementZamowienia(ez)) elementyZamowienia.remove(ez);
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            btn.setStyle("-fx-background-color: #ffffff; ");
                            btn.setGraphic(new ImageView(image));
                            setGraphic(btn);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        elemZamTabela.setItems(elementyZamowienia);
    }

    /**
     * Oblicza stan produktu.
     *
     * @param stosunek stosunek ilości produktu w magazynie i całkowitej pojemności magazynu na dany produkt
     * @return stan magazynowy produktu
     */
    public String obliczStan(double stosunek){
        //TODO: ustalić progi
        if(stosunek<30) return "niski";
        else if(stosunek<60) return "umiarkowany";
        else return "wysoki";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //Obsługa przycisków

    /**
     * Obsługuje przycisk logowania.
     *
     * @param event
     */
    @FXML
    public void btnZaloguj(MouseEvent event) {
        //TODO: obsłużyć logowanie
        /*if(loginService.zaloguj(textFieldLogin.getText(), textFieldPassword.getText())) {
            if(loginService.isAdmin()) tabZarzadzanie.setDisable(false);
            else tabZarzadzanie.setDisable(true);
            paneLogowanie.toBack();*/
    }

    /**
     * Obsluguje przycisk wylogowania.
     *
     * @param event
     */
    @FXML
    public void btnWylogujClicked(MouseEvent event) {
        //TODO: obsłużyć wylogowanie
        /*loginService.wyloguj();
        textFieldLogin.clear();
        textFieldPassword.clear();
        paneLogowanie.toFront();*/
    }

    /**
     * Obsługuje przycisk wyjścia z aplikacji
     *
     * @param event
     */
    @FXML
    public void btnZakonczClicked(MouseEvent event) {
        //TODO: obsłużyć wyjście z aplikacji (logout and close)
    }


    //Tab uzytkownicy

    /**
     * Obsługuje przycisk wyświetlenia wszystkich użytkowników.
     *
     * @param event
     */
    @FXML
    public void uzytkownicyBtnPokazClicked(MouseEvent event) {
        uzytkownicyTabela.getItems().clear();
        uzytkownicy.setAll(uzytkownikMethods.getUzytkownicy());
    }

    /**
     * Obsługuje przycisk dodawania nowego użytkownika.
     *
     * @param event
     */
    @FXML
    public void uzytkownicyBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania użytkowników z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void uzytkownicyBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    //Tab klienci

    /**
     * Obsługuje przycisk wyświetlenia wszystkich klientów.
     *
     * @param event
     */
    @FXML
    public void klienciBtnPokazClicked(MouseEvent event) {
        klienciTabela.getItems().clear();
        klienci.setAll(klientMethods.getKlienci());
    }

    /**
     * Obsługuje przycisk dodawania nowego klienta.
     *
     * @param event
     */
    @FXML
    public void klienciBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania klientów z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void klienciBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    //Tab dostawcy

    /**
     * Obsługuje przycisk wyświetlenia wszystkich dostawców.
     *
     * @param event
     */
    @FXML
    public void dostawcyBtnPokazClicked(MouseEvent event) {
        dostawcyTabela.getItems().clear();
        dostawcy.setAll(dostawcaMethods.getDostawcy());
    }

    /**
     * Obsługuje przycisk dodawania nowego dostawcy.
     *
     * @param event
     */
    @FXML
    public void dostawcyBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania dostawców z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void dostawcyBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    //Tab produkty

    /**
     * Obsługuje przycisk wyświetlenia wszystkich produktów.
     *
     * @param event
     */
    @FXML
    public void produktyBtnPokazClicked(MouseEvent event) {
        produktyTabela.getItems().clear();
        produkty.setAll(produktMethods.getProdukty());
    }

    /**
     * Obsługuje przycisk dodawania nowego produktu.
     *
     * @param event
     */
    @FXML
    public void produktyBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania produktów z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void produktyBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    //Tab zamowienia

    /**
     * Obsługuje przycisk wyświetlenia wszystkich zamówień.
     *
     * @param event
     */
    @FXML
    public void zamowieniaBtnPokazClicked(MouseEvent event) {
        zamowieniaTabela.getItems().clear();
        zamowienia.setAll(zamowienieMethods.getZamowienia());
    }

    /**
     * Obsługuje przycisk dodawania nowego zamówienia.
     *
     * @param event
     */
    @FXML
    public void zamowieniaBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania zamówień z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void zamowieniaBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

    //Tab elementy zamowienia

    /**
     * Obsługuje przycisk wyświetlenia wszystkich elementów zamówień.
     *
     * @param event
     */
    @FXML
    public void elemZamBtnPokazClicked(MouseEvent event) {
        elemZamTabela.getItems().clear();
        elementyZamowienia.setAll(elementZamowieniaMethods.getElementyZamowienia());
    }

    /**
     * Obsługuje przycisk dodawania nowego elementu zamówienia.
     *
     * @param event
     */
    @FXML
    public void elemZamBtnDodajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk dodawania
    }

    /**
     * Obsługuje przycisk wyszukiwania elementów zamówień z uwzględnieniem filtrów.
     *
     * @param event
     */
    @FXML
    public void elemZamBtnSzukajClicked(MouseEvent event) {
        //TODO: obsłużyć przycisk wyszukiwania
    }

}
