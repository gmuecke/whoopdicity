package whoopdicity.example.xxe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import sun.misc.IOUtils;

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
    public void testProcessDocumentVulnerable_secretRevealed() throws Exception {
        //prepare
        //the attacked file containing the secret
        final File attackedFile = folder.newFile("attackedFile.txt");
        try (FileOutputStream fos = new FileOutputStream(attackedFile)) {
            IOUtils.write("secretContent", fos);
        }
        //as attacker file we use a template and replacing a %s placeholder with the url of the attacked file
        //in a real-world attack we would use a valuable target such as /etc/passwd
        final File attackerFile = folder.newFile("attackerFile.xml");

        //load the template file from the classpath
        try (InputStream is = getClass().getResourceAsStream("RemoteContentRepositoryTest_attacker.xml");
             FileOutputStream fos = new FileOutputStream(attackerFile)) {

            final String attackerContent = prepareAttackerContent(is, attackedFile);
            IOUtils.write(attackerContent, fos);
        }

        System.setProperty("arquillian.launch", "testContainer");
        subject.onArquillianHost(attackerFile.toURI().toURL());
        subject.setupManually();

        //act
        subject.before();

        //assert
        //the content from the attacked file is put into the attacking xml where it resolves to the hostname
        assertEquals("secretContent", subject.getRemoteHost());
    }

    private String prepareAttackerContent(final InputStream templateInputStream, final File attackedFile)
            throws IOException {

        final StringWriter writer = new StringWriter();
        IOUtils.copy(templateInputStream, writer, Charset.defaultCharset());
        return String.format(writer.toString(), attackedFile.toURI().toURL());
    }

}