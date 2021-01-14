package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class PageHandler {

    static String parseForSearch(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return document.text();
    }

}
