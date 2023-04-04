package Controllers;

import domain.Customer.KlienciRaport;
import domain.Customer.Klient;
import domain.Customer.KlientService;
import DatabaseAccess.DbAccess;
import DatabaseQueries.*;
import domain.Order.ZamowieniaRaport;
import domain.Order.Zamowienie;
import domain.Order.ZamowienieService;
import domain.OrderItem.OrderItem;
import domain.OrderItem.ElementZamowieniaService;
import domain.OrderItem.ElementyZamowieniaRaport;
import PDFGeneration.*;
import domain.Product.Produkt;
import domain.Product.ProduktService;
import domain.Product.ProduktyRaport;
import Singleton.SingletonConnection;
import domain.Supplier.Dostawca;
import domain.Supplier.DostawcaService;
import domain.Supplier.DostawcyRaport;
import domain.User.Uzytkownik;
import domain.User.UzytkownikService;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Kontroler głównego okna aplikacji.
 */
public class MainController implements Initializable {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Elementy FXML
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
    private TableColumn<Dostawca, String> dostawcyKolEmail, dostawcyKolNip, dostawcyKolId, dostawcyKolKraj, dostawcyKolMiejscowosc, dostawcyKolNazwa, dostawcyKolUlica;
    @FXML
    private TableColumn<Dostawca, Void> dostawcyKolUsun;
    //Filtry
    @FXML
    private TextField dostawcySzukajEmail, dostawcySzukajNip, dostawcySzukajId, dostawcySzukajKraj, dostawcySzukajMiejscowosc, dostawcySzukajNazwa, dostawcySzukajUlica;
    //Tab Elementy zamowienia
    //Pola
    @FXML
    private TextArea elemZamInformacje;
    @FXML
    private TextField elemZamPoleIdProduktu, elemZamPoleIdZamowienia, elemZamPoleIlosc;
    //Tabela
    @FXML
    private TableView<OrderItem> elemZamTabela;
    @FXML
    private TableColumn<OrderItem, String> elemZamKolCenaEl, elemZamKolCenaJM, elemZamKolId, elemZamKolIdProduktu, elemZamKolIdZamowienia, elemZamKolIlosc;
    @FXML
    private TableColumn<OrderItem, Void> elemZamKolUsun;
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
    private TextField produktySzukajCena, produktySzukajId, produktySzukajIdDostawcy, produktySzukajIlosc, produktySzukajJm, produktySzukajKod, produktySzukajKolor, produktySzukajKraj, produktySzukajMaxIlosc, produktySzukajStan;
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
    private TableColumn<Zamowienie, String> zamowieniaKolData, zamowieniaKolId, zamowieniaKolIdKlienta, zamowieniaKolRabat, zamowieniaKolStan, zamowieniaKolWartosc;
    @FXML
    private TableColumn<Zamowienie, Void> zamowieniaKolFaktura, zamowieniaKolUsun;
    //Filtry
    @FXML
    private TextField zamowieniaSzukajData, zamowieniaSzukajId, zamowieniaSzukajIdKlienta, zamowieniaSzukajRabat, zamowieniaSzukajStan, zamowieniaSzukajWartosc;
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
    private DostawcaService dostawcaService;
    private KlientService klientMethods;
    private ZamowienieService zamowienieMethods;
    private ElementZamowieniaService elementZamowieniaService;
    private ProduktService produktService;
    private UzytkownikService uzytkownikService;
    private Scene scene;
    //Listy
    public static ObservableList<Dostawca> dostawcy = FXCollections.observableArrayList();
    public static ObservableList<Uzytkownik> uzytkownicy = FXCollections.observableArrayList();
    public static ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    public static ObservableList<Klient> klienci = FXCollections.observableArrayList();
    public static ObservableList<Zamowienie> zamowienia = FXCollections.observableArrayList();
    public static ObservableList<OrderItem> elementyZamowienia = FXCollections.observableArrayList();
    private final String[] stanyZamowienia = {"w przygotowaniu", "gotowe", "odebrane"};

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
        dostawcaService = new DostawcaService();
        klientMethods = new KlientService();
        zamowienieMethods = new ZamowienieService();
        elementZamowieniaService = new ElementZamowieniaService();
        produktService = new ProduktService();
        uzytkownikService = new UzytkownikService();

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

        session.save(new Zamowienie(k, "22-02-2022", "złożone", 0.2));
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id='" + 1 + "'").addEntity(Zamowienie.class).getSingleResult();

        session.save(new OrderItem(z, p, 10, p.getCena() * 10, p.getCena()));

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Ustawienie wypełniania poszczególnych kolumn w tabelach
     */
    private void setTables() {
        StringConverter<String> converter = new DefaultStringConverter();

        //Tabela Uzytkownicy
        uzytkownicyKolImie.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setImie(newValue);
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolImie.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setImie(e.getNewValue()));
        uzytkownicyKolNazwisko.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setNazwisko(newValue);
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolNazwisko.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNazwisko(e.getNewValue()));
        uzytkownicyKolNrTel.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setNumerTelefonu(newValue);
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolNrTel.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumerTelefonu(e.getNewValue()));
        uzytkownicyKolEmail.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setEmail(newValue);
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolEmail.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue()));
        uzytkownicyKolHaslo.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setHaslo(newValue);
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolHaslo.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setHaslo(e.getNewValue()));
        uzytkownicyKolAdmin.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setIsAdmin(Integer.parseInt(newValue));
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolAdmin.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setIsAdmin(Integer.parseInt(e.getNewValue())));
        uzytkownicyKolGenRap.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setGenerowanieRaportow(Integer.parseInt(newValue));
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolGenRap.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setGenerowanieRaportow(Integer.parseInt(e.getNewValue())));
        uzytkownicyKolUdzRab.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Uzytkownik u = uzytkownicyTabela.getSelectionModel().getSelectedItem();
                    try{
                        u.setUdzielanieRabatow(Integer.parseInt(newValue));
                        uzytkownikService.updateUzytkownik(u);
                        uzytkownicyInformacje.appendText("\nPomyślnie edytowano użytkownika o id "+u.getId());
                    } catch(Exception e) {
                        uzytkownicyInformacje.appendText("\nNie udało się edytować użytkownika o id "+u.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        uzytkownicyKolUdzRab.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setUdzielanieRabatow(Integer.parseInt(e.getNewValue())));

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
                            if (uzytkownikService.deleteUzytkownik(u)) {
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
        dostawcyKolNazwa.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setNazwa(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolNazwa.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNazwa(e.getNewValue()));
        dostawcyKolNip.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setNip(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolNip.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNip(e.getNewValue()));
        dostawcyKolEmail.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setEmail(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolEmail.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue()));
        dostawcyKolMiejscowosc.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setMiejscowosc(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolMiejscowosc.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setMiejscowosc(e.getNewValue()));
        dostawcyKolUlica.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setUlica(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolUlica.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setUlica(e.getNewValue()));
        dostawcyKolKraj.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Dostawca d = dostawcyTabela.getSelectionModel().getSelectedItem();
                    try{
                        d.setKraj(newValue);
                        dostawcaService.updateDostawca(d);
                        dostawcyInformacje.appendText("\nPomyślnie edytowano dostawcę o id "+d.getId());
                    } catch(Exception e) {
                        dostawcyInformacje.appendText("\nNie udało się edytować dostawcy o id "+d.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        dostawcyKolKraj.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setKraj(e.getNewValue()));

        dostawcyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        dostawcyKolNazwa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNazwa()));
        dostawcyKolNip.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNip()));
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
                            if (dostawcaService.deleteDostawca(d)) {
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
        klienciKolImie.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setImie(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolImie.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setImie(e.getNewValue()));
        klienciKolNazwisko.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setNazwisko(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolNazwisko.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNazwisko(e.getNewValue()));
        klienciKolMiejscowosc.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setMiejscowosc(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolMiejscowosc.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setMiejscowosc(e.getNewValue()));
        klienciKolUlica.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setUlica(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolUlica.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setUlica(e.getNewValue()));
        klienciKolNrBud.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setNumerBudynku(Integer.parseInt(newValue));
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolNrBud.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumerBudynku(Integer.parseInt(e.getNewValue())));
        klienciKolNrMieszk.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setNumerMieszkania(Integer.parseInt(newValue));
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolNrMieszk.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumerMieszkania(Integer.parseInt(e.getNewValue())));
        klienciKolEmail.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setEmail(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolEmail.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue()));
        klienciKolNrTel.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setNumerTelefonu(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolNrTel.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setNumerTelefonu(e.getNewValue()));
        klienciKolPesel.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Klient k = klienciTabela.getSelectionModel().getSelectedItem();
                    try{
                        k.setPesel(newValue);
                        klientMethods.updateKlient(k);
                        klienciInformacje.appendText("\nPomyślnie edytowano klienta o id "+k.getId());
                    } catch(Exception e) {
                        klienciInformacje.appendText("\nNie udało się edytować klienta o id "+k.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        klienciKolPesel.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setPesel(e.getNewValue()));

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
        produktyKolIdDostawcy.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        produktService.updateProduktDostawca(p, newValue);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolIdDostawcy.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setDostawca(dostawcaService.getDostawca(e.getNewValue())));
        produktyKolKod.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setKod(newValue);
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolKod.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setKod(e.getNewValue()));
        produktyKolCena.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setCena(Double.parseDouble(newValue));
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolCena.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setCena(Double.parseDouble(e.getNewValue())));
        produktyKolIlosc.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setIlosc(Integer.parseInt(newValue));
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolIlosc.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setIlosc(Integer.parseInt(e.getNewValue())));
        produktyKolJM.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setJednostkaMiary(newValue);
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolJM.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setJednostkaMiary(e.getNewValue()));
        produktyKolKraj.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setKraj(newValue);
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolKraj.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setKraj(e.getNewValue()));
        produktyKolKolor.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setKolor(newValue);
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolKolor.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setKolor(e.getNewValue()));
        produktyKolMaxIlosc.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Produkt p = produktyTabela.getSelectionModel().getSelectedItem();
                    try{
                        p.setMaxIlosc(Integer.parseInt(newValue));
                        produktService.updateProdukt(p);
                        produktyInformacje.appendText("\nPomyślnie edytowano produkt o id "+p.getId());
                    } catch(Exception e) {
                        produktyInformacje.appendText("\nNie udało się edytować produktu o id "+p.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        produktyKolMaxIlosc.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setMaxIlosc(Integer.parseInt(e.getNewValue())));

        produktyKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        produktyKolIdDostawcy.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDostawca().getId())));
        produktyKolKod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKod()));
        produktyKolCena.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCena())));
        produktyKolIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));
        produktyKolJM.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJednostkaMiary()));
        produktyKolKraj.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKraj()));
        produktyKolKolor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKolor()));
        produktyKolMaxIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMaxIlosc())));
        produktyKolStan.setCellValueFactory(cellData -> new SimpleStringProperty(obliczStan(cellData.getValue().getIlosc()/cellData.getValue().getMaxIlosc())));
        produktyKolUwz.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Produkt, Boolean> call(final TableColumn<Produkt, Boolean> param) {
                final TableCell<Produkt, Boolean> cell = new TableCell<>() {
                    private final CheckBox check = new CheckBox();

                    {
                        check.setOnAction((ActionEvent event) -> produkty.get(getIndex()).setDostawa(!produkty.get(getIndex()).isDostawa()));
                        check.setOnMouseEntered(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.HAND);
                            }
                        });

                        check.setOnMouseExited(new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                scene.setCursor(Cursor.DEFAULT);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(check);
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        produktyKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Produkt, Void> call(final TableColumn<Produkt, Void> param) {
                final TableCell<Produkt, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Produkt p = getTableView().getItems().get(getIndex());
                            if (produktService.deleteProdukt(p)) {
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
        zamowieniaKolIdKlienta.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Zamowienie z = zamowieniaTabela.getSelectionModel().getSelectedItem();
                    zamowienieMethods.updateZamowienieKlient(z, newValue);
                }
                super.commitEdit(newValue);
            }
        });
        zamowieniaKolIdKlienta.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setKlient(klientMethods.getKlient(e.getNewValue())));
        zamowieniaKolData.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Zamowienie z = zamowieniaTabela.getSelectionModel().getSelectedItem();
                    try{
                        z.setData(newValue);
                        zamowienieMethods.updateZamowienie(z);
                        zamowieniaInformacje.appendText("\nPomyślnie edytowano zamowienie o id "+z.getId());
                    } catch(Exception e) {
                        zamowieniaInformacje.appendText("\nNie udało się edytować zamowienia o id "+z.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        zamowieniaKolData.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setData(e.getNewValue()));
        zamowieniaKolRabat.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    Zamowienie z = zamowieniaTabela.getSelectionModel().getSelectedItem();
                    double rabat;
                    switch(newValue) {
                        case "KOD1":
                            rabat = 0.1;
                            newValue = "-10%";
                            break;
                        case "KOD2":
                            rabat = 0.2;
                            newValue = "-20%";
                            break;
                        default:
                            rabat = 0.0;
                            newValue = "nie udzielono";
                    }

                    try{
                        z.setRabat(rabat);
                        zamowienieMethods.updateZamowienie(z);
                        zamowieniaInformacje.appendText("\nPomyślnie edytowano zamowienie o id "+z.getId());
                    } catch(Exception e) {
                        zamowieniaInformacje.appendText("\nNie udało się edytować zamowienia o id "+z.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        zamowieniaKolRabat.setOnEditCommit(e-> {
            String value;
            switch(e.getNewValue()) {
                case "KOD1":
                    value = "-10%";
                    break;
                case "KOD2":
                    value = "-20%";
                    break;
                default:
                    value = "nie udzielono";
            }
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setData(e.getNewValue());
        });

        zamowieniaKolStan.setCellFactory(ComboBoxTableCell.forTableColumn(stanyZamowienia));
        zamowieniaKolStan.setOnEditCommit(t -> {
            Zamowienie z = zamowieniaTabela.getSelectionModel().getSelectedItem();
            if (!Objects.equals(t.getNewValue(), t.getOldValue())) {
                try {
                    z.setStanZamowienia(t.getNewValue());
                    zamowienieMethods.updateZamowienie(z);
                    zamowieniaInformacje.appendText("\nPomyślnie edytowano zamowienie o id " + z.getId());
                } catch (Exception e) {
                    zamowieniaInformacje.appendText("\nNie udało się edytować zamowienia o id " + z.getId());
                }
            }
        });

        zamowieniaKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        zamowieniaKolIdKlienta.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getKlient().getId())));
        zamowieniaKolData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData()));
        zamowieniaKolWartosc.setCellValueFactory(cellData -> {
            List<OrderItem> list = elementZamowieniaService.getElementZamowienia(cellData.getValue().getId());
            double cena = 0.0;
            for (OrderItem el : list) {
                cena+=el.getCenaElementu()*el.getIlosc();
            }
            return new SimpleStringProperty(String.valueOf(Math.round(cena*100.0)/100.0));
        });
        zamowieniaKolRabat.setCellValueFactory(cellData -> {
            String kod = String.valueOf(cellData.getValue().getRabat());
            switch(kod) {
                case "0.1":
                    return new SimpleStringProperty("-10%");
                case "0.2":
                    return new SimpleStringProperty("-20%");
                default:
                    return new SimpleStringProperty("nie udzielono");
            }
        });

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
                            loadModal(z);
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
                                zamowieniaInformacje.appendText("\nPomyślnie usunięto zamowienie o id " + z.getId());
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
        elemZamKolIdZamowienia.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem ez = elemZamTabela.getSelectionModel().getSelectedItem();
                    try{
                        elementZamowieniaService.updateElementZamowieniaZamowienie(ez, newValue);
                        elemZamInformacje.appendText("\nPomyślnie edytowano element zamówienia o id "+ez.getId());
                    } catch(Exception e) {
                        elemZamInformacje.appendText("\nNie udało się edytować elementu zamowienia o id "+ez.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        elemZamKolIdZamowienia.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setZamowienie(zamowienieMethods.getZamowienie(e.getNewValue())));
        elemZamKolIdProduktu.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem ez = elemZamTabela.getSelectionModel().getSelectedItem();
                    try{
                        elementZamowieniaService.updateElementZamowieniaProdukt(ez, newValue);
                        elemZamInformacje.appendText("\nPomyślnie edytowano element zamówienia o id "+ez.getId());
                    } catch(Exception e) {
                        elemZamInformacje.appendText("\nNie udało się edytować elementu zamowienia o id "+ez.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        elemZamKolIdProduktu.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setProdukt(produktService.getProdukt(e.getNewValue())));
        elemZamKolIlosc.setCellFactory(cell -> new TextFieldTableCell<>(converter) {
            @Override
            public void commitEdit(String newValue) {
                if (!Objects.equals(newValue, getItem())) {
                    OrderItem ez = elemZamTabela.getSelectionModel().getSelectedItem();
                    ez.setIlosc(Integer.parseInt(newValue));
                    ez.setCenaElementu(ez.getIlosc()*ez.getCenaZaJednostke());
                    try{
                        elementZamowieniaService.updateElementZamowienia(ez);
                        elemZamInformacje.appendText("\nPomyślnie edytowano element zamówienia o id "+ez.getId());
                    } catch(Exception e) {
                        elemZamInformacje.appendText("\nNie udało się edytować elementu zamowienia o id "+ez.getId());
                    }
                }
                super.commitEdit(newValue);
            }
        });
        elemZamKolIlosc.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setIlosc(Integer.parseInt(e.getNewValue())));

        elemZamKolId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        elemZamKolIdZamowienia.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getZamowienie().getId())));
        elemZamKolIdProduktu.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProdukt().getId())));
        elemZamKolCenaEl.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaElementu())));
        elemZamKolCenaJM.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaZaJednostke())));
        elemZamKolIlosc.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));
        elemZamKolUsun.setCellFactory(new Callback<>() {
            @Override
            public TableCell<OrderItem, Void> call(final TableColumn<OrderItem, Void> param) {
                final TableCell<OrderItem, Void> cell = new TableCell<>() {
                    final Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OrderItem ez = getTableView().getItems().get(getIndex());
                            if (elementZamowieniaService.deleteElementZamowienia(ez)) {
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
        if (stosunek < 30) return "niski";
        else if (stosunek < 70) return "umiarkowany";
        else return "wysoki";
    }

    /**
     * Załadowanie okna zapisu raportu.
     *
     * @param type typ raportu
     * @return
     */
    public void loadModal(int type) {
        RaportAbstract raport=null;
        switch (type){
            case 1:
                raport = new UzytkownicyRaport(uzytkownicyTabela.getItems());
                break;
            case 2:
                raport = new KlienciRaport(klienciTabela.getItems());
                break;
            case 3:
                raport = new DostawcyRaport(dostawcyTabela.getItems());
                break;
            case 4:
                raport = new ProduktyRaport(produktyTabela.getItems());
                break;
            case 5:
                raport = new ZamowieniaRaport(zamowieniaTabela.getItems());
                break;
            case 6:
                raport = new ElementyZamowieniaRaport(elemZamTabela.getItems());
                break;
            case 7:
                raport = new DostawaRaport(produktyTabela.getItems());
                break;
        }

        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            PDFController controller = fxmlLoader.getController();
            controller.setRaport(raport);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Błąd załadowania modułu generowania raportu.");
            alert.showAndWait();
        }
    }

    /**
     * Załadowanie okna zapisu faktury.
     *
     * @param z zamówienie, z którego generowana jest faktura
     */
    public void loadModal(Zamowienie z) {
        RaportAbstract raport=new FakturaRaport(z);

        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("Generuj raport");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/PDF-save-view.fxml"));
            Parent root = fxmlLoader.load();
            PDFController controller = fxmlLoader.getController();
            controller.setRaport(raport);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText("Błąd załadowania modułu generowania raportu.");
            alert.showAndWait();
        }
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
        uzytkownicy.setAll(uzytkownikService.getUzytkownicy());
    }

    /**
     * Obsługuje przycisk dodawania nowego użytkownika.
     *
     * @param event
     */
    @FXML
    public void uzytkownicyBtnDodajClicked(MouseEvent event) {
        try {
            Uzytkownik u = uzytkownikService.saveUzytkownik(uzytkownicyPoleImie.getText(), uzytkownicyPoleNazwisko.getText(), uzytkownicyPoleNrTel.getText(), uzytkownicyPoleEmail.getText(), uzytkownicyPoleHaslo.getText(), uzytkownicyRaport.isSelected() ? 1 : 0, uzytkownicyRabat.isSelected() ? 1 : 0, uzytkownicyAdmin.isSelected() ? 1 : 0);
            uzytkownicyInformacje.appendText("\nDodano nowego użytkownika");
        } catch(Exception e) {
            uzytkownicyInformacje.appendText("\nNie udało się dodać nowego użytkownika");
        }
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
        loadModal(1);
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
        try {
            Klient k = klientMethods.saveKlient(klienciPoleImie.getText(), klienciPoleNazwisko.getText(), klienciPolePesel.getText(), klienciPoleNrTel.getText(), klienciPoleEmail.getText(), klienciPoleMiejscowosc.getText(), klienciPoleUlica.getText(), Integer.valueOf(klienciPoleNrMieszkania.getText()), Integer.valueOf(klienciPoleNrBud.getText()));
            klienciInformacje.appendText("\nDodano nowego klienta");
        } catch(Exception e) {
            klienciInformacje.appendText("\nNie udało się dodać nowego klienta");
        }
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
        loadModal(2);
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
        dostawcy.setAll(dostawcaService.getDostawcy());
    }

    /**
     * Obsługuje przycisk dodawania nowego dostawcy.
     *
     * @param event
     */
    @FXML
    public void dostawcyBtnDodajClicked(MouseEvent event) {
        try {
            Dostawca d = dostawcaService.saveDostawca(dostawcyPoleEmail.getText(), dostawcyPoleKraj.getText(), dostawcyPoleMiejscowosc.getText(), dostawcyPoleUlica.getText(), dostawcyPoleNazwa.getText(), dostawcyPoleNip.getText());
            dostawcyInformacje.appendText("\nDodano nowego dostawcę");
        } catch(Exception e) {
            dostawcyInformacje.appendText("\nNie udało się dodać nowego dostawcy");
        }
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
        loadModal(3);
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
        produkty.setAll(produktService.getProdukty());
    }

    /**
     * Obsługuje przycisk dodawania nowego produktu.
     *
     * @param event
     */
    @FXML
    public void produktyBtnDodajClicked(MouseEvent event) {
        try {
            Produkt p = produktService.saveProdukt(Integer.parseInt(produktyPoleIdDostawcy.getText()), produktyPoleNazwa.getText(), produktyPoleJM.getText(), Double.parseDouble(produktyPoleCena.getText()), produktyPoleKraj.getText(), produktyPoleKod.getText(), produktyPoleKolor.getText(), Integer.parseInt(produktyPoleIlosc.getText()), Integer.parseInt(produktyPoleMaxIlosc.getText()));
            produktyInformacje.appendText("\nDodano nowy produkt");
        } catch(Exception e) {
            produktyInformacje.appendText("\nNie udało się dodać nowego produktu");
        }
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
    public void produktyBtnRaportClicked(MouseEvent event) {loadModal(4);}

    /**
     * Obsługuje przycisk generowania raportu dostaw.
     *
     * @param event
     */
    @FXML
    public void produktyBtnRaportDostawClicked(MouseEvent event) {
        loadModal(7);

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
        try {
            Double rabat;
            String kodRabatowy = zamowieniaPoleRabat.getText();
            switch(kodRabatowy) {
                case "KOD1":
                    rabat = 0.1;
                    break;
                case "KOD2":
                    rabat = 0.2;
                    break;
                default:
                    rabat = 0.0;
            }

            Zamowienie z = zamowienieMethods.saveZamowienie(Integer.parseInt(zamowieniaIdKlienta.getText()), zamowieniaPoleData.getText(), "w przygotowaniu", rabat);
            zamowieniaInformacje.appendText("\nDodano nowe zamówienie");
        } catch(Exception e) {
            zamowieniaInformacje.appendText("\nNie udało się dodać nowego zamówienia");
        }
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
        loadModal(5);
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
        elementyZamowienia.setAll(elementZamowieniaService.getElementyZamowienia());
    }

    /**
     * Obsługuje przycisk dodawania nowego elementu zamówienia.
     *
     * @param event
     */
    @FXML
    public void elemZamBtnDodajClicked(MouseEvent event) {
        try {
            OrderItem ez = elementZamowieniaService.saveElementZamowienia(Integer.parseInt(elemZamPoleIdProduktu.getText()), Integer.parseInt(elemZamPoleIdZamowienia.getText()), Integer.parseInt(elemZamPoleIlosc.getText()));
            elemZamInformacje.appendText("\nDodano nowy element zamówienia");
        } catch(Exception e) {
            elemZamInformacje.appendText("\nNie udało się dodać nowego elementu zamówienia");
        }
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
        loadModal(6);
    }

}
