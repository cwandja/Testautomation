package de.funke.qa;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.Test;



public class PageTest extends BaseTest {
    //ausgewählte Seiten, die soviel Variante wie möglich abdeckt - alle publikationen
    @Test(groups = {"responsive", "cross-browser", "smoke"})
    public void verifyHtmlCssCode() {
           validateHtmlCss(Configuration.baseUrl);
    }

    //Homepage, Rubrik, Artikel  - alle publikationen
    @Test(groups = {"content-type"})
    public void verifyContentType() {
        validateHtmlCss(Configuration.baseUrl);
    }


}
