package peaksoft.enums;

public enum Genre {

    ACTION("Action"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    HORROR("Horror"),
    HISTORY("History"),
    ANIME("Anime"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    ROMANCE("Romance");
    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.displayName.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        return null;
    }
}
