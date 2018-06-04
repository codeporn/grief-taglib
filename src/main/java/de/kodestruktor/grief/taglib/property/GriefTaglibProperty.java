package de.kodestruktor.grief.taglib.property;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.kodestruktor.grief.taglib.tag.Favicon;
import de.kodestruktor.grief.taglib.tag.Image;
import de.kodestruktor.grief.taglib.tag.Pagination;
import de.kodestruktor.grief.taglib.tag.Script;
import de.kodestruktor.grief.taglib.tag.Style;
import de.kodestruktor.grief.taglib.tag.Version;

/**
 * Constants used in grief internally.
 *
 * @author Christoph Wende
 */
public final class GriefTaglibProperty {

  /**
   * Empty private constructor to avoid instantiation.
   */
  private GriefTaglibProperty() {
    // Here be dragons...
  }

  static {
    final Map<Pattern, String> map = new HashMap<>(2);
    map.put(Pattern.compile(".+\\.ico$"), "image/x-icon");
    map.put(Pattern.compile(".+\\.gif$"), "image/gif");
    map.put(Pattern.compile(".+\\.png$"), "image/png");
    map.put(Pattern.compile(".+\\.jp[eg]{1,2}$"), "image/jpeg");

    FAVICON_MIME_TYPES = Collections.unmodifiableMap(map);
  }

  /**
   * Property containing possible mime types for simple mapping against a file extension.
   *
   * @see Favicon
   */
  public static final Map<Pattern, String> FAVICON_MIME_TYPES;

  /**
   * Property containing the default mime type, in case the correct mime type cannot be determined.
   *
   * @see Favicon
   */
  public static final String FAVICON_DEFAULT_MIME_TYPE = "application/octet-stream";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Used to globally enable or disable the version tag, e.g.:<br>
   * <br>
   * <code>grief.version.enabled=true</code>
   *
   * @see Version
   */
  public static final String VERSION_PROP_ENABLED = "grief.version.enabled";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Used to globally enable or disable the version tags outpus in comment style, e.g.:<br>
   * <br>
   * <code>grief.version.comment.enabled=true</code>
   *
   * @see Version
   */
  public static final String VERSION_PROP_COMMENT_ENABLED = "grief.version.comment.enabled";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Used to globally enable or disable the version tags outpus in content style, e.g.:<br>
   * <br>
   * <code>grief.version.content.enabled=true</code>
   *
   * @see Version
   */
  public static final String VERSION_PROP_CONTENT_ENABLED = "grief.version.content.enabled";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Used to globally enable or disable the version tags outpus in meta style, e.g.:<br>
   * <br>
   * <code>grief.version.meta.enabled=true</code>
   *
   * @see Version
   */
  public static final String VERSION_PROP_META_ENABLED = "grief.version.meta.enabled";

  /**
   * Output format of the version tag, when rendered in meta style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_META_SIMPLE = "<meta name=\"%s\" content=\"%s\" />";

  /**
   * Output format of the version tag, when rendered in meta style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_META_COMBINED = "<meta name=\"%s\" content=\"%sr%s\" />";

  /**
   * Output format of the version tag, when rendered in content style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_CONTENT_SIMPLE = "<span id=\"%s\">%s: %s</span>";

  /**
   * Output format of the version tag, when rendered in content style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_CONTENT_COMBINED = "<span id=\"%s\">%s: %sr%s</span>";

  /**
   * Output format of the version tag, when rendered in comment style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_COMMENT_SIMPLE = "<!-- %s: %s -->";

  /**
   * Output format of the version tag, when rendered in comment style.
   *
   * @see Version
   */
  public static final String VERSION_TAG_COMMENT_COMBINED = "<!-- %s: %sr%s -->";

  /**
   * Default URL parameter name for defining the page size in generated links of the page numbers.
   *
   * @see Pagination
   */
  public static final String PAGINATION_PAGE_SIZE_PARAMETER = "size";

  /**
   * Default URL parameter name for defining the page number in generated links of the page numbers.
   *
   * @see Pagination
   */
  public static final String PAGINATION_PAGE_NUMBER_PARAMETER = "page";

  /**
   * Message code that may be set in the resource bundle of the application using grief. <br>
   * Lables the paginations 'next' button.
   *
   * @see Pagination
   */
  public static final String PAGINATION_PROP_NEXT = "grief.pagiantion.next";

  /**
   * Message code that may be set in the resource bundle of the application using grief. <br>
   * Lables the paginations 'prev' button.
   *
   * @see Pagination
   */
  public static final String PAGINATION_PROP_PREV = "grief.pagiantion.prev";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the base directory for all web resources such as js, css or images; e.g.:<br>
   * <br>
   * <code>grief.resource.dir.base.resource=/resources</code>
   *
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_BASEDIR_RESOURCES = "grief.resource.dir.base.resource";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the base directory for <b>static</b> web resources, relative to the resources basedir configured in
   * {@link GriefTaglibProperty#RESOURCE_PROP_BASEDIR_RESOURCES}. <br>
   * <br>
   * Example: <br>
   * <code>grief.resource.dir.base.static=/static</code>
   *
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_BASEDIR_STATIC = "grief.resource.dir.base.static";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the prefix for the <b>versioned</b> web resources base directory, relative to the resources basedir configured in
   * {@link GriefTaglibProperty#RESOURCE_PROP_BASEDIR_RESOURCES}. <br>
   * <br>
   * Example: <br>
   * <code>grief.resource.dir.version.prefix=/r</code>
   *
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_VERSIONDIR_PREFIX = "grief.resource.dir.version.prefix";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the directory for image resources, relative to the static and/or versioned directories. <br>
   * <br>
   * Example: <br>
   * <code>grief.resource.dir.image=/images</code>
   *
   * @see GriefTaglibProperty#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefTaglibProperty#RESOURCE_PROP_VERSIONDIR_PREFIX
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_IMAGEDIR = "grief.resource.dir.image";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the directory for script resources, relative to the static and/or versioned directories. <br>
   * <br>
   * Example: <br>
   * <code>grief.resource.dir.image=/images</code>
   *
   * @see GriefTaglibProperty#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefTaglibProperty#RESOURCE_PROP_VERSIONDIR_PREFIX
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_SCRIPTDIR = "grief.resource.dir.script";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * Configures the directory for stylesheet resources, relative to the static and/or versioned directories. <br>
   * <br>
   * Example: <br>
   * <code>grief.resource.dir.image=/images</code>
   *
   * @see GriefTaglibProperty#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefTaglibProperty#RESOURCE_PROP_VERSIONDIR_PREFIX
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String RESOURCE_PROP_STYLEDIR = "grief.resource.dir.style";

  /**
   * Output format of the static/versioned favicon/link tag.
   *
   * @see Image
   */
  public static final String RESOURCE_TAG_FAVICON = "<link href=\"%s\" rel=\"icon\" type=\"%s\" />";

  /**
   * Output format of the static/versioned image tag.
   *
   * @see Image
   */
  public static final String RESOURCE_TAG_IMAGE = "<img src=\"%s\" alt=\"%s\" title=\"%s\" class=\"%s\" id=\"%s\" />";

  /**
   * Output format of the static/versioned script tag.
   *
   * @see Script
   */
  public static final String RESOURCE_TAG_SCRIPT = "<script type=\"text/javascript\" src=\"%s\" id=\"%s\"></script>";

  /**
   * Output format of the static/versioned asynchronous script tag.
   *
   * @see Script
   */
  public static final String RESOURCE_TAG_SCRIPT_ASYNC = "<script type=\"text/javascript\" src=\"%s\" id=\"%s\" async></script>";

  /**
   * Output format of the static/versioned style/link tag.
   *
   * @see Style
   */
  public static final String RESOURCE_TAG_STYLE = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\" id=\"%s\" />";
}
