package de.kodestruktor.grief.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.core.util.ConfigurationUtil;
import de.kodestruktor.grief.core.util.GriefConstants;

/**
 * Taglib to create a revision dependent image tag.<br>
 * An input with the <code>uri</code> attribute <code>image.jpg</code> would i.e. output <br>
 * <br>
 * <code>&lt;img src='/[rootPath]/resources/r[revNo]/images/image.jpg' alt='[alt]' title='[title]' class='[cssClass]' id='[id]' /&gt;</code><br>
 * <br>
 * where <code>[rootPath]</code> would be the root path of the application, configured in the base application and <code>[revNo]</code> would be the
 * current SVN revision number.<br>
 * <br>
 * The attributes <code>alt</code>, <code>title</code>, <code>cssClass</code> and <code>id</code> are optional. In case the optional attribute
 * <code>staticResource</code> is set to <code>true</code>, the above example would be rendered as <br>
 * <br>
 * <code>&lt;img src='/[rootPath]/resources/static/images/image.jpg' alt='[alt]' title='[title]' class='[cssClass]' id='[id]' /&gt;</code><br>
 * <br>
 * Also see {@link GriefConstants} for configuration options to manipulate the resource directories.
 *
 * @version $Id: Image.java 36 2016-11-07 14:34:04Z christoph $
 * @author Christoph Wende
 * @see GriefConstants
 */
public class Image extends RequestContextAwareTag {

  private static final Logger LOG = LoggerFactory.getLogger(Image.class);

  private static final long serialVersionUID = 3805265733096516623L;

  private String uri;

  private String cssClass;

  private String alt;

  private String title;

  private String staticResource = "false";

  /**
   * Initialize the tag with properties from base application.
   */
  private void init() {
    this.id = StringUtils.isBlank(this.id) ? "" : this.id;
    this.alt = StringUtils.isBlank(this.alt) ? "" : this.alt;
    this.title = StringUtils.isBlank(this.title) ? "" : this.title;
    this.cssClass = StringUtils.isBlank(this.cssClass) ? "" : this.cssClass;
  }

  @Override
  @SuppressWarnings("resource")
  public int doEndTag() throws JspException {
    this.init();
    String result = "";

    final String path = ConfigurationUtil.buildImagePath(this.getRequestContext(), this.pageContext, this.uri,
        StringUtils.equalsIgnoreCase(this.staticResource, "true"));

    result = String.format(GriefConstants.RESOURCE_TAG_IMAGE, path, this.alt, this.title, this.cssClass, this.id);

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

  public String getCssClass() {
    return this.cssClass;
  }

  public void setCssClass(final String cssClass) {
    this.cssClass = cssClass;
  }

  public String getAlt() {
    return this.alt;
  }

  public void setAlt(final String alt) {
    this.alt = alt;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
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
