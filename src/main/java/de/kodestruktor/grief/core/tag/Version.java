package de.kodestruktor.grief.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.core.util.GriefConstants;
import de.kodestruktor.grief.core.util.ManifestUtil;

/**
 * Renders the applications version and/or revision based on the parameters set.
 *
 * @version $Id: Version.java 27 2015-09-29 08:57:09Z christoph $
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

    this.version = env.getProperty(GriefConstants.GRIEF_PROP_APP_VERSION);

    if (StringUtils.isBlank(this.version)) {
      this.version = ManifestUtil.getImplementationVersion((HttpServletRequest) this.pageContext.getRequest());
    }

    this.revision = ManifestUtil.getImplementationBuild((HttpServletRequest) this.pageContext.getRequest());

    this.tagEnabled = env.getProperty(GriefConstants.VERSION_PROP_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.metaEnabled = env.getProperty(GriefConstants.VERSION_PROP_META_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.contentEnabled = env.getProperty(GriefConstants.VERSION_PROP_CONTENT_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
    this.commentEnabled = env.getProperty(GriefConstants.VERSION_PROP_COMMENT_ENABLED, Boolean.class, Boolean.FALSE).booleanValue();
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
          result = String.format(GriefConstants.VERSION_TAG_META_SIMPLE, "Version", this.version);
          break;
        case "revision_meta":
          result = String.format(GriefConstants.VERSION_TAG_META_SIMPLE, "Revision", this.revision);
          break;
        case "both_meta":
          result = String.format(GriefConstants.VERSION_TAG_META_COMBINED, "Version", this.version, this.revision);
          break;
        case "version_content":
          result = String.format(GriefConstants.VERSION_TAG_CONTENT_SIMPLE, "version", "Version", this.version);
          break;
        case "revision_content":
          result = String.format(GriefConstants.VERSION_TAG_CONTENT_SIMPLE, "revision", "Revision", this.revision);
          break;
        case "both_content":
          result = String.format(GriefConstants.VERSION_TAG_CONTENT_COMBINED, "version", "Version", this.version, this.revision);
          break;
        case "version_comment":
          result = String.format(GriefConstants.VERSION_TAG_COMMENT_SIMPLE, "version", this.version);
          break;
        case "revision_comment":
          result = String.format(GriefConstants.VERSION_TAG_COMMENT_SIMPLE, "revision", this.revision);
          break;
        case "both_comment":
          result = String.format(GriefConstants.VERSION_TAG_COMMENT_COMBINED, "Version", this.version, this.revision);
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
