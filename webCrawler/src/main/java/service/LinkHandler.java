package service;


import org.apache.commons.lang3.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.*;


public class LinkHandler {

    public static Set<String> getLinks(String url) {
        Set<String> setLinks =  new LinkedHashSet<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IllegalArgumentException|IOException e) {
            e.printStackTrace();
            return setLinks;
        }
        Elements links = doc.select("a[href]");
        for (Element link : links) {
           setLinks.add(StringUtils.substringBefore(link.attr("abs:href"),"#"));
        }
        return setLinks;
    }


//
//    private static final String LINK_REGEX_EXPRESSION = "<a\\s.*?href=\"(.+?)\".*?>(.+?)</a>";
//
//   public static Set<String> getListLinks(String pageHTML, String parentLink) {
//        Pattern p = Pattern.compile(LINK_REGEX_EXPRESSION);
//        Matcher m = p.matcher(pageHTML);
//        Set<String> list = new HashSet<>();
//        while (m.find()) {
//            String newLink = m.group(1);
//            if (newLink.charAt(0) != '#') {
//                newLink = refactoringLink(newLink, parentLink);
//                list.add(newLink);
//            }
//        }
//        return list;
//    }
//
//
//    private static String refactoringLink(String link, String parentLink) {
//        String parentHost = PageHandler.getHost(parentLink);
//        if (link.charAt(0) == '/') {
//            link = parentHost.concat(link);
//            return link;
//        } else return link;
//
//    }


}

