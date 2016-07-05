package myBookCatalogue;

/**
 * This is an enumeration that contains all of the available genre values
 * @author Lawrence Young
 */
public enum Genres {
    ACTION_ADVENTURE ("Action Adventure"),
    ANTHOLOGY ("Anthology"),
    ART ("Art"),
    AUTOBIOGRAPHIES ("Autobiographies"),
    BIOGRAPHIES ("Biographies"),
    BUSINESS ("Business"),
    CHIDRENS ("Children's"),
    COMICS ("Comics"),
    COOKBOOKS ("Cookbooks"),
    DIARIES ("Diaries"),
    DICTIONARIES ("Dictionaries"),
    DRAMA ("Drama"),
    ENCYCLOPEDIA ("Encyclopedia"),
    FANTASY ("Fantasy"),
    GUIDE ("Guide"),
    HEALTH ("Health"),
    HISTORY ("History"),
    HORROR ("Horror"),
    SELF_HELP ("Self Help"),
    JOURNALS ("Journals"),
    MATH ("Math"),
    MYSTERY ("Mystery"),
    POETRY ("Poetry"),
    PRAYER_BOOKS ("Prayer Books"),
    SPIRTUALITY ("Spirituality"),
    ROMANCE ("Romance"),
    SATIRE ("Satire"),
    SCIENCE ("Science"),
    SCIENCE_FICTION ("Science Fiction"),
    TRAVEL ("Travel");
    
    // Field for genre name
    private final String genreName;
    
    /**
     * Accessor method to retrieve the genre name
     * @return genre name
     */
    public String getGenreName() {
        return genreName;
    }
    
    private Genres(String genreName) {
        this.genreName = genreName;
    }
}
