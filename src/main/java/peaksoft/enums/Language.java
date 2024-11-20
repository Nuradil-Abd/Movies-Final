package peaksoft.enums;

public enum Language {
    KYRGYZ("Kyrgyz"),
    ENGLISH("English"),
    RUSSIAN("Russian"),
    FRENCH("French"),
    GERMAN("German"),;

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.displayName.equalsIgnoreCase(text)) {
                return language;
            }
        }
        return null;
    }

}
