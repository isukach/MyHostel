package by.bsuir.suite.util;

import java.util.HashMap;
import java.util.Map;

import static by.bsuir.suite.util.StringUtils.emptyString;

/**
 * @author i.sukach
 */
public final class LinkUtils {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String DOT = ".";
    private static final String WWW = "www.";

    private static final String VK_URL = "vk.com/";
    private static final String DEVIANT_ART_URL = "deviantart.com";
    private static final String FACEBOOK_URL = "facebook.com/";
    private static final String GOOGLE_PLUS_URL = "plus.google.com/";
    private static final String LAST_FM_URL = "lastfm.ru/user/";
    private static final String SKYPE_URL_START = "skype:";
    private static final String SKYPE_URL_END = "?call";
    private static final String TWITTER_URL = "twitter.com/";
    private static final String VIMEO_URL = "vimeo.com/";
    private static final String YOUTUBE_URL = "youtube.com/user/";

    private static final String VK_IMAGE_PATH = "images/social/vk.png";
    private static final String DEVIANT_ART_IMAGE_PATH = "images/social/deviant_art.png";
    private static final String FACEBOOK_IMAGE_PATH = "images/social/fb.png";
    private static final String GOOGLE_IMAGE_PATH = "images/social/google.png";
    private static final String LAST_FM_IMAGE_PATH = "images/social/lastfm.png";
    private static final String SKYPE_IMAGE_PATH = "images/social/skype.png";
    private static final String TWITTER_IMAGE_PATH = "images/social/twitter.png";
    private static final String VIMEO_IMAGE_PATH = "images/social/vimeo.png";
    private static final String YOUTUBE_IMAGE_PATH = "images/social/youtube.png";

    private LinkUtils() {
    }

    private static final Map<Link, String> IMAGE_PATHS = new HashMap<Link, String>();

    static {
        IMAGE_PATHS.put(Link.VK, VK_IMAGE_PATH);
        IMAGE_PATHS.put(Link.DEVIANT_ART, DEVIANT_ART_IMAGE_PATH);
        IMAGE_PATHS.put(Link.FACEBOOK, FACEBOOK_IMAGE_PATH);
        IMAGE_PATHS.put(Link.GOOGLE, GOOGLE_IMAGE_PATH);
        IMAGE_PATHS.put(Link.LAST_FM, LAST_FM_IMAGE_PATH);
        IMAGE_PATHS.put(Link.SKYPE, SKYPE_IMAGE_PATH);
        IMAGE_PATHS.put(Link.TWITTER, TWITTER_IMAGE_PATH);
        IMAGE_PATHS.put(Link.VIMEO, VIMEO_IMAGE_PATH);
        IMAGE_PATHS.put(Link.YOUTUBE, YOUTUBE_IMAGE_PATH);
    }

    public static String getImagePath(Link linkName) {
        if (IMAGE_PATHS.containsKey(linkName)) {
            return IMAGE_PATHS.get(linkName);
        }
        return emptyString();
    }

    public static String generateHrefForLinkName(Link linkName, String link) {
        if (link.startsWith("/")) {
            link = link.substring(1, link.length());
        }
        if (link.endsWith("/")) {
            link = link.substring(0, link.length() - 1);
        }
        switch (linkName) {
            case VK:
                return generateCommonHref(link, VK_URL);
            case DEVIANT_ART:
                return generateDeviantArtHref(link);
            case FACEBOOK:
                return generateCommonHref(link, FACEBOOK_URL);
            case GOOGLE:
                return generateGoogleHref(link);
            case LAST_FM:
                return generateLastFmHref(link);
            case SKYPE:
                return generateSkypeHref(link);
            case TWITTER:
                return generateCommonHref(link, TWITTER_URL);
            case VIMEO:
                return generateCommonHref(link, VIMEO_URL);
            case YOUTUBE:
                return generateCommonHref(link, YOUTUBE_URL);
            default:
                return "";
        }
    }

    private static String generateCommonHref(String link, String url) {
        if (link.startsWith(HTTP) || link.startsWith(HTTPS)) {
            return link;
        }
        if (link.startsWith(WWW)) {
            return HTTPS + link;
        }
        if (link.startsWith(url)) {
            return HTTPS + link;
        }
        return HTTPS + WWW + url + link;
    }

    private static String generateDeviantArtHref(String link) {
        if (link.startsWith(HTTP) || link.startsWith(HTTPS)) {
            return link;
        }
        if (link.startsWith(WWW)) {
            return HTTP + link;
        }
        if (link.endsWith(DEVIANT_ART_URL)) {
            return HTTP + link;
        }
        return HTTP + WWW + link + DOT + DEVIANT_ART_URL;
    }

    private static String generateGoogleHref(String link) {
        if (link.startsWith(HTTP) || link.startsWith(HTTPS)) {
            return link;
        }
        if (link.startsWith(GOOGLE_PLUS_URL)) {
            return HTTPS + link;
        }
        return HTTPS + GOOGLE_PLUS_URL + link;
    }

    private static String generateLastFmHref(String link) {
        if (link.startsWith(HTTP) || link.startsWith(HTTPS)) {
            return link;
        }
        if (link.startsWith(WWW)) {
            return HTTP + link;
        }
        if (link.startsWith(LAST_FM_URL)) {
            return HTTP + link;
        }
        return HTTP + WWW + LAST_FM_URL + link;
    }

    private static String generateSkypeHref(String link) {
        return SKYPE_URL_START + link + SKYPE_URL_END;
    }
}
