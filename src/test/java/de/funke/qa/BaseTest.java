package de.funke.qa;

import com.jcabi.w3c.Defect;
import com.jcabi.w3c.ValidationResponse;
import com.jcabi.w3c.ValidatorBuilder;
import nu.validator.messages.MessageEmitter;
import nu.validator.messages.MessageEmitterAdapter;
import nu.validator.messages.TextMessageEmitter;
import nu.validator.servlet.imagereview.ImageCollector;
import nu.validator.source.SourceCode;
import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.xml.SystemErrErrorHandler;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.Assert;
import org.testng.SkipException;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BaseTest extends Base{
    static final Logger logger = Logger.getLogger(BaseTest.class);
    private int numOfError;

    public String getContentFile(String urlToRead) {
        String line;
        String result = "";
        // create the url
        URL url = null;
        try {
            url = new URL(urlToRead);

            // open the url stream, wrap it an a few "readers"
            BufferedReader reader = null;

            reader = new BufferedReader(new InputStreamReader(url.openStream()));


            // write the output to stdout
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }

            // close our reader
            reader.close();
        } catch (IOException e) {
            throw new SkipException("Skipping - There was an exception: please look at your code: " + e.toString());
        }
        return result;
    }
public String getCssLink(String htmlUrl) {
    Document document = null;
    try {
        document = Jsoup.connect(htmlUrl).get();
    } catch (IOException e) {
        e.printStackTrace();
    }
    Elements links = document.select("link");
    String cssUrl = "";
    for (Element link : links) {
        cssUrl = link.absUrl("href");
    }
    return cssUrl;
}
    public void validateCss(String url) {
        String cssContent = getContentFile(url);
        //String css = "body { font-family: Arial; }";
        ValidationResponse response =
                null;
        try {
            response = new ValidatorBuilder().css().validate(cssContent);
            Set<Defect> defects = response.errors();
            if(defects.size()>0) {
                List<String> errors = new ArrayList<>();
                for (Defect defect : defects) {
                    errors.add("\nError: [" + defect.line() + "], "  + defect.message());
                }
                Assert.fail(errors.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response.valid();
    }

    public void validateHtml(String url) {
        String htmlContent = getContentFile(url);
        // String htmlContent = "<!-- b2 -->";
        /*String htmlContent = "<html>\n" +
                "<head>\n" +
                "  <title>This is bad HTML</title>\n" +
                "\n" +
                "<body>\n" +
                "  <h1>Bad HTML\n" +
                "  <p>This is a paragraph\n" +
                "</body>";*/
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            InputStream in = new ByteArrayInputStream(htmlContent.getBytes("UTF-8"));
            SourceCode sourceCode = new SourceCode();
            ImageCollector imageCollector = new ImageCollector(sourceCode);
            boolean showSource = false;
            MessageEmitter emitter = new TextMessageEmitter(out, false);
            MessageEmitterAdapter errorHandler = new MessageEmitterAdapter(sourceCode, showSource, imageCollector, 0, false, emitter);
            errorHandler.setErrorsOnly(true);
            errorHandler.setLoggingOk(true);

            SimpleDocumentValidator validator = new SimpleDocumentValidator();
            validator.setUpMainSchema("http://s.validator.nu/html5-rdfalite.rnc", new SystemErrErrorHandler());
            validator.setUpValidatorAndParsers(errorHandler, true, false);
            validator.checkHtmlInputSource(new InputSource(in));
            int numOfFatalError = errorHandler.getFatalErrors();
            int numOfError = errorHandler.getErrors();
            errorHandler.end("", "");
            if (0 != numOfFatalError) {
                Assert.fail("\nFatal Error present\n " + out.toString());
            }
            if (0 != numOfError) {
                String[] errors = out.toString().split("Error:");
                Assert.fail("\n " + out.toString());
            }
        } catch (Exception e) {
            throw new SkipException("Skipping - There was an exception: please look at your code: " + e.toString());
        }
    }

}
