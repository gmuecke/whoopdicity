package whoopdicity.example.xxe;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.URL;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Created by gerald on 20.05.15.
 */
public class DocumentProcessor {

    /**
     * Parses the document at the specified location and retrieves the value using the given xpath expression. The
     * implementation is vulnerable to the XXE attack
     * @param url
     * @param xpathExpression
     * @return
     */
    public static String evaluateVulnerable(final URL url, final String xpathExpression)
            throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {

        //parse the document
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final Document document = parse(url, factory);

        return evaluate(xpathExpression, document);
    }

    /**
     * Parses the document at the specified location and retrieves the value using the given xpath expression. The
     * document does not allow doctype declarations
     * @param url
     * @param xpathExpression
     * @return
     */
    public static String evaluateNoDTD(final URL url, final String xpathExpression)
            throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {

        //parse the document
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        final Document document = parse(url, factory);

        return evaluate(xpathExpression, document);
    }

    private static Document parse(final URL url, final DocumentBuilderFactory factory)
            throws SAXException, IOException, ParserConfigurationException {

        return factory.newDocumentBuilder().parse(url.openStream());
    }

    private static String evaluate(final String xpathExpression, final Document document)
            throws XPathExpressionException {

        return XPathFactory.newInstance().newXPath().evaluate(xpathExpression, document);
    }
}
