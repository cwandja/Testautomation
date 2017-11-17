package de.funke.qa;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;



public class PageTest extends BaseTest {
    //Homepage, Rubrik, Artikel
    @Test(groups = {"responsive", "cross-browser", "smoke"})
    public void verifyDesignLayoutPage() {
        String url = Configuration.baseUrl;
        url = url.replace(HTTP_PROTOCOL, HTTPS_PROTOCOL);
           validateHtml(url);
        //validateCss("https://www.abendblatt.de/resources/1507120781/css/styles.min.css");
        //validateCss(url);
    }

    @Test(groups = {"access", "smoke"})
    public void verifyBrokenLinks() {

    }


}
