package de.kodestruktor.grief.taglib.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.core.manifest.ManifestReader;
import de.kodestruktor.grief.core.property.GriefProperty;
import de.kodestruktor.grief.taglib.property.GriefTaglibProperty;

/**
 * Renders the applications version and/or revision based on the parameters set.
 *
 * @author Christoph Wende
 */
public class Version extends RequestContextAwareTag {

  private static final long serialVersionUID = 3805265733096516623L;

  private static final Logger LOG = LoggerFactory.getLogger(Version.class);

  private String style;

  private String type;

  private String version = "-1";

  private String revision = "-1";

  private boolean tagEnabled;

  private boolean metaEnabled;

  private boolean contentEnabled;

  private boolean commentEnabled;

  /**
   * Initialize the tag with various properties scattered around the project.
   */
  private void init() {
    final Environment env = this.getRequestContext().getWebApplicationContext().getEnvironment();

    this.version = env.getProperty(GriefProperty.GRIEF_PROP_APP_VERSION);

    if (StringUtils.isBlank(this.version)) {
      this.version = ManifestReader.getSpecificationVersion((HttpServletRequest) this.pageContext.getRequest());
    }

    this.revision = ManifestReader.getImplementationVersion((HttpServletRequest) this.pageContext.getRequest());

    this.tagEnabled = env.getProperty(GriefTaglibProperty.VERSION_PROP_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.metaEnabled = env.getProperty(GriefTaglibProperty.VERSION_PROP_META_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.contentEnabled = env.getProperty(GriefTaglibProperty.VERSION_PROP_CONTENT_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.commentEnabled = env.getProperty(GriefTaglibProperty.VERSION_PROP_COMMENT_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
  }

  /**
   * Render the version tag based on the passed values and found properties. Resource warnings are supressed on purpose, as leaving the page contexts
   * output stream open is neccessary to render the whole page.
   */
  @SuppressWarnings("resource")
  @Override
  public int doEndTag() throws JspException {

    String result = "";

    this.init();

    if (this.isRenderingEnabled()) {

      switch (this.type + "_" + this.style) {
        case "version_meta":
          result = String.format(GriefTaglibProperty.VERSION_TAG_META_SIMPLE, "Version", this.version);
          break;
        case "revision_meta":
          result = String.format(GriefTaglibProperty.VERSION_TAG_META_SIMPLE, "Revision", this.revision);
          break;
        case "both_meta":
          result = String.format(GriefTaglibProperty.VERSION_TAG_META_COMBINED, "Version", this.version, this.revision);
          break;
        case "version_content":
          result = String.format(GriefTaglibProperty.VERSION_TAG_CONTENT_SIMPLE, "version", "Version", this.version);
          break;
        case "revision_content":
          result = String.format(GriefTaglibProperty.VERSION_TAG_CONTENT_SIMPLE, "revision", "Revision", this.revision);
          break;
        case "both_content":
          result = String.format(GriefTaglibProperty.VERSION_TAG_CONTENT_COMBINED, "version", "Version", this.version, this.revision);
          break;
        case "version_comment":
          result = String.format(GriefTaglibProperty.VERSION_TAG_COMMENT_SIMPLE, "version", this.version);
          break;
        case "revision_comment":
          result = String.format(GriefTaglibProperty.VERSION_TAG_COMMENT_SIMPLE, "revision", this.revision);
          break;
        case "both_comment":
          result = String.format(GriefTaglibProperty.VERSION_TAG_COMMENT_COMBINED, "Version", this.version, this.revision);
          break;
        default:
          break;
      }
    }

    final JspWriter out = this.pageContext.getOut();
    try {
      out.println(result);
    } catch (final IOException e) {
      LOG.error("Could not write buffer to out", e);
    }

    return super.doEndTag();
  }

  protected boolean isRenderingEnabled() {

    // check the basics
    if (!this.tagEnabled || StringUtils.isAnyBlank(this.style, this.type)) {
      return false;
    }

    if ("meta".equals(this.style) && !this.metaEnabled) {
      return false;
    }
    if ("comment".equals(this.style) && !this.commentEnabled) {
      return false;
    }
    if ("content".equals(this.style) && !this.contentEnabled) {
      return false;
    }

    return true;
  }

  public String getStyle() {
    return this.style;
  }

  public void setStyle(final String style) {
    this.style = style;
  }

  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  @Override
  protected int doStartTagInternal() throws Exception {
    return 0;
  }

}
