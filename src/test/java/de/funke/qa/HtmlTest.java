package de.funke.qa;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HtmlTest extends Base{
    @Test(groups = {"design-layout", "smoke"})
    public void verifyHtmlPage() {
           // Assert.assertTrue(validateHtml("https://www.morgenpost.de/"));
        w3cValidateHTML("https://www.morgenpost.de/");

    }
}
