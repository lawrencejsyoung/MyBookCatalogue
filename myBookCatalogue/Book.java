
package myBookCatalogue;
/**
 * Class models a book that includes fields for title, author, ISBN, genre
 * format, pages, and price.
 * @author Lawrence Young
 */
public class Book {

    //Fields
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private String format;
    private int pages;
    private double price;

    /**
     * Constructor to create a book
     * @param title Title of the book
     * @param author AUthor of book
     * @param isbn ISBN for the book
     * @param genre Book genre
     * @param format Book format
     * @param pages Number of pages in the book
     * @param price Purchase price of the book
     */
    public Book(String title, String author, String isbn, String genre, String format, int pages, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.format = format;
        this.pages = pages;
        this.price = price;
    }
    
    /**
     * Accessor method that retrieves the book title
     * @return title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Accessor method that retrieves the book's author
     * @return author
     */
    public String getAuthor() {
        return author;
    }
    
    /**
    * Accessor method that retrieves the book's isbn
    * @return isbn
    */
    public String getIsbn() {
        return isbn;
    }
    
    /**
    * Accessor method that retrieves the book's genre
    * @return genre
    */
    public String getGenre() {
        return genre;
    }
    
    /**
    * Accessor method that retrieves the book format
    * @return format
    */
    public String getFormat() {
        return format;
    }
    
    /**
    * Accessor method that retrieves the number of pages in the book
    * @return int - number of pages in the book
    */
    public int getPages() {
        return pages;
    }
    
    /**
    * Accessor method that retrieves the book's purchase price
    * @return double price
    */
    public double getPrice() {
        return price;
    }
    
    /**
     * Mutator method that is used to edit the book title
     * @param title - title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Mutator method that is used to edit the book author
     * @param author - author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * Mutator method that is used to edit the book isbn
     * @param isbn - book's isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    /**
     * Mutator method that is used to edit the book genre
     * @param genre - book's genre category
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Mutator method used to edit the book's format
     * @param format - the format of the book (ie. eBook, Paperback
     */
    public void setFormat(String format) {
        this.format = format;
    }
    
    /**
     * Mutator method used to edit the number of pages in a book
     * @param pages - number of pages in the book
     */
    public void setNumPages(int pages) {
        this.pages = pages;
    }
    
    /**
     * Mutator method used to edit the price of the book
     * @param price - price of the book
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns of string containing the book's details
     * @return String 0 a string printout of the book details
     */
    @Override
    public String toString() {
        String bookInfo;
        bookInfo = String.format("Title: %s "
                + "\nAuthor: %s"
                + "\nISBN: %s"
                + "\nGenre: %s"
                + "\nFormat: %s"
                + "\nPages: %s"
                + "\nPrice: %s", title, author, isbn, genre, format, pages, price);
        return bookInfo;
    }
    
    
}