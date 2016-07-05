package myBookCatalogue;

/**
 * This is an enumeration that contains all of the available format values
 * @author Lawrence Young
 */
public enum Formats {
    EBOOK ("eBook"),
    HARDCOVER ("Hardcover"),
    PAPERBACK ("Paperback");
    
    // Field for the format name
    private final String formatName;
    
    /**
     * Accessor method for retrieving the format name
     * @return format name
     */
    public String getFormatName() {
        return formatName;
    }

    private Formats(String formatName) {
        this.formatName = formatName;
    }
}
