package de.funke.qa;

import org.testng.annotations.BeforeSuite;
import com.codeborne.selenide.Configuration;

public class Base {
    public static final String HTTP_PROTOCOL = "http://";
    public static final String HTTPS_PROTOCOL = "https://";

    protected String getBaseUrl() {
        String publication = System.getProperty("publication", "hao");
        String stage = System.getProperty("stage", "prod");
        String baseUrl=null;
        String prefix = "http://";
        String suffix = ".de";
        String domain = Publications.valueOf(publication.toUpperCase().replace("-", "_")).getDomain();
        switch (Stage.valueOf(stage.toUpperCase())) {
            case UAT:
                baseUrl = prefix+"uat."+domain+suffix;
                break;
            case PROD:
            default:
                baseUrl = prefix+"www."+domain+suffix;
                break;
        }
        if (baseUrl == null) {
            throw new IllegalStateException("no mapping for publication=" + publication + ", stage=" + stage);
        }
        return baseUrl;
    }
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        Configuration.baseUrl = getBaseUrl();
        Configuration.browser = System.getProperty("selenide.browser", "chrome");
    }
}
