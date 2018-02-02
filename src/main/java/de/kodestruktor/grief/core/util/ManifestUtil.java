package de.kodestruktor.grief.core.util;

import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for retrieving information from the manifest based on the current request.
 *
 * @author Christoph Wende
 */
public class ManifestUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ManifestUtil.class);

  /**
   * Retrieve the manifest files attribute value denoted by {@link GriefConstants#MANIFEST_IMPLEMENTATION_BUILD}.
   *
   * @param request
   *          the current request
   * @return the value or an empty string if not found
   */
  public static String getImplementationBuild(final HttpServletRequest request) {
    return getManifestAttribute(request, GriefConstants.MANIFEST_IMPLEMENTATION_BUILD);
  }

  /**
   * Retrieve the manifest files attribute value denoted by {@link GriefConstants#MANIFEST_IMPLEMENTATION_VERSION}.
   *
   * @param request
   *          the current request
   * @return the value or an empty string if not found
   */
  public static String getImplementationVersion(final HttpServletRequest request) {
    return getManifestAttribute(request, GriefConstants.MANIFEST_IMPLEMENTATION_VERSION);
  }

  /**
   * Retrieve the manifest files attribute value denoted by the passed attribute name.
   *
   * @param request
   *          the current request
   * @param attributeName
   *          the attribute name of the value to find
   * @return the value or an empty string if not found
   */
  private static String getManifestAttribute(final HttpServletRequest request, final String attributeName) {
    String attributeValue = "";
    final ServletContext servletContext = request.getSession().getServletContext();

    try (InputStream manifestStream = servletContext.getResourceAsStream(JarFile.MANIFEST_NAME)) {

      if (manifestStream != null) {
        final Manifest manifest = new Manifest(manifestStream);
        final Attributes mainAttributes = manifest.getMainAttributes();
        attributeValue = mainAttributes.getValue(attributeName);

        if (StringUtils.isBlank(attributeValue)) {
          LOG.warn("Could not find attribute [{}] in manifest", attributeName);
          attributeValue = "";
        }
      }

    } catch (final Exception e) {
      LOG.error("Could not read source revision.", e);
    }

    return attributeValue;
  }

}
