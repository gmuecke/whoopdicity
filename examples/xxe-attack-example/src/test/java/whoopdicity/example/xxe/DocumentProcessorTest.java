package whoopdicity.example.xxe;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXParseException;

/**
 * Created by gerald on 20.05.15.
 */
public class DocumentProcessorTest {


    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testProcessDocument_vulnerable_secretRevealed() throws Exception {
        //prepare
        //the attacked file containing the secret
        final File attackedFile = prepareAttackedFile();
        final File maliciousFile = prepareMaliciousFile(attackedFile);

        String secretContent = DocumentProcessor.evaluateVulnerable(maliciousFile.toURI().toURL(),
                                                                    "//document/property");

        //assert
        //the content from the attacked file is put into the attacking xml where it resolves to the hostname
        assertEquals("secretContent", secretContent);
    }

    @Test(expected = SAXParseException.class)
    public void testProcessDocument_dtdDisabled_exception() throws Exception {
        //prepare
        //the attacked file containing the secret
        final File attackedFile = prepareAttackedFile();
        final File maliciousFile = prepareMaliciousFile(attackedFile);

        String secretContent = DocumentProcessor.evaluateNoDTD(maliciousFile.toURI().toURL(), "//document/property");

        //assert
        //the content from the attacked file is put into the attacking xml where it resolves to the hostname
        assertEquals("secretContent", secretContent);
    }

    /**
     * Creates a temporary file containing a secret
     * @return
     *  the handle of the file.
     * @throws IOException
     */
    private File prepareAttackedFile() throws IOException {

        final File attackedFile = folder.newFile("attackedFile.txt");
        try (FileOutputStream fos = new FileOutputStream(attackedFile)) {
            IOUtils.write("secretContent", fos);
        }
        return attackedFile;
    }

    /**
     * Creates a temporary file containing an external entity definition pointing to the attacked filed.
     * In this test scenario, the file is a temporary file. In a real-world attack, it would be the location
     * of a well known file containing a secret, like /etc/passwd
     * @param attackedFile
     *  the target file whose content should be read
     * @return
     *  the handle of the malicious file
     * @throws IOException
     */
    private File prepareMaliciousFile(final File attackedFile) throws IOException {

        //as attacker file we use a template and replacing a %s placeholder with the url of the attacked file
        //in a real-world attack we would use a valuable target such as /etc/passwd
        final File attackerFile = folder.newFile("attackerFile.xml");

        //load the template file from the classpath

        try (InputStream is = DocumentProcessorTest.class.getResourceAsStream("/malicious_template.xml");
             FileOutputStream fos = new FileOutputStream(attackerFile)) {

            final String attackerContent = prepareMaliciousContent(is, attackedFile);
            IOUtils.write(attackerContent, fos);
        }
        return attackerFile;
    }

    /**
     * Read the content from the input stream and replace the placeholder in the template with the
     * URL of the attacked file
     * @param templateInputStream
     * @param attackedFile
     * @return
     * @throws IOException
     */
    private String prepareMaliciousContent(final InputStream templateInputStream, final File attackedFile)
            throws IOException {

        final StringWriter writer = new StringWriter();
        IOUtils.copy(templateInputStream, writer, Charset.defaultCharset());
        return String.format(writer.toString(), attackedFile.toURI().toURL());
    }

}