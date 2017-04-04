package myspring.common.util.template;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Simple String Template Engine  
 * @author 
 */
public class StringTemplate {

    private static final String EMPTY_STRING = "";

    private static final String PLACEHOLDER_PREFIX = "${";

    private static final String PLACEHOLDER_SUFFIX = "}";
    
    private static final String expression = "(\\$\\{[a-zA-Z_0-9. ()]*\\})";
    
    private static final Pattern pattern = Pattern.compile(expression);
    
    private static final String FORMAT_PREFIX = "(";
    
    private static final String FORMAT_SUFFIX = ")";
    
    private Map<String, Object> attributes;
    
    private Map<Class, AttributeRenderer> attributeRenderers;
    
    public StringTemplate() {
        attributes = new HashMap<String, Object>();
    }
    
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
    
    public void setAttribute(Map attributes) {
        this.attributes.putAll(attributes);
    }
    
    public void registerRenderer(Class clazz, AttributeRenderer attributeRenderer) {
        if(attributeRenderers == null) {
            attributeRenderers = new HashMap<Class, AttributeRenderer>();       
        }
        attributeRenderers.put(clazz, attributeRenderer);
    }

    private Object renderAttribute(Object value, String optionFormat) {
        if(attributeRenderers == null || !attributeRenderers.containsKey(value.getClass())) {
            return value;
        }
        return attributeRenderers.get(value.getClass()).toString(value, optionFormat);
    }

    private String getReplaceString(String placeHolder, String optionFormat) {
        Object value = attributes.get(placeHolder);
        if(value == null) {
            value = System.getProperties().get(placeHolder);
        }
        if(value != null) {
            value = renderAttribute(value, optionFormat); 
        }
        return (value != null) ? value.toString() : EMPTY_STRING; 
    }
    
    public String applyTemplate(String src) {
        StringBuffer sb = new StringBuffer();
        Matcher matcher = pattern.matcher(src);
        while(matcher.find()) {
            String placeHolder = matcher.group();
            placeHolder = StringUtils.substring(placeHolder, PLACEHOLDER_PREFIX.length(), placeHolder.length() - PLACEHOLDER_SUFFIX.length());

            String optionFormat = null;
            if(StringUtils.contains(placeHolder, FORMAT_PREFIX) && StringUtils.contains(placeHolder, FORMAT_SUFFIX)) {
                optionFormat = StringUtils.substring(placeHolder, placeHolder.indexOf(FORMAT_PREFIX) + 1,  placeHolder.indexOf(FORMAT_SUFFIX));
                placeHolder = StringUtils.substring(placeHolder, 0, placeHolder.indexOf(FORMAT_PREFIX));
            }
            String replaceString = getReplaceString(placeHolder, optionFormat);
            matcher.appendReplacement(sb, replaceString);
        } 
        matcher.appendTail(sb);
        
        return sb.toString();
    }

}
