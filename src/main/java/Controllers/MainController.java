package Controllers;

import DatabaseAccess.DbAccess;
import DatabaseQueries.*;
import Entities.*;
import Singleton.SingletonConnection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Kontroler głównego okna aplikacji.
 */
public class MainController implements Initializable {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Elementy FXML
    //Tab Dostawcy
    //Pola
    @FXML
    private TextArea dostawcyInformacje;
    @FXML
    private TextField dostawcyPoleEmail, dostawcyPoleKraj, dostawcyPoleMiejscowosc, dostawcyPoleNazwa, dostawcyPoleNip, dostawcyPoleUlica;
    //Tabela
    @FXML
    private TableView<Dostawca> dostawcyTabela;
    @FXML
    private TableColumn<Dostawca, String> dostawcyKolEmail, dostawcyKolId, dostawcyKolKraj, dostawcyKolMiejscowosc, dostawcyKolNazwa, dostawcyKolUlica;
    @FXML
    private TableColumn<Dostawca, Void> dostawcyKolUsun;
    //Filtry
    @FXML
    private TextField dostawcySzukajEmail, dostawcySzukajId, dostawcySzukajKraj, dostawcySzukajMiejscowosc, dostawcySzukajNazwa, dostawcySzukajUlica;
    //Tab Elementy zamowienia
    //Pola
    @FXML
    private TextArea elemZamInformacje;
    @FXML
    private TextField elemZamPoleIdProduktu, elemZamPoleIdZamowienia, elemZamPoleIlosc;
    //Tabela
    @FXML
    private TableView<ElementZamowienia> elemZamTabela;
    @FXML
    private TableColumn<ElementZamowienia, String> elemZamKolCenaEl, elemZamKolCenaJM, elemZamKolId, elemZamKolIdProduktu, elemZamKolIdZamowienia, elemZamKolIlosc;
    @FXML
    private TableColumn<ElementZamowienia, Void> elemZamKolUsun;
    //Filtry
    @FXML
    private TextField elemZamSzukajCenaEl, elemZamSzukajCenaJM, elemZamSzukajId, elemZamSzukajIdProduktu, elemZamSzukajIdZamowienia, elemZamSzukajIlosc;
    //Tab Klienci
    //Pola
    @FXML
    private TextArea klienciInformacje;
    @FXML
    private TextField klienciPoleEmail, klienciPoleImie, klienciPoleMiejscowosc, klienciPoleNazwisko, klienciPoleNrBud, klienciPoleNrMieszkania, klienciPoleNrTel, klienciPolePesel, klienciPoleUlica;
    //Tabela
    @FXML
    private TableView<Klient> klienciTabela;
    @FXML
    private TableColumn<Klient, String> klienciKolEmail, klienciKolId, klienciKolImie, klienciKolMiejscowosc, klienciKolNazwisko, klienciKolNrBud, klienciKolNrMieszk, klienciKolNrTel, klienciKolPesel, klienciKolUlica;
    @FXML
    private TableColumn<Klient, Void> klienciKolUsun;
    //Filtry
    @FXML
    private TextField klienciSzukajEmail, klienciSzukajId, klienciSzukajImie, klienciSzukajMiejscowosc, klienciSzukajNazwisko, klienciSzukajNrBud, klienciSzukajNrMieszk, klienciSzukajNrTel, klienciSzukajPesel, klienciSzukajUlica;
    //Tab Produkty
    //Pola
    @FXML
    private TextArea produktyInformacje;
    @FXML
    private TextField produktyPoleCena, produktyPoleIdDostawcy, produktyPoleIlosc, produktyPoleJM, produktyPoleKod, produktyPoleKolor, produktyPoleKraj, produktyPoleMaxIlosc, produktyPoleNazwa;
    //Tabela
    @FXML
    private TableView<Produkt> produktyTabela;
    @FXML
    private TableColumn<Produkt, String> produktyKolCena, produktyKolId, produktyKolIdDostawcy, produktyKolIlosc, produktyKolJM, produktyKolKod, produktyKolKolor, produktyKolKraj, produktyKolMaxIlosc, produktyKolStan;
    @FXML
    private TableColumn<Produkt, Boolean> produktyKolUwz;
    @FXML
    private TableColumn<Produkt, Void> produktyKolUsun;
    //Filtry
    @FXML
    private TextField produktySzukajCena, produktySzukajId, produktySzukajIdDostawcy, produktySzukajIlosc, produktySzukajJm, produktySzukajKod, produktySzukajKolor, produktySzukajKraj, produktySzukajMaxIlosc;
    //Tab Uzytkownicy
    //Pola
    @FXML
    private CheckBox uzytkownicyAdmin, uzytkownicyRabat, uzytkownicyRaport;
    @FXML
    private TextArea uzytkownicyInformacje;
    @FXML
    private TextField uzytkownicyPoleEmail, uzytkownicyPoleHaslo, uzytkownicyPoleImie, uzytkownicyPoleNazwisko, uzytkownicyPoleNrTel;
    //Tabela
    @FXML
    private TableView<Uzytkownik> uzytkownicyTabela;
    @FXML
    private TableColumn<Uzytkownik, String> uzytkownicyKolAdmin, uzytkownicyKolEmail, uzytkownicyKolHaslo, uzytkownicyKolId, uzytkownicyKolImie, uzytkownicyKolNazwisko, uzytkownicyKolNrTel, uzytkownicyKolGenRap, uzytkownicyKolUdzRab;
    @FXML
    private TableColumn<Uzytkownik, Void> uzytkownicyKolUsun;
    //Filtry
    @FXML
    private TextField uzytkownicySzukajAdmin, uzytkownicySzukajEmail, uzytkownicySzukajHaslo, uzytkownicySzukajId, uzytkownicySzukajImie, uzytkownicySzukajNazwisko, uzytkownicySzukajNrTel, uzytkownicySzukajGenRap, uzytkownicySzukajUdzRab;
    //Tab Zamowienia
    //Pola
    @FXML
    private TextField zamowieniaIdKlienta, zamowieniaPoleData, zamowieniaPoleRabat;
    @FXML
    private TextArea zamowieniaInformacje;
    //Tabela
    @FXML
    private TableView<Zamowienie> zamowieniaTabela;
    @FXML
    private TableColumn<Zamowienie, String> zamowieniaKolData, zamowieniaKolId, zamowieniaKolIdKlienta, zamowieniaKolRabat, zamowieniaKolStan;
    @FXML
    private TableColumn<Zamowienie, Void> zamowieniaKolFaktura, zamowieniaKolUsun;
    //Filtry
    @FXML
    private TextField zamowieniaSzukajData, zamowieniaSzukajId, zamowieniaSzukajIdKlienta, zamowieniaSzukajRabat, zamowieniaSzukajStan;
    //Zakładki
    @FXML
    private Tab tabDostawcy, tabElementyZamowienia, tabKlienci, tabProdukty, tabZamowienia, tabZarządzanie;
    @FXML
    private Button btnWyloguj, btnZakoncz;
    @FXML
    private Text labelTime, labelUzytkownik;

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
    private Scene scene;
    //Listy
    public static ObservableList<Dostawca> dostawcy = FXCollections.observableArrayList();
    public static ObservableList<Uzytkownik> uzytkownicy = FXCollections.observableArrayList();
    public static ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    public static ObservableList<Klient> klienci = FXCollections.observableArrayList();
    public static ObservableList<Zamowienie> zamowienia = FXCollections.observableArrayList();
    public static ObservableList<ElementZamowienia> elementyZamowienia = FXCollections.observableArrayList();

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

        uzytkownicyInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> uzytkownicyInformacje.setScrollTop(Double.MAX_VALUE));
        klienciInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> klienciInformacje.setScrollTop(Double.MAX_VALUE));
        dostawcyInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> dostawcyInformacje.setScrollTop(Double.MAX_VALUE));
        produktyInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> produktyInformacje.setScrollTop(Double.MAX_VALUE));
        zamowieniaInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> zamowieniaInformacje.setScrollTop(Double.MAX_VALUE));
        elemZamInformacje.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> elemZamInformacje.setScrollTop(Double.MAX_VALUE));

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
        new Thread(() -> runClock()).start(); //wątek zegara

        loginService.zaloguj("admin", "1234");
        labelUzytkownik.setText(loginService.getLogin());
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Odpowiedzialna za działanie zegara.
     */
    private void runClock() {
        DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date;

        while (true) {
            date = outputformat.format(Calendar.getInstance().getTime());
            labelTime.setText(date);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Testowy insert danych.
     */
    private void insertData() {
        //TODO: insert rzeczywistych danych

        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session;

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));
        session.save(new Uzytkownik("imie", "nazwisko", "email", "haslo", "123456789", 1, 1, 1));

        Uzytkownik u = (Uzytkownik) session.createSQLQuery("select * from uzytkownik where id='" + 1 + "'").addEntity(Uzytkownik.class).getSingleResult();

        session.save(new Dostawca("email", "chiny", "pekin", "ulica", "nazwa", "NIP"));
        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id='" + 1 + "'").addEntity(Dostawca.class).getSingleResult();

        session.save(new Klient("imie", "nazwisko", "pesel", "nrtel", "email", "miejscowosc", "ulica", 10, 12));
        Klient k = (Klient) session.createSQLQuery("select * from klient where id='" + 1 + "'").addEntity(Klient.class).getSingleResult();

        session.save(new Produkt(d, "nazwa", "sztuka", 22.56, "Polska", "AS2345", "brak", 80, 100));
        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id='" + 1 + "'").addEntity(Produkt.class).getSingleResult();

        session.save(new Zamowienie(k, "22-02-2022", "złożone", "-20%"));
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id='" + 1 + "'").addEntity(Zamowienie.class).getSingleResult();

        session.save(new ElementZamowienia(z, p, 10, p.getCena() * 10, p.getCena()));

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Ustawienie wypełniania poszczególnych kolumn w tabelach
     */
    private void setTables() {
        //Tabela Uzytkownicy
        uzytkownicyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        uzytkownicyKolImie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImie()));
        uzytkownicyKolNazwisko.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNazwisko()));
        uzytkownicyKolNrTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumerTelefonu()));
        uzytkownicyKolEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        uzytkownicyKolHaslo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHaslo()));
        uzytkownicyKolAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIsAdmin())));
        uzytkownicyKolGenRap.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGenerowanieRaportow())));
        uzytkownicyKolUdzRab.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getUdzielanieRabatow())));
        uzytkownicyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Uzytkownik, Void> call(final TableColumn<Uzytkownik, Void> param) {
                final TableCell<Uzytkownik, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Uzytkownik u = getTableView().getItems().get(getIndex());
                            if (uzytkownikMethods.deleteUzytkownik(u)) {
                                uzytkownicy.remove(u);
                                uzytkownicyInformacje.appendText("\nPomyślnie usunięto użytkownika o id " + u.getId());
                            } else
                                uzytkownicyInformacje.appendText("\nBłąd przy próbie usunięcia użytkownika o id " + u.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Dostawca d = getTableView().getItems().get(getIndex());
                            if (dostawcaMethods.deleteDostawca(d)) {
                                dostawcy.remove(d);
                                dostawcyInformacje.appendText("\nPomyślnie usunięto dostawcę o id " + d.getId());
                            } else
                                dostawcyInformacje.appendText("\nBłąd przy próbie usunięcia dostawcy o id " + d.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Klient k = getTableView().getItems().get(getIndex());
                            if (klientMethods.deleteKlient(k)) {
                                klienci.remove(k);
                                klienciInformacje.appendText("\nPomyślnie usunięto klienta o id " + k.getId());
                            } else
                                klienciInformacje.appendText("\nBłąd przy próbie usunięcia klienta o id " + k.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
        produktyKolStan.setCellValueFactory(cellData -> new SimpleStringProperty(obliczStan(cellData.getValue().getIlosc() / cellData.getValue().getMaxIlosc())));
        produktyKolUwz.setCellValueFactory(cellData -> new SimpleBooleanProperty(false));
        produktyKolUwz.setCellFactory(tc -> new CheckBoxTableCell<>());

        produktyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Produkt, Void> call(final TableColumn<Produkt, Void> param) {
                final TableCell<Produkt, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Produkt p = getTableView().getItems().get(getIndex());
                            if (produktMethods.deleteProdukt(p)) {
                                produkty.remove(p);
                                produktyInformacje.appendText("\nPomyślnie usunięto produktu o id " + p.getId());
                            } else
                                produktyInformacje.appendText("\nBłąd przy próbie usunięcia produktu o id " + p.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
        zamowieniaKolFaktura.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Zamowienie, Void> call(final TableColumn<Zamowienie, Void> param) {
                final TableCell<Zamowienie, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/faktura.png"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Zamowienie z = getTableView().getItems().get(getIndex());
                            try {
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setOpacity(1);
                                stage.setTitle("Generuj raport");
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
                                Parent root = fxmlLoader.load();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.showAndWait();
                            } catch (IOException e) {
                                uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania faktury");
                            }
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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

        zamowieniaKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Zamowienie, Void> call(final TableColumn<Zamowienie, Void> param) {
                final TableCell<Zamowienie, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Zamowienie z = getTableView().getItems().get(getIndex());
                            if (zamowienieMethods.deleteZamowienie(z)) {
                                zamowienia.remove(z);
                                zamowieniaInformacje.appendText("\nPomyślnie usunięto zamowienia o id " + z.getId());
                            } else
                                zamowieniaInformacje.appendText("\nBłąd przy próbie usunięcia zamowienia o id " + z.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ElementZamowienia ez = getTableView().getItems().get(getIndex());
                            if (elementZamowieniaMethods.deleteElementZamowienia(ez)) {
                                elementyZamowienia.remove(ez);
                                elemZamInformacje.appendText("\nPomyślnie usunięto element zamowienia o id " + ez.getId());
                            } else
                                zamowieniaInformacje.appendText("\nBłąd przy próbie usunięcia elementu zamowienia o id " + ez.getId());
                        });

                        btn.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        btn.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
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
    public String obliczStan(double stosunek) {
        //TODO: ustalić progi
        if (stosunek < 30) return "niski";
        else if (stosunek < 60) return "umiarkowany";
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
     * Obsługuje przycisk wyjścia z aplikacji.
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli użytkowników.
     *
     * @param event
     */
    @FXML
    public void uzytkownicyBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli klientów.
     *
     * @param event
     */
    @FXML
    public void klienciBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli dostawców.
     *
     * @param event
     */
    @FXML
    public void dostawcyBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli produktów.
     *
     * @param event
     */
    @FXML
    public void produktyBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
    }

    /**
     * Obsługuje przycisk generowania raportu dostaw.
     *
     * @param event
     */
    @FXML
    public void produktyBtnRaportDostawClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu dostaw
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli zamówień.
     *
     * @param event
     */
    @FXML
    public void zamowieniaBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
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

    /**
     * Obsługuje przycisk generowania raportu z aktualnego widoku tabeli elementów zamówienia.
     *
     * @param event
     */
    @FXML
    public void elemZamBtnRaportClicked(MouseEvent event) {
        //TODO: obsłużyć generowanie raportu
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            uzytkownicyInformacje.appendText("\nBłąd załadowania modułu generowania raportu");
        }
    }

}
