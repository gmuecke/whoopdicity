package whoopdicity.examples.xpath;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

/**
 * Created by gerald on 20.05.15.
 */
public class XPathInjectionExampleTest {

    private InputSource source;

    private String validUserId;
    private String maliciousUserId;

    @Before
    public void setUp() throws Exception {
        this.source = new InputSource(XPathInjectionExample.class.getResourceAsStream("technicalUsers.xml"));
        this.validUserId = "reader";
        this.maliciousUserId = "reader']/../user[@id='writer";
    }

    @Test
    public void testGetPrivateKeySanitized_validUserId_correctPrivateKey() throws Exception {
        String privateKey = XPathInjectionExample.getPrivateKeySanitized(source, validUserId);
        assertEquals("Incorrect private key", "ABC", privateKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPrivateKeySanitized_invalidUserId_exception() throws Exception {
        String privateKey = XPathInjectionExample.getPrivateKeySanitized(source, maliciousUserId);
        assertEquals("Incorrect private key", "123", privateKey);
    }

    @Test
    public void testGetPrivateKeyVulnerable_validUserId_correctPrivateKey() throws Exception {
        String privateKey = XPathInjectionExample.getPrivateKeyVulnerable(source, validUserId);
        assertEquals("Incorrect private key", "ABC", privateKey);
    }

    @Test
    public void testGetPrivateKeyVulnerable_invalidUserId_successfulAttack() throws Exception {
        String privateKey = XPathInjectionExample.getPrivateKeyVulnerable(source, maliciousUserId);
        assertEquals("Attack failed", "123", privateKey);
    }
}