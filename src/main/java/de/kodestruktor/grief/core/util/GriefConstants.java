package de.kodestruktor.grief.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.kodestruktor.grief.core.tag.Favicon;
import de.kodestruktor.grief.core.tag.Image;
import de.kodestruktor.grief.core.tag.Pagination;
import de.kodestruktor.grief.core.tag.Script;
import de.kodestruktor.grief.core.tag.Style;
import de.kodestruktor.grief.core.tag.Version;

/**
 * Constants used in grief internally.
 *
 * @version $Id: GriefConstants.java 37 2016-11-10 12:19:30Z christoph $
 * @author Christoph Wende
 */
public final class GriefConstants {

  /**
   * Empty private constructor to avoid instantiation.
   */
  private GriefConstants() {
    // Here be dragons...
  }

  /**
   * Property that may be set in the manifest file of the application using grief. <br>
   * Manifest attribute name "Implementation-Build"
   *
   * @see ManifestUtil
   * @see Version
   */
  public static final String MANIFEST_IMPLEMENTATION_BUILD = "Implementation-Build";

  /**
   * Property that may be set in the manifest file of the application using grief. <br>
   * Manifest attribute name "Implementation-Version"
   *
   * @see ManifestUtil
   * @see Version
   */
  public static final String MANIFEST_IMPLEMENTATION_VERSION = "Implementation-Version";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * This should hold the root path of the application, e.g.:<br>
   * <br>
   * <code>grief.parent.application.root=/my-app</code><br>
   * <br>
   * if the application is running at <em>http://my-domain.org/my-app</em>
   *
   * @see Image
   * @see Script
   * @see Style
   */
  public static final String GRIEF_PROP_APP_ROOT = "grief.parent.application.root";

  /**
   * Property that may be set in the configuration of the application using grief. <br>
   * This should hold the version of the application, e.g.:<br>
   * <br>
   * <code>grief.parent.application.version=0.9.2</code>
   *
   * @see Version
   */
  public static final String GRIEF_PROP_APP_VERSION = "grief.parent.application.version";

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
   * {@link GriefConstants#RESOURCE_PROP_BASEDIR_RESOURCES}. <br>
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
   * {@link GriefConstants#RESOURCE_PROP_BASEDIR_RESOURCES}. <br>
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
   * @see GriefConstants#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefConstants#RESOURCE_PROP_VERSIONDIR_PREFIX
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
   * @see GriefConstants#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefConstants#RESOURCE_PROP_VERSIONDIR_PREFIX
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
   * @see GriefConstants#RESOURCE_PROP_BASEDIR_RESOURCES
   * @see GriefConstants#RESOURCE_PROP_VERSIONDIR_PREFIX
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
