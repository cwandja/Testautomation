package de.funke.qa;

public enum Publications {
    HAO("hao", "abendblatt"),
    BZV_BZ("bzv-bz", "braunschweiger-zeitung"),
    NRW_WAZ("nrw-waz", "waz");


    private final String name;
    private final String domain;

    Publications(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
