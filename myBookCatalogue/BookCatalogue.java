package myBookCatalogue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Class models a collection of books. THe class contains methods for searching
 * and populating a book catalogue
 * @author Lawrence Young
 */
public class BookCatalogue extends ArrayList<Book> {
    
    private RandomAccessFile raf;
    public int TITLE_SIZE = 50;
    public int AUTHOR_SIZE = 50;
    public int ISBN_SIZE = 13;
    public int GENRE_SIZE = 20;
    public int FORMAT_SIZE = 10;
    public int RECORD_SIZE = 298;
    
    public BookCatalogue() {
        super();
    }
    /**
     * Method searches through a book catalog by title for a book record
     * @param search - The String entered by a user in search box
     * @return results  - the number of books that contain that title search
     */
    public int searchTitle(String search) {
        int results = 0;
        for (Book book : this) {
            if (book.getTitle().contains(search)) {
                results++;
            }          
        }
        return results;
    }
    
    /**
     * Method searches through a book catalog by author for a book record
     * @param search - the string entered by a user in search box
     * @return results  - the number of books that contain that author search
     */
    public int searchAuthor(String search) {
        int results = 0;
        for (Book book : this) {
            if (book.getAuthor().contains(search)) {
                results++;
            }          
        }
        return results;
    }

    /**
     * Method is used to read each string field from the random access file
     * ensuring that the proper number of characters are read in
     * @param raf
     * @param size
     * @return the string read in from the random access file
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
     * Method reads in all of the records from the file into the book catalogue
     * for easy data manipulation while the book app is running.
     */
    public void getRecords() {
        long recNum;
        try {
            File f = new File("src\\myBookCatalogue\\records.dat");
            raf = new RandomAccessFile(f, "rw");
            
            recNum = raf.length() / RECORD_SIZE;
            raf.seek(0);
            for (int i = 0; i < recNum; i++) {
                String title = readString(raf, TITLE_SIZE);
                String author = readString(raf, AUTHOR_SIZE);
                String isbn = readString(raf, ISBN_SIZE);
                String genre = readString(raf, GENRE_SIZE);
                String format = readString(raf, FORMAT_SIZE);
                int pages = raf.readInt();
                double price = raf.readDouble();
                
                Book b = new Book(title, author, isbn, genre, format, pages, price);
                this.add(b);
            }
                    
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

