package Controllers;

import DatabaseAccess.*;
import DatabaseQueries.*;
import Entities.*;
import Singleton.SingletonConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Kontroler głównego okna aplikacji.
 */
public class MainController implements Initializable {

    //elementy FXML

    @FXML
    private Pane paneLogowanie;

    //tabele
            //tab dostawcy
            @FXML
            private TableView<Dostawca> tabDostawcy;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyEmail;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyId;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyKraj;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyMiejscowosc;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyNazwa;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyNip;

            @FXML
            private TableColumn<Dostawca, String> tabDostawcyUlica;

            @FXML
            private TableColumn<Dostawca, Void> tabDostawcyDelete;

            ///////////////////////////////////////////////////
            //tab klienci
            @FXML
            private TableView<Klient> tabKlienci;

            @FXML
            private TableColumn<Klient, String> tabKlienciEmail;

            @FXML
            private TableColumn<Klient, String> tabKlienciId;

            @FXML
            private TableColumn<Klient, String> tabKlienciImie;

            @FXML
            private TableColumn<Klient, String> tabKlienciMiejscowosc;

            @FXML
            private TableColumn<Klient, String> tabKlienciNazwisko;

            @FXML
            private TableColumn<Klient, String> tabKlienciNrMieszkania;
            @FXML
            private TableColumn<Klient, String> tabKlienciUlica;

            @FXML
            private TableColumn<Klient, String> tabKlienciNrTel;

            @FXML
            private TableColumn<Klient, String> tabKlienciPesel;

            @FXML
            private TableColumn<Klient, Void> tabKlienciDelete;

            ///////////////////////////////////////////////////
            //tab produkty
            @FXML
            private TableView<Produkt> tabProdukty;

            @FXML
            private TableColumn<Produkt, String> tabProduktyCena;

            @FXML
            private TableColumn<Produkt, String> tabProduktyId;

            @FXML
            private TableColumn<Produkt, String> tabProduktyIdDostawcy;

            @FXML
            private TableColumn<Produkt, String> tabProduktyIlosc;

            @FXML
            private TableColumn<Produkt, String> tabProduktyJm;

            @FXML
            private TableColumn<Produkt, String> tabProduktyKod;

            @FXML
            private TableColumn<Produkt, String> tabProduktyKolor;

            @FXML
            private TableColumn<Produkt, String> tabProduktyKraj;

            @FXML
            private TableColumn<Produkt, String> tabProduktyMaxIlosc;

            @FXML
            private TableColumn<Produkt, String> tabProduktyNazwa;

            @FXML
            private TableColumn<Produkt, String> tabProduktyStan;

            @FXML
            private TableColumn<Produkt, Void> tabProduktyDelete;

            ///////////////////////////////////////////////////
            //tab elementy zamowienia
            @FXML
            private TableView<ElementZamowienia> tabElementyZamowienia;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaCenaEl;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaCenaProduktu;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaId;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaIlosc;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaIdZamowienia;

            @FXML
            private TableColumn<ElementZamowienia, String> tabElementyZamowieniaIdProduktu;

            @FXML
            private TableColumn<ElementZamowienia, Void> tabElementyZamowieniaDelete;

            ///////////////////////////////////////////////////
            //tab zamowienia
            @FXML
            private TableView<Zamowienie> tabZamowienia;

            @FXML
            private TableColumn<Zamowienie, String> tabZamowieniaData;

            @FXML
            private TableColumn<Zamowienie, String> tabZamowieniaId;

            @FXML
            private TableColumn<Zamowienie, String> tabZamowieniaIdKlienta;

            @FXML
            private TableColumn<Zamowienie, String> tabZamowieniaStan;

            @FXML
            private TableColumn<Zamowienie, String> tabZamowieniaRabat;

            @FXML
            private TableColumn<Zamowienie, Void> tabZamowieniaDelete;



    //obiekty
    private DbAccess dbAccess;
    private Login loginService;
    private DostawcaMethods dostawcaMethods;
    private KlientMethods klientMethods;
    private ZamowienieMethods zamowienieMethods;
    private ElementZamowieniaMethods elementZamowieniaMethods;
    private ProduktMethods produktMethods;
    private UzytkownikMethods uzytkownikMethods;

    //listy
    public static ObservableList<Dostawca> dostawcy = FXCollections.observableArrayList();
    public static ObservableList<Produkt> produkty = FXCollections.observableArrayList();
    public static ObservableList<Klient> klienci = FXCollections.observableArrayList();
    public static ObservableList<Zamowienie> zamowienia = FXCollections.observableArrayList();
    public static ObservableList<ElementZamowienia> elementyZamowienia = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  paneLogowanie.toFront();

        dbAccess = new DbAccess();
        dbAccess.getDatabaseName();

        loginService = new Login();
        dostawcaMethods = new DostawcaMethods();
        klientMethods = new KlientMethods();
        zamowienieMethods = new ZamowienieMethods();
        elementZamowieniaMethods = new ElementZamowieniaMethods();
        produktMethods = new ProduktMethods();
        uzytkownikMethods = new UzytkownikMethods();

        //TODO: logowanie

        setTables();
        insertData();

    }

    /**
    * Testowy insert danych
    */
    public void insertData(){
        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session;

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.flush();

        session.save(new StanZamowienia("złożone"));
        session.save(new StanZamowienia("skompletowane"));
        session.save(new StanZamowienia("wysłane"));
        StanZamowienia s = (StanZamowienia) session.createSQLQuery("select * from stanzamowienia where id=\'" + 1 + "\'").addEntity(StanZamowienia.class).getSingleResult();

        session.save(new Dostawca("email","chiny","pekin","ulica","nazwa","NIP"));
        Dostawca d = (Dostawca) session.createSQLQuery("select * from dostawca where id=\'" + 1 + "\'").addEntity(Dostawca.class).getSingleResult();

        session.save(new Klient("imie","nazwisko","pesel","nrtel","email","miejscowosc","ulica","nrmieszk"));
        Klient k = (Klient) session.createSQLQuery("select * from klient where id=\'" + 1 + "\'").addEntity(Klient.class).getSingleResult();

        session.save(new Produkt(d,"nazwa","sztuka",22.56,"Polska","AS2345","brak",80,100));
        Produkt p = (Produkt) session.createSQLQuery("select * from produkt where id=\'" + 1 + "\'").addEntity(Produkt.class).getSingleResult();

        session.save(new Zamowienie(k,s,"22-02-2022","-20%"));
        Zamowienie z = (Zamowienie) session.createSQLQuery("select * from zamowienie where id=\'" + 1 + "\'").addEntity(Zamowienie.class).getSingleResult();

        session.save(new ElementZamowienia(z,p,10, p.getCena()));

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Ustawienie wypełniania poszczególnych kolumn w tabelach
     */
    public void setTables(){
        //tabela dostawców
        tabDostawcyId.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        tabDostawcyEmail.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEmail()));

        tabDostawcyKraj.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getKraj()));

        tabDostawcyMiejscowosc.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getMiejscowosc()));

        tabDostawcyUlica.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getUlica()));

        tabDostawcyNazwa.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNazwa()));

        tabDostawcyNip.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNip()));

        //button delete
        tabDostawcyDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Dostawca, Void> call(final TableColumn<Dostawca, Void> param) {
                final TableCell<Dostawca, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           /* if (user.getRola().equals("admin")) {
                                Student s = getTableView().getItems().get(getIndex());
                                studentMethods.deleteStudent(s);
                                studenci.remove(s);
                                System.out.println(true);
                            } else {
                                alert.setTitle("ERROR");
                               alert.setHeaderText("ERROR");
                                alert.setContentText("Brak uprawnień.");
                                alert.showAndWait();
                                System.out.println(false);
                            }*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
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

        tabDostawcy.setItems(dostawcy);

        //tabela klientów
        tabKlienciId.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        tabKlienciImie.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getImie()));

        tabKlienciNazwisko.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNazwisko()));

        tabKlienciPesel.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPesel()));

        tabKlienciNrTel.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNumerTelefonu()));

        tabKlienciEmail.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEmail()));

        tabKlienciMiejscowosc.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getMiejscowosc()));

        tabKlienciUlica.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getUlica()));

        tabKlienciNrMieszkania.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNumerMieszkania()));

        //button delete
        tabKlienciDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Klient, Void> call(final TableColumn<Klient, Void> param) {
                final TableCell<Klient, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           /* if (user.getRola().equals("admin")) {
                                Student s = getTableView().getItems().get(getIndex());
                                studentMethods.deleteStudent(s);
                                studenci.remove(s);
                                System.out.println(true);
                            } else {
                                alert.setTitle("ERROR");
                               alert.setHeaderText("ERROR");
                                alert.setContentText("Brak uprawnień.");
                                alert.showAndWait();
                                System.out.println(false);
                            }*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
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

        tabKlienci.setItems(klienci);

        //tabela produktów
        tabProduktyId.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        tabProduktyIdDostawcy.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDostawca().getId())));

        tabProduktyNazwa.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNazwa()));

        tabProduktyJm.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getJednostkaMiary()));

        tabProduktyCena.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCena())));

        tabProduktyKraj.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getKraj()));

        tabProduktyKod.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getKod()));

        tabProduktyKolor.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getKolor()));

        tabProduktyIlosc.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));

        tabProduktyMaxIlosc.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMaxIlosc())));

        tabProduktyStan.setCellValueFactory(cellData
                -> {
            double x = cellData.getValue().getIlosc()/cellData.getValue().getMaxIlosc();
            return new SimpleStringProperty(calculateStan(x));
        });

        tabProduktyDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Produkt, Void> call(final TableColumn<Produkt, Void> param) {
                final TableCell<Produkt, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           /* if (user.getRola().equals("admin")) {
                                Student s = getTableView().getItems().get(getIndex());
                                studentMethods.deleteStudent(s);
                                studenci.remove(s);
                                System.out.println(true);
                            } else {
                                alert.setTitle("ERROR");
                               alert.setHeaderText("ERROR");
                                alert.setContentText("Brak uprawnień.");
                                alert.showAndWait();
                                System.out.println(false);
                            }*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
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

        tabProdukty.setItems(produkty);

        //tabela zamówień
        tabZamowieniaId.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        tabZamowieniaIdKlienta.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getKlient().getId())));

        tabZamowieniaData.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getData()));

        tabZamowieniaStan.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStanZamowienia().getStan())));

        tabZamowieniaRabat.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRabat())));

        tabZamowieniaDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Zamowienie, Void> call(final TableColumn<Zamowienie, Void> param) {
                final TableCell<Zamowienie, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           /* if (user.getRola().equals("admin")) {
                                Student s = getTableView().getItems().get(getIndex());
                                studentMethods.deleteStudent(s);
                                studenci.remove(s);
                                System.out.println(true);
                            } else {
                                alert.setTitle("ERROR");
                               alert.setHeaderText("ERROR");
                                alert.setContentText("Brak uprawnień.");
                                alert.showAndWait();
                                System.out.println(false);
                            }*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
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

        tabZamowienia.setItems(zamowienia);

        //listener na wybór pola w tabeli zamowienia
        tabZamowienia.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<ElementZamowienia> list = elementZamowieniaMethods.getElementyZamowienia(newSelection.getId()); //pobierz elmenty wybranego zamowienia
                elementyZamowienia.setAll(list);
            }
        });

        //tabela elementów zamówienia
        tabElementyZamowieniaId.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        tabElementyZamowieniaIdProduktu.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProdukt().getId())));

        tabElementyZamowieniaIdZamowienia.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getZamowienie().getId())));

        tabElementyZamowieniaIlosc.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIlosc())));

        tabElementyZamowieniaCenaEl.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaProduktu()*cellData.getValue().getIlosc())));

        tabElementyZamowieniaCenaProduktu.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCenaProduktu())));

        tabElementyZamowieniaDelete.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ElementZamowienia, Void> call(final TableColumn<ElementZamowienia, Void> param) {
                final TableCell<ElementZamowienia, Void> cell = new TableCell<>() {
                    Image image = new Image(getClass().getResourceAsStream("/Images/delete.jpg"), 25, 25, false, false);
                    private final Button btn = new Button();

                    {
                        btn.setOnAction((ActionEvent event) -> {
                           /* if (user.getRola().equals("admin")) {
                                Student s = getTableView().getItems().get(getIndex());
                                studentMethods.deleteStudent(s);
                                studenci.remove(s);
                                System.out.println(true);
                            } else {
                                alert.setTitle("ERROR");
                               alert.setHeaderText("ERROR");
                                alert.setContentText("Brak uprawnień.");
                                alert.showAndWait();
                                System.out.println(false);
                            }*/
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
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

        tabElementyZamowienia.setItems(elementyZamowienia);

    }

    /**
     * Oblicza stan produktu.
     * *
     * @param x stosunek ilości produktu w magazynie i całkowitej pojemności magazynu na dany produkt
     * @return stan magazynowy produktu
     */
    public String calculateStan(double x){
        //TODO: ustalić progi
        if(x<30) return "niski";
        else if(x<60) return "umiarkowany";
        else return "wysoki";
    }


    //////////////////////////////////////////////////////////////////////////////
    //obsługa przycisków
    @FXML
    public void btnZaloguj(MouseEvent event) {
        //TODO: obsłużyć logowanie
        loginService.zaloguj();
    }

    @FXML
    public void btnDostawcyPobierzClicked(MouseEvent event) {
        tabDostawcy.getItems().clear();
        dostawcy.setAll(dostawcaMethods.getDostawcy());
    }

    @FXML
    public void btnKlienciPobierzClicked(MouseEvent event) {
        tabKlienci.getItems().clear();
        klienci.setAll(klientMethods.getKlienci());
    }

    @FXML
    public void btnProduktyPobierzClicked(MouseEvent event) {
        tabProdukty.getItems().clear();
        produkty.setAll(produktMethods.getProdukty());
    }

    @FXML
    public void btnZamowieniaPobierzClicked(MouseEvent event) {
        tabZamowienia.getItems().clear();
        zamowienia.setAll(zamowienieMethods.getZamowienia());
    }

}
