package de.funke.qa;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;



public class PageTest extends BaseTest {
    //Homepage, Rubrik, Artikel
    @Test(groups = {"access", "content-type", "responsive", "design-layout", "smoke"})
    public void verifyDesignLayoutPage() {
        String url = Configuration.baseUrl;
        url = url.replace(HTTP_PROTOCOL, HTTPS_PROTOCOL);
           validateHtml(url);
        //w3cValidateHTML("https://www.morgenpost.de/");

    }

    @Test(groups = {"access", "smoke"})
    public void verifyBrokenLinks() {

    }


}
