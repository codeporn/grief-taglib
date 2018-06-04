package de.kodestruktor.grief.taglib.util;

import java.util.jar.Manifest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.support.RequestContext;

import de.kodestruktor.grief.core.manifest.ManifestReader;
import de.kodestruktor.grief.core.property.GriefProperty;
import de.kodestruktor.grief.taglib.property.GriefTaglibProperty;

/**
 * Utility methods making use of configuration; either the artifact ones or the configuration of the enclosing application.
 *
 * @author Christoph Wende
 */
public class ConfigurationUtil {

  /**
   * Build a path to a static or versioned image resource.
   *
   * @param requestContext
   *          the current request context to determine the applicaitons {@link Environment}
   * @param pageContext
   *          the current page context to determine the applications {@link Manifest}
   * @param uri
   *          the URI to the image, relative to either the static or versioned resource directory
   * @param staticResource
   *          <code>true</code> if a path to a static image should be build, <code>false</code> otherwise
   * @return the path to the image resource
   */
  public static String buildImagePath(final RequestContext requestContext, final PageContext pageContext, final String uri,
      final boolean staticResource) {
    return buildResourcePath(requestContext, pageContext, GriefTaglibProperty.RESOURCE_PROP_IMAGEDIR, uri, staticResource);
  }

  /**
   * Build a path to a static or versioned script resource.
   *
   * @param requestContext
   *          the current request context to determine the applicaitons {@link Environment}
   * @param pageContext
   *          the current page context to determine the applications {@link Manifest}
   * @param uri
   *          the URI to the script, relative to either the static or versioned resource directory
   * @param staticResource
   *          <code>true</code> if a path to a static script should be build, <code>false</code> otherwise
   * @return the path to the script resource
   */
  public static String buildScriptPath(final RequestContext requestContext, final PageContext pageContext, final String uri,
      final boolean staticResource) {
    return buildResourcePath(requestContext, pageContext, GriefTaglibProperty.RESOURCE_PROP_SCRIPTDIR, uri, staticResource);
  }

  /**
   * Build a path to a static or versioned stylesheet resource.
   *
   * @param requestContext
   *          the current request context to determine the applicaitons {@link Environment}
   * @param pageContext
   *          the current page context to determine the applications {@link Manifest}
   * @param uri
   *          the URI to the stylesheet, relative to either the static or versioned resource directory
   * @param staticResource
   *          <code>true</code> if a path to a static stylesheet should be built, <code>false</code> otherwise
   * @return the path to the stylesheet resource
   */
  public static String buildStylePath(final RequestContext requestContext, final PageContext pageContext, final String uri,
      final boolean staticResource) {
    return buildResourcePath(requestContext, pageContext, GriefTaglibProperty.RESOURCE_PROP_STYLEDIR, uri, staticResource);
  }

  /**
   * Build a path to a static or versioned resource.
   *
   * @param requestContext
   *          the current request context to determine the applicaitons {@link Environment}
   * @param pageContext
   *          the current page context to determine the applications {@link Manifest}
   * @param relativeLocationProp
   *          the resource location property name; should contain a relative path inside the static or versioned resource directory, where the
   *          resource can be found
   * @param uri
   *          the URI to the stylesheet, relative to either the static or versioned resource directory
   * @param staticResource
   *          <code>true</code> if a path to a static resource should be built, <code>false</code> otherwise
   * @return the path to the resource
   */
  private static String buildResourcePath(final RequestContext requestContext, final PageContext pageContext, final String relativeLocationProp,
      final String uri, final boolean staticResource) {

    final Environment env = requestContext.getWebApplicationContext().getEnvironment();

    final String rootPath = env.getProperty(GriefProperty.GRIEF_PROP_APP_ROOT);

    final String resourceLocation = env.getProperty(GriefTaglibProperty.RESOURCE_PROP_BASEDIR_RESOURCES);
    final String staticLocation = env.getProperty(GriefTaglibProperty.RESOURCE_PROP_BASEDIR_STATIC);
    final String revisionLocationPrefix = env.getProperty(GriefTaglibProperty.RESOURCE_PROP_VERSIONDIR_PREFIX);
    final String relativeResourceLocation = env.getProperty(relativeLocationProp);
    final String revision = ManifestReader.getImplementationVersion((HttpServletRequest) pageContext.getRequest());

    final StringBuffer path = new StringBuffer();

    path.append(rootPath).append(resourceLocation);

    if (staticResource) {
      path.append(staticLocation);
    } else {
      path.append(revisionLocationPrefix).append(revision);
    }

    path.append(relativeResourceLocation).append(uri.startsWith("/") ? "" : "/").append(uri);

    return path.toString();
  }
}
