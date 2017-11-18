package de.funke.qa;

public enum Publication {
    HAO("hao", "abendblatt", true),
    BZV_BZ("bzv-bz", "braunschweiger-zeitung", false),
    NRW_WAZ("nrw-waz", "waz", false);


    private final String name;
    private final String domain;
    private final boolean isHttpsForced;

    Publication(String name, String domain, boolean isHttpsForced) {
        this.name = name;
        this.domain = domain;
        this.isHttpsForced = isHttpsForced;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public boolean isHttpsForced() {
        return isHttpsForced;
    }
}
