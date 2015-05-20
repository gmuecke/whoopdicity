package whoopdicity.examples.xpath;


import javax.xml.namespace.QName;
import javax.xml.xpath.XPathVariableResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Simple sanitizing variable resolver, allowing to define a set of regex pattern against which
 * values are checked.
 * Created by gerald on 20.05.15.
 */
public class SanitizingVariableResolver implements XPathVariableResolver {

    private Map<QName, String> variables = new HashMap<>();

    private final List<Pattern> validationPatterns;

    public SanitizingVariableResolver(String... regexPatterns){

        this.validationPatterns = new ArrayList<>();
        for(String regexPattern : regexPatterns) {
            this.validationPatterns.add(Pattern.compile(regexPattern));
        }
    }

    public void addVariable(String name, String value) {
        for(Pattern pattern : validationPatterns){
            if(pattern.matcher(value).matches()){
                variables.put(new QName(name), value);
                return;
            }
        }
        throw new IllegalArgumentException("The value '" + value + "' is not allowed for a variable" );

    }

    @Override
    public Object resolveVariable(QName variableName) {
        return this.variables.get(variableName);
    }
}
