package de.kodestruktor.grief.core.tag;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.core.util.ConfigurationUtil;
import de.kodestruktor.grief.core.util.GriefConstants;

/**
 * Taglib to create a revision dependent link/favicon tag.<br>
 * An input with the <code>uri</code> attribute <code>icon.ico</code> would i.e. output <br>
 * <br>
 * <code>&lt;link href='/[rootPath]/resources/r[revNo]/images/icon.ico' rel='icon' type='image/x-icon' /&gt;</code><br>
 * <br>
 * where <code>[rootPath]</code> would be the root path of the application, configured in the base application and <code>[revNo]</code> would be the
 * current SVN revision number.<br>
 * <br>
 * The attributes <code>rel</code> and <code>type</code> are added automatically, the latter will be generated depending on the passed
 * <code>uri</code>. In case the optional attribute <code>staticResource</code> is set to <code>true</code>, the above example would be rendered as
 * <br>
 * <br>
 * <code>&lt;link href='/[rootPath]/resources/static/images/icon.ico' rel='icon' type='image/x-icon' /&gt;</code><br>
 * <br>
 * Also see {@link GriefConstants} for configuration options to manipulate the resource directories.
 *
 * @author Christoph Wende
 * @see GriefConstants
 */
public class Favicon extends RequestContextAwareTag {

  private static final Logger LOG = LoggerFactory.getLogger(Favicon.class);

  private static final long serialVersionUID = 3805265733096516623L;

  private String uri;

  private String staticResource = "false";

  private void init() {
    this.id = StringUtils.isBlank(this.id) ? "" : this.id;
  }

  @Override
  @SuppressWarnings("resource")
  public int doEndTag() throws JspException {
    this.init();
    String result = "";
    String mimeType = GriefConstants.FAVICON_DEFAULT_MIME_TYPE;

    final String path = ConfigurationUtil.buildImagePath(this.getRequestContext(), this.pageContext, this.uri,
        StringUtils.equalsIgnoreCase(this.staticResource, "true"));

    for (final Entry<Pattern, String> mimeEntry : GriefConstants.FAVICON_MIME_TYPES.entrySet()) {
      if (mimeEntry.getKey().matcher(this.uri).matches()) {
        mimeType = mimeEntry.getValue();
        break;
      }
    }

    result = String.format(GriefConstants.RESOURCE_TAG_FAVICON, path, mimeType);

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
