package de.kodestruktor.grief.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import de.kodestruktor.grief.core.util.GriefConstants;

/**
 * Taglib which creates a ul/li pagination with the help of a given {@link Page} object. The pagination has a previous/next link and a list of all
 * pages. The HTML code has the following form:
 * <ul>
 * <li class='prev'><a href='#'>Prev</a></li>
 * <li><a href='#'>1</a></li>
 * <li><a href='#'>2</a></li>
 * <li><a href='#'>3</a></li>
 * <li class='next'><a href='#'>Next</a></li>
 * </ul>
 * Per default the ul-element has the class "pagination", an additional class and/or an id can be added in the tag-lib parameters "cssClass" and "id".
 * The li-element representing the current page gets the class "active", the li-elements for the prev/next buttons get the classes "prev" and "next".
 *
 * @param <T>
 *          type which is delivered by page (e.g. User objects)
 * @version $Id: Pagination.java 28 2016-10-14 13:46:41Z christoph $
 * @author Christoph Wende
 */

public class Pagination<T> extends RequestContextAwareTag {

  private static final Logger LOG = LoggerFactory.getLogger(Pagination.class);

  private static final long serialVersionUID = -6808629135782081070L;

  private Page<T> page;

  private String prevCode = GriefConstants.PAGINATION_PROP_PREV;

  private String nextCode = GriefConstants.PAGINATION_PROP_NEXT;

  private String uri;

  private String cssClass;

  private String pageSizeParam = GriefConstants.PAGINATION_PAGE_SIZE_PARAMETER;

  private String pageNumberParam = GriefConstants.PAGINATION_PAGE_NUMBER_PARAMETER;

  @SuppressWarnings("resource")
  @Override
  public int doEndTag() throws JspException {
    final JspWriter out = this.pageContext.getOut();
    final StringBuffer buffer = new StringBuffer();
    final Page<T> currentPage = this.getPage();

    final String finalUri = this.uri.contains("?") ? this.uri.endsWith("&") ? this.uri : this.uri + "&"
        : this.uri + "?" + this.pageSizeParam + "=" + currentPage.getSize() + "&";

    // getting label translation
    final String prev = this.getRequestContext().getWebApplicationContext().getMessage(this.getPrevCode(), null,
        RequestContextUtils.getLocale((HttpServletRequest) this.pageContext.getRequest()));
    final String next = this.getRequestContext().getWebApplicationContext().getMessage(this.getNextCode(), null,
        RequestContextUtils.getLocale((HttpServletRequest) this.pageContext.getRequest()));

    buffer.append("<ul class='");

    // set additional css class if given
    if (StringUtils.isNotBlank(this.getCssClass())) {
      buffer.append(this.getCssClass());
    }

    buffer.append("'");

    // set id if given
    if (StringUtils.isNotBlank(this.getId())) {
      buffer.append(" id='").append(this.getId()).append("'");
    }

    buffer.append(">");

    // create link for previous page
    if (currentPage.hasPrevious()) {
      buffer.append("<li class='prev'><a href='").append(finalUri).append(this.pageNumberParam).append("=").append(currentPage.getNumber() - 1)
          .append("'>").append(prev).append("</a></li>");
    }

    // create list of all pages
    if (currentPage.getTotalPages() > 0) {
      for (int i = 0; i < currentPage.getTotalPages(); i++) {
        buffer.append("<li");

        // set active class if we are on the current page
        if (currentPage.getNumber() == i) {
          buffer.append(" class='active'");
        }

        buffer.append("><a href='").append(finalUri).append(this.pageNumberParam).append("=").append(i).append("'>").append(i + 1)
            .append("</a></li>");
      }
    }

    // create link for next page
    if (currentPage.hasNext()) {
      buffer.append("<li class='next'><a href='").append(finalUri).append(this.pageNumberParam).append("=").append(currentPage.getNumber() + 1)
          .append("'>").append(next).append("</a></li>");
    }

    buffer.append("</ul>");

    try {
      out.println(buffer.toString());
    } catch (final IOException e) {
      LOG.error("Could not write buffer to out", e);
    }
    return super.doEndTag();
  }

  public void setPage(final Page<T> page) {
    this.page = page;
  }

  public Page<T> getPage() {
    return this.page;
  }

  public String getPrevCode() {
    return this.prevCode;
  }

  public void setPrevCode(final String prevCode) {
    this.prevCode = prevCode;
  }

  public String getNextCode() {
    return this.nextCode;
  }

  public void setNextCode(final String nextCode) {
    this.nextCode = nextCode;
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

  public String getPageSizeParam() {
    return this.pageSizeParam;
  }

  public void setPageSizeParam(final String pageSizeParam) {
    this.pageSizeParam = pageSizeParam;
  }

  public String getPageNumberParam() {
    return this.pageNumberParam;
  }

  public void setPageNumberParam(final String pageNumberParam) {
    this.pageNumberParam = pageNumberParam;
  }

  @Override
  protected int doStartTagInternal() throws Exception {
    return 0;
  }

}
