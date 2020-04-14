package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import com.sun.tools.javac.Main;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocator {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    public GeoLocation getGeoLocation() throws IOException {
        logger.debug("Get GeoLocation");
        return getGeoLocation(null);
    }

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            logger.debug("IP Address or Host is " + ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            logger.info("url: " + url);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
            logger.info("url: " + url);
        }
        String s = IOUtils.toString(url, "UTF-8");
        logger.debug("Converting URL to UTF-8 string");
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            logger.debug("Checking Command Line argument");
            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            logger.error("This is IOException message: " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }

}
