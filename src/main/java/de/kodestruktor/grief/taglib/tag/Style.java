package de.kodestruktor.grief.taglib.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.taglib.property.GriefTaglibProperty;
import de.kodestruktor.grief.taglib.util.ConfigurationUtil;

/**
 * Taglib to create a revision dependent link tag.<br>
 * An input with the <code>uri</code> attribute <code>style.css</code> would i.e. output <br>
 * <br>
 * <code>&lt;link rel='stylesheet' type='text/css' href='/[rootPath]/resources/r[revNo]/presentation/[static|generated]/style.css' /&gt;</code><br>
 * <br>
 * where <code>[rootPath]</code> would be the root path of the application, configured in the base application and <code>[revNo]</code> would be the
 * current SVN revision number. An <code>id</code> attribute can be passed optionally.
 *
 * @author Christoph Wende
 */
public class Style extends RequestContextAwareTag {

  private static final Logger LOG = LoggerFactory.getLogger(Style.class);

  private static final long serialVersionUID = 3805265733096516623L;

  private String uri;

  private String staticResource = "false";

  /**
   * Initialize the tag with properties from base application.
   */
  private void init() {
    this.id = StringUtils.isBlank(this.id) ? "" : this.id;
  }

  @Override
  @SuppressWarnings("resource")
  public int doEndTag() throws JspException {
    this.init();
    String result = "";

    final String path = ConfigurationUtil.buildStylePath(this.getRequestContext(), this.pageContext, this.uri,
        StringUtils.equalsIgnoreCase(this.staticResource, "true"));

    result = String.format(GriefTaglibProperty.RESOURCE_TAG_STYLE, path, this.id);

    final JspWriter out = this.pageContext.getOut();
    try {
      out.println(result);
    } catch (final IOException e) {
      LOG.error("Could not write buffer to out", e);
    }
    return super.doEndTag();
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(final String uri) {
    this.uri = uri;
  }

  public String getStaticResource() {
    return this.staticResource;
  }

  public void setStaticResource(final String staticResource) {
    this.staticResource = staticResource;
  }

  @Override
  protected int doStartTagInternal() throws Exception {
    return 0;
  }

}
