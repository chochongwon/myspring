package myspring.common.config;

import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import myspring.common.util.DateUtil;
import myspring.common.util.template.AttributeRenderer;
import myspring.common.util.template.StringTemplate;

/**
 * @author ddam40
 *
 */
public class SystemConfigTemplateApplyer implements StringTemplateApplyer, InitializingBean {

    private Properties properties;

    private StringTemplate stringTemplate;
    
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    public void setStringTemplate(StringTemplate stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(stringTemplate == null) {
            stringTemplate = new StringTemplate();
        }
        
        if(properties != null) {
            stringTemplate.setAttribute(properties);
        }

        stringTemplate.registerRenderer(Date.class, new DateRenderer());
    }
    
    @Override
    public String applyTemplate(String src) {
        stringTemplate.setAttribute("system.date",  DateUtil.getDate());
        stringTemplate.setAttribute("system.today", DateUtil.getDate());
        stringTemplate.setAttribute("system.yesterday", DateUtil.addDays(DateUtil.getDate(), -1));
        stringTemplate.setAttribute("system.tomorrow",  DateUtil.addDays(DateUtil.getDate(),  1));
        
        return stringTemplate.applyTemplate(src);
    }

    private static class DateRenderer implements AttributeRenderer {
        public String toString(Object o, String format) {
            return (StringUtils.isBlank(format)) ? DateUtil.format((Date)o) : DateUtil.format((Date)o, format);
        }
    }
}
