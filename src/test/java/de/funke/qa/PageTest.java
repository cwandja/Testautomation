package de.funke.qa;

import org.testng.annotations.Test;

public class PageTest extends BaseTest {
    //Homepage, Rubrik, Artikel
    @Test(groups = {"access", "content-type", "responsive", "design-layout", "smoke"})
    public void verifyDesignLayoutPage() {
           validateHtml("https://www.morgenpost.de/");
        //w3cValidateHTML("https://www.morgenpost.de/");

    }

    @Test(groups = {"access", "smoke"})
    public void verifyBrokenLinks() {

    }


}
