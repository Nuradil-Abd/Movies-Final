package peaksoft.enums;

public enum Country {
    KYRGYZSTAN("KGZ"),
    UNITED_STATES("US"),
    CANADA("CA"),
    UNITED_KINGDOM("GB"),
    FRANCE("FR"),
    GERMANY("DE"),
    ITALY("IT"),
    SPAIN("ES"),
    AUSTRALIA("AU"),
    NEW_ZEALAND("NZ"),
    CHINA("CN"),
    JAPAN("JP"),
    SOUTH_KOREA("KR"),
    INDIA("IN"),
    BRAZIL("BR"),
    ARGENTINA("AR"),
    MEXICO("MX"),
    SOUTH_AFRICA("ZA"),
    RUSSIA("RU"),
    NETHERLANDS("NL"),
    BELGIUM("BE"),
    SWITZERLAND("CH"),
    SWEDEN("SE"),
    NORWAY("NO"),
    DENMARK("DK"),
    FINLAND("FI");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public static Country fromString(String text) {
        for (Country country : Country.values()) {
            if (country.code.equalsIgnoreCase(text)) {
                return country;
            }
        }
        return null;
    }
}
