package de.kodestruktor.grief.core.tag;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Exposes all constants of a given class to the {@link ServletContext} and thus making them usable in JSPs.
 *
 * @author Christoph Wende
 */
public class Constants extends RequestContextAwareTag {

  private static final Logger LOG = LoggerFactory.getLogger(Constants.class);

  private static final long serialVersionUID = 5507720997675245155L;

  private String className;

  private String var;

  @Override
  public int doEndTag() throws JspException {

    if (StringUtils.isNoneBlank(this.className, this.var)) {

      try {
        final Map<String, Object> constants = new HashMap<>();
        final Class<?> declaringClass = Class.forName(this.className);
        final Field[] fields = declaringClass.getFields();

        for (final Field field : fields) {
          constants.put(field.getName(), field.get(null));
        }

        this.getRequestContext().getWebApplicationContext().getServletContext().setAttribute(this.var, constants);
      } catch (final Exception e) {
        LOG.error("Could not add contstants for class [{}]", this.className);
        throw new JspException("Exception setting constants", e);
      }

    } else {
      LOG.error("No class and/or variable name passed; omitting contant tag");
    }
    return super.doEndTag();
  }

  @Override
  protected int doStartTagInternal() throws Exception {
    return 0;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public String getVar() {
    return this.var;
  }

  public void setVar(final String var) {
    this.var = var;
  }

}
