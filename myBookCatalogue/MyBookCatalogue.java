package myBookCatalogue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import static javafx.application.Application.launch;

/**
 * Class contains all of the GUI components and main functionality for 
 * the My Book Catalogue application
 * @author Lawrence Young
 */
public class MyBookCatalogue extends Application {
    
    // Fields used in entire GUI
    private BookCatalogue bc;
    private Stage stage;
    
    //Fields for Welecome Pane
    private BorderPane welcomePane;
    private HBox hbAppTitle;
    private Label lblWelcome, lblAppTitle;
    private Button btnEnterApp;
    private VBox vbWelcome;
    
    //Fields for Main Pane
    private BorderPane root;
    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem exitMenuItem, homePageMenuItem;
    private HBox hbEverything;
    private RandomAccessFile raf;
    private Scene scene;
    
    //Entry Form Fields
    private Alert titleError, authorError, isbnError, genreError, formatError, 
            pagesError, priceError, saveConfirm, updateConfirm, deleteConfirm;
    private Button btnSave, btnUpdate, btnReset;
    private ComboBox cmbGenre, cmbFormat;
    private HBox hbSingleBook, hbButtons1, hbTitle, hbAuthor, hbISBN, hbGenre, hbFormat,
            hbPages, hbPrice;
    private Label lblSingleBook, lblTitle, lblAuthor, lblISBN, lblGenre, lblFormat,
            lblPages, lblPrice;
    private ObservableList genreList, formatList; 
    private TextField txtTitle, txtAuthor, txtISBN, txtPages, txtPrice;
    private VBox vbEntry;
    
    //Records Display Fields
    private Button btnSearch, btnDelete, btnExit;
    private HBox hbAllRecords, hbRecords, hbButtons2;
    private Label lblAllBooks;
    private ObservableList recordsList;
    private TableView<Book> allRecords;
    private TableColumn<Book, String> titleCol, authorCol, isbnCol, genreCol, 
            formatCol, pagesCol, priceCol;
    private VBox vbRecords;
    
    //Record size details
    public int TITLE_SIZE = 50;
    public int AUTHOR_SIZE = 50;
    public int ISBN_SIZE = 13;
    public int GENRE_SIZE = 20;
    public int FORMAT_SIZE = 10;
    public int RECORD_SIZE = 298;
    
    /**
     * Creates the GUI
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        bc = new BookCatalogue();
        bc.getRecords();
 
        scene = createWelcomeScene();
        
        primaryStage.setTitle("My Book Catalogue");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        launch(args);

    }
    
    /**
     * Method is used to create the welcome page scene
     * @return 
     */
    public Scene createWelcomeScene(){
        // Main title
        lblAppTitle = new Label("My Book Catalogue");
        hbAppTitle = new HBox(lblAppTitle);
        hbAppTitle.setAlignment(Pos.CENTER);
        hbAppTitle.setMargin(lblAppTitle, new Insets(20, 20, 20, 20));
        
        //Welcome section
        lblWelcome = new Label("Welcome!");
        btnEnterApp = new Button("Enter App");
        btnEnterApp.setOnAction(e -> stage.setScene(createMainScene()));
        vbWelcome = new VBox(lblWelcome, btnEnterApp);
        vbWelcome.setMargin(lblWelcome, new Insets(10, 10, 10, 10));
        vbWelcome.setMargin(btnEnterApp, new Insets(10, 10, 10, 10));
        vbWelcome.setAlignment(Pos.CENTER);
        
        // Section for creating pane and adding styles
        welcomePane = new BorderPane();
        welcomePane.setTop(hbAppTitle);
        welcomePane.setCenter(vbWelcome);
        scene = new Scene(welcomePane, 1000, 600);
        scene.getStylesheets().add("/myBookCatalogue/style.css");
        return scene;
    }
    
    /**
     * Method is used to construct the main application scene
     * @return the main application scene
     */
    public Scene createMainScene(){
        // Loads records into book catalogue
        bc = new BookCatalogue();
        bc.getRecords();
        
        // Creates the two main panes
        vbEntry = createEntryForm();
        vbRecords = createRecordsDisplay();
        hbEverything = new HBox(vbEntry, vbRecords);
        hbEverything.setMargin(vbEntry, new Insets(20, 20, 10, 20));
        hbEverything.setMargin(vbRecords, new Insets(20, 20, 20, 20));
        hbEverything.setAlignment(Pos.CENTER);
        hbEverything.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, null, new BorderWidths(2))));

        menuBar = setMenuBar();
        root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(hbEverything);
        scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("/myBookCatalogue/style.css");
        return scene;
    }
    
    /**
     * Method constructs the menu bar for the application
     * @return menu bar
     */
    public MenuBar setMenuBar() {
        menuBar = new MenuBar();
        fileMenu = new Menu("_File");
        fileMenu.setMnemonicParsing(true);
        //Exit Menu item
        exitMenuItem = new MenuItem("_Exit");
        exitMenuItem.setMnemonicParsing(true);
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
        //home page menu item
        homePageMenuItem = new MenuItem("_Home Page");
        homePageMenuItem.setMnemonicParsing(true);
        homePageMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.SHORTCUT_DOWN));
        fileMenu.getItems().addAll(homePageMenuItem, exitMenuItem);
        menuBar.getMenus().add(fileMenu);
        // Action Events
        exitMenuItem.setOnAction((ActionEvent t) -> {
            System.exit(0);   
        });
        homePageMenuItem.setOnAction((ActionEvent t) -> {
            stage.setScene(createWelcomeScene());
        });
        return menuBar;
    }
   
    /**
     * Method constructs the data entry form
     * @return VBox containing the constructed data entry form
     */
    public VBox createEntryForm() {
        //Tile for form area
        lblSingleBook = new Label("Single Book");
        hbSingleBook = new HBox(lblSingleBook);
        hbSingleBook.setAlignment(Pos.CENTER);
        //All form elements
        lblTitle = new Label("Title");
        txtTitle = new TextField();
        hbTitle = new HBox(5, lblTitle, txtTitle);
        lblAuthor = new Label("Author");
        txtAuthor = new TextField();
        hbAuthor = new HBox(5, lblAuthor, txtAuthor);
        lblISBN = new Label("ISBN");
        txtISBN = new TextField();
        hbISBN = new HBox(5, lblISBN, txtISBN);
        lblGenre = new Label("Genre");
        cmbGenre = createGenreCmb();
        hbGenre = new HBox(5, lblGenre, cmbGenre);
        lblFormat = new Label("Format");
        cmbFormat = createFormatCmb();
        hbFormat = new HBox(5, lblFormat, cmbFormat);
        lblPages = new Label("# Pages");
        txtPages = new TextField();
        hbPages = new HBox(5, lblPages, txtPages);
        lblPrice = new Label("Price");
        txtPrice = new TextField();
        hbPrice = new HBox(5, lblPrice, txtPrice);
        //Save, update, and reset buttons
        btnSave = new Button("Save");
        btnSave.setOnAction(e -> eventCode(e));
        btnUpdate = new Button("Update");
        btnUpdate.setOnAction(e -> eventCode(e));
        btnReset = new Button("Reset");
        btnReset.setOnAction(e -> eventCode(e));
        hbButtons1 = new HBox(5, btnSave, btnUpdate, btnReset);
        hbButtons1.setAlignment(Pos.CENTER);
        //Creating the VBox
        vbEntry = new VBox();
        vbEntry.getChildren().add(hbSingleBook);
        vbEntry.setMargin(hbSingleBook, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbTitle);
        vbEntry.setMargin(hbTitle, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbAuthor);
        vbEntry.setMargin(hbAuthor, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbISBN);
        vbEntry.setMargin(hbISBN, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbGenre);
        vbEntry.setMargin(hbGenre, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbFormat);
        vbEntry.setMargin(hbFormat, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbPages);
        vbEntry.setMargin(hbPages, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbPrice);
        vbEntry.setMargin(hbPrice, new Insets(10, 10, 10, 10));
        vbEntry.getChildren().add(hbButtons1);
        vbEntry.setMargin(hbButtons1, new Insets(10, 10, 10, 10));
        vbEntry.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        return vbEntry;
    }
    /**
     * Method used to construct the genres combo box
     * @return ComboBox containing genres
     */
    public ComboBox createGenreCmb() {
        //Getting genre names
        ArrayList<String> gList = new ArrayList<String>();
        Genres[] genres = Genres.values();
        int i = 0;
        for (Genres g : genres) {
            gList.add(genres[i].getGenreName());
            i++;
        }
        //Creating genre combobox
        cmbGenre = new ComboBox();
        genreList = FXCollections.observableArrayList(gList);
        cmbGenre.setItems(genreList);
        cmbGenre.setValue("Select Genre");
        return cmbGenre;
    }
    
    /**
     * Method is used to construct the formats combo box
     * @return ComboBox containing all format choices
     */
    public ComboBox createFormatCmb() {
        //Getting format names
        ArrayList<String> fList = new ArrayList<String>();
        Formats[] formats = Formats.values();
        int i = 0;
        for (Formats f : formats) {
            fList.add(formats[i].getFormatName());
            i++;
        }
        
        //Creating the comboBox
        cmbFormat = new ComboBox();
        formatList = FXCollections.observableArrayList(fList);
        cmbFormat.setItems(formatList);
        cmbFormat.setValue("Select Format");
        return cmbFormat;
    }
    
    /**
     * Method is used to create the records display, search, delete and exit buttons
     * @return VBox the created records display and buttons
     */
    public VBox createRecordsDisplay() {
        //Creates title for displa area
        lblAllBooks = new Label("All Books");
        hbAllRecords = new HBox(lblAllBooks);
        hbAllRecords.setAlignment(Pos.CENTER);
        
        //Following code creates a TableView objects and sets up columns
        allRecords = new TableView<Book>();
        recordsList = FXCollections.observableArrayList(this.bc);
        allRecords.setItems(recordsList);
        titleCol = new TableColumn<Book,String>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        authorCol = new TableColumn<Book,String>("Author");
        authorCol.setCellValueFactory(new PropertyValueFactory("author"));
        isbnCol = new TableColumn<Book,String>("ISBN");
        isbnCol.setCellValueFactory(new PropertyValueFactory("isbn"));
        genreCol = new TableColumn<Book,String>("Genre");
        genreCol.setCellValueFactory(new PropertyValueFactory("genre"));
        formatCol = new TableColumn<Book,String>("Format");
        formatCol.setCellValueFactory(new PropertyValueFactory("format"));
        pagesCol = new TableColumn<Book,String>("Pages");
        pagesCol.setCellValueFactory(new PropertyValueFactory("pages"));
        priceCol = new TableColumn<Book,String>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        allRecords.getColumns().setAll(titleCol, authorCol, isbnCol, genreCol,
                formatCol, pagesCol, priceCol);
        
        //Event Listener that takes the selected table row and adds to entry form
        allRecords.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Book clickedRow = row.getItem();
                    selectedBook(clickedRow);
                }
            });
            return row ;
        });
        
        hbRecords = new HBox(5, allRecords);
        hbRecords.setAlignment(Pos.CENTER);
        
        //Following code creates the search, delete and exit buttons
        btnSearch = new Button("Search");
        btnSearch.setOnAction(e -> eventCode(e));
        btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> eventCode(e));
        btnExit = new Button("Exit");
        btnExit.setOnAction(e -> eventCode(e));
        hbButtons2 = new HBox(5, btnSearch, btnDelete, btnExit);
        hbButtons2.setAlignment(Pos.CENTER);
        
        //Follwing code creates the VBox that contains the records display
        vbRecords = new VBox();
        vbRecords.getChildren().addAll(hbAllRecords, hbRecords, hbButtons2);
        vbRecords.setMargin(hbAllRecords, new Insets(10, 10, 10, 10));
        vbRecords.setMargin(hbRecords, new Insets(10, 10, 10, 10));
        vbRecords.setMargin(hbButtons2, new Insets(10, 10, 10, 10));
        vbRecords.setBorder(new Border(new BorderStroke(Color.BLACK, 
                BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        return vbRecords;
    }
    
    /**
     * Method sets entry form fields to the selected table view row's data
     * @param book - the book that is selected
     */
    public void selectedBook(Book book) {
        txtTitle.setText(book.getTitle().trim());
        txtAuthor.setText(book.getAuthor().trim());
        txtISBN.setText(book.getIsbn().trim());
        cmbGenre.setValue(book.getGenre().trim());
        cmbFormat.setValue(book.getFormat().trim());
        txtPages.setText("" + book.getPages());
        txtPrice.setText("" + book.getPrice());    
    }
    
    /**
     * Method is used to read a string from a random access file
     * @param raf a random access file
     * @param size the size of the string field
     * @return String field
     * @throws IOException
     */      
    public String readString(RandomAccessFile raf, int size)
            throws java.io.IOException {
        String n = "";
        for (int i = 0; i < size; i++) {
            n += String.valueOf(raf.readChar());
        }
        return n;
    }
    
    /**
     * Method prepares each string field to be written into the random access
     * file by either padding it with extra spaces or trim the size of the field
     * @param value the string value that needs to be prepared
     * @param size the desired size for that particular field
     * @return prepared string
     */
    public String prepStringField(String value, int size) {
        if (value.length() < size) {
            int numSpaces = size - value.length();
            for (int i = 1; i <= numSpaces; i++) {
                value += " ";
            }
        } else {
            value = value.substring(0, size);
        }
        return value;
    }
    
    /**
     * Method contains all of the event code when a buttons is clicked
     * @param e 
     */
    public void eventCode(ActionEvent e) {
        try {
            File f = new File("src\\myBOokCatalogue\\records.dat");
            raf = new RandomAccessFile(f, "rw");
            if (e.getSource() == btnSave || e.getSource() == btnUpdate) {
                if (txtTitle.getText().equals("")) {
                    titleError = new Alert(Alert.AlertType.ERROR);
                    titleError.setTitle("Data Entry Error");
                    titleError.setHeaderText("Invalid Value Entered");
                    titleError.setContentText("Title cannot be blank.");
                    titleError.showAndWait();
                    txtTitle.requestFocus();
                } else if (txtAuthor.getText().equals("")) {
                    authorError = new Alert(Alert.AlertType.ERROR);
                    authorError.setTitle("Data Entry Error");
                    authorError.setHeaderText("Invalid Value Entered");
                    authorError.setContentText("Author cannot be blank.");
                    authorError.showAndWait();
                    txtAuthor.requestFocus();
                } else if (txtISBN.getText().equals("")) {
                    isbnError = new Alert(Alert.AlertType.ERROR);
                    isbnError.setTitle("Data Entry Error");
                    isbnError.setHeaderText("Invalid Value Entered");
                    isbnError.setContentText("ISBN cannot be blank.");
                    isbnError.showAndWait();
                    txtISBN.requestFocus();
                } else if (((String)cmbGenre.getValue()).equals("Select Genre")){
                    genreError = new Alert(Alert.AlertType.ERROR);
                    genreError.setTitle("Data Entry Error");
                    genreError.setHeaderText("Invalid Value Entered");
                    genreError.setContentText("Please select a genre.");
                    genreError.showAndWait();
                    cmbGenre.requestFocus();
                } else if (((String)cmbFormat.getValue()).equals("Select Format")){
                    formatError = new Alert(Alert.AlertType.ERROR);
                    formatError.setTitle("Data Entry Error");
                    formatError.setHeaderText("Invalid Value Entered");
                    formatError.setContentText("Please select a format.");
                    formatError.showAndWait();
                    cmbFormat.requestFocus();
                } else if (validPages() == false) {
                    pagesError = new Alert(Alert.AlertType.ERROR);
                    pagesError.setTitle("Data Entry Error");
                    pagesError.setHeaderText("Invalid Value Entered");
                    pagesError.setContentText("# of pages needs to be a positive integer.");
                    pagesError.showAndWait();
                    txtPages.requestFocus();
                } else if (validPrice() == false) {
                    priceError = new Alert(Alert.AlertType.ERROR);
                    priceError.setTitle("Data Entry Error");
                    priceError.setHeaderText("Invalid Value Entered");
                    priceError.setContentText("Price needs to be a positive number.");
                    priceError.showAndWait();
                    txtPrice.requestFocus();
                } else {
                    if (e.getSource() == btnSave) {
                        saveConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                        saveConfirm.setTitle("Save Record");
                        saveConfirm.setHeaderText(null);
                        saveConfirm.setContentText("Do you want to save this new record?");
                        Optional<ButtonType> result = saveConfirm.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            String title = txtTitle.getText();
                            String author = txtAuthor.getText();
                            String isbn = txtISBN.getText();
                            String genre = (String)cmbGenre.getValue();
                            String format = (String)cmbFormat.getValue();
                            int pages = Integer.parseInt(txtPages.getText());
                            double price = Double.parseDouble(txtPrice.getText());

                            Book b = new Book(title, author, isbn, genre, format, pages, price);
                            this.bc.add(b);
                            recordsList = FXCollections.observableArrayList(this.bc);
                            allRecords.setItems(recordsList);

                            raf.seek(raf.length());
                            title = prepStringField(title, TITLE_SIZE);
                            raf.writeChars(title);
                            author = prepStringField(author, AUTHOR_SIZE);
                            raf.writeChars(author);
                            isbn = prepStringField(isbn, ISBN_SIZE);
                            raf.writeChars(isbn);
                            genre = prepStringField(genre, GENRE_SIZE);
                            raf.writeChars(genre);
                            format = prepStringField(format, FORMAT_SIZE);
                            raf.writeChars(format);
                            raf.writeInt(pages);
                            raf.writeDouble(price);
                        }
                    } else {
                        updateConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                        updateConfirm.setTitle("Update Record");
                        updateConfirm.setHeaderText(null);
                        updateConfirm.setContentText("Do you want to update this record?");
                        Optional<ButtonType> result = updateConfirm.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            String title = txtTitle.getText();
                            String author = txtAuthor.getText();
                            String isbn = txtISBN.getText();
                            String genre = (String)cmbGenre.getValue();
                            String format = (String)cmbFormat.getValue();
                            int pages = Integer.parseInt(txtPages.getText());
                            double price = Double.parseDouble(txtPrice.getText());
                            
                            int index = allRecords.getSelectionModel().selectedIndexProperty().get();
                            Book book = (Book)this.bc.remove(index);
                            recordsList = FXCollections.observableArrayList(this.bc);
                            allRecords.setItems(recordsList);
                            
                   
                            Book b = new Book(title, author, isbn, genre, format, pages, price);
                            
                            this.bc.add(index, b);
                            recordsList = FXCollections.observableArrayList(this.bc);
                            allRecords.setItems(recordsList);
                            
                            raf.seek(RECORD_SIZE * index);
                            title = prepStringField(title, TITLE_SIZE);
                            raf.writeChars(title);
                            author = prepStringField(author, AUTHOR_SIZE);
                            raf.writeChars(author);
                            isbn = prepStringField(isbn, ISBN_SIZE);
                            raf.writeChars(isbn);
                            genre = prepStringField(genre, GENRE_SIZE);
                            raf.writeChars(genre);
                            format = prepStringField(format, FORMAT_SIZE);
                            raf.writeChars(format);
                            raf.writeInt(pages);
                            raf.writeDouble(price);
                        }
                    }
                }
            } else if (e.getSource() == btnSearch) {
                ArrayList<String> choices = new ArrayList<>();
                choices.add("Title");
                choices.add("Author");

                ChoiceDialog<String> searchOptions = new ChoiceDialog<>("Select", choices);
                searchOptions.setTitle("Search Options");
                searchOptions.setHeaderText("You have a few different search options");
                searchOptions.setContentText("Select a search option:");

                Optional<String> searchChoice = searchOptions.showAndWait();
                if (searchChoice.isPresent()){
                    System.out.println("Your choice: " + searchChoice.get());
                    if (searchChoice.get().equals("Title")) {
                        TextInputDialog searchTitle = new TextInputDialog("");
                        searchTitle.setTitle("Search by Title");
                        searchTitle.setHeaderText("");
                        searchTitle.setContentText("Please enter a title: ");
                        Optional<String> searchTitleChoice = searchTitle.showAndWait();
                        
                        if (searchTitleChoice.isPresent()){
                            Alert titleSearchAlert = new Alert(Alert.AlertType.INFORMATION);
                            titleSearchAlert.setTitle("Search Results");
                            titleSearchAlert.setHeaderText("");
                            titleSearchAlert.setContentText("You own " + 
                                    bc.searchTitle(searchTitleChoice.get()) + 
                                    " book with that title!");
                            titleSearchAlert.showAndWait();
                        }
                        
                    } else if (searchChoice.get().equals("Author")) {
                        TextInputDialog searchAuthor = new TextInputDialog("");
                        searchAuthor.setTitle("Search by Author");
                        searchAuthor.setHeaderText("");
                        searchAuthor.setContentText("Please enter an author: ");
                        Optional<String> searchTitleChoice = searchAuthor.showAndWait();
                        
                        if (searchTitleChoice.isPresent()){
                            Alert authorSearchAlert = new Alert(Alert.AlertType.INFORMATION);
                            authorSearchAlert.setTitle("Search Results");
                            authorSearchAlert.setHeaderText("");
                            authorSearchAlert.setContentText("You own " + 
                                    bc.searchAuthor(searchTitleChoice.get()) + 
                                    " book(s) by that author!");
                            authorSearchAlert.showAndWait();
                        }
                    }
                }
            } else if (e.getSource() == btnDelete) {
                deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                deleteConfirm.setTitle("Delete Record");
                deleteConfirm.setHeaderText(null);
                deleteConfirm.setContentText("Are you sure you want to delete this record?");
                Optional<ButtonType> result = deleteConfirm.showAndWait();
                if (result.get() == ButtonType.OK) {

                    int index = allRecords.getSelectionModel().selectedIndexProperty().get();
                    Book book = (Book)this.bc.remove(index);
                    recordsList = FXCollections.observableArrayList(bc);
                    allRecords.setItems(recordsList);
                    
                    raf.seek(RECORD_SIZE * index);
                    raf.setLength(RECORD_SIZE * bc.size());
                    for (int i = index ; i < bc.size(); i++) {

                        String title = bc.get(i).getTitle();
                        title = prepStringField(title, TITLE_SIZE);
                        raf.writeChars(title);
                        String author = bc.get(i).getAuthor();
                        author = prepStringField(author, AUTHOR_SIZE);
                        raf.writeChars(author);
                        String isbn = bc.get(i).getIsbn();
                        isbn = prepStringField(isbn, ISBN_SIZE);
                        raf.writeChars(isbn);
                        String genre = bc.get(i).getGenre();
                        genre = prepStringField(genre, GENRE_SIZE);
                        raf.writeChars(genre);
                        String format = bc.get(i).getFormat();
                        format = prepStringField(format, FORMAT_SIZE);
                        raf.writeChars(format);
                        int pages = bc.get(i).getPages();
                        raf.writeInt(pages);
                        double price = bc.get(i).getPrice();
                        raf.writeDouble(price);      
                        
                    }
                 
                }
            } else if (e.getSource() == btnReset) {        
                txtTitle.setText("");
                txtAuthor.setText("");
                txtISBN.setText("");
                cmbGenre.setValue("Select Genre");
                cmbFormat.setValue("Select Format");
                txtPages.setText("");
                txtPrice.setText("");
            } else {
                System.exit(0);
            }
            
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Methods return true if a valid # of pages is entered
     * @return boolean 
     */
    public boolean validPages() {
        try {
            int numPages = Integer.parseInt(txtPages.getText());
            if (numPages < 1) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    /**
     * Method returns true if a valid price is entered
     * @return boolean true if valid
     */
    public boolean validPrice() {
        try {
            double price = Double.parseDouble(txtPrice.getText());
            if (price < 0) {
                return false;
            }
            
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    
}
