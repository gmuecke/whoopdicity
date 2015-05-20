package whoopdicity.examples.xpath;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

/**
 * Created by gerald on 20.05.15.
 */
public class XPathInjectionExample {

    public static void main(String[] args) throws Exception {


        final InputSource source = new InputSource(XPathInjectionExample.class.getResourceAsStream("technicalUsers.xml"));


        String userId = "reader";

        String privateKey = getPrivateKeyVulnerable(source, userId);
        System.out.println("private key: " + privateKey);


    }

    public static String getPrivateKeySanitized(InputSource source, String userId) throws XPathExpressionException {
        final XPath xp = XPathFactory.newInstance().newXPath();
        final SanitizingVariableResolver resolver = new SanitizingVariableResolver("[a-zA-Z0-9]{4,15}");
        resolver.addVariable("userId", userId);
        xp.setXPathVariableResolver(resolver);
        return xp.evaluate("//technical-users/user[@id=$userId]/privateKey",source);
    }

    public static String getPrivateKeyVulnerable(InputSource source, String userId) throws XPathExpressionException {
        final XPath xp = XPathFactory.newInstance().newXPath();
        return xp.evaluate("//technical-users/user[@id='"+userId+"']/privateKey",source);
    }

}
