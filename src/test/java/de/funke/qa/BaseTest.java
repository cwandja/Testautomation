package de.funke.qa;

import nu.validator.messages.MessageEmitter;
import nu.validator.messages.MessageEmitterAdapter;
import nu.validator.messages.TextMessageEmitter;
import nu.validator.servlet.imagereview.ImageCollector;
import nu.validator.source.SourceCode;
import nu.validator.validation.SimpleDocumentValidator;
import nu.validator.xml.SystemErrErrorHandler;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;

public class BaseTest extends Base{
    static final Logger logger = Logger.getLogger(BaseTest.class);
    private int numOfError;

    public String getHTML(String urlToRead) {
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

    public void validateHtml(String url) {
        String htmlContent = getHTML(url);
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
        int numOfError = -1;
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
            numOfError = errorHandler.getErrors();
            errorHandler.end("", "");
            if (0 != numOfError) {
                Assert.fail("\n " + out.toString());
            }
        } catch (Exception e) {
            throw new SkipException("Skipping - There was an exception: please look at your code: " + e.toString());
        }
    }

}
