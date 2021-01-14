package contoller;

import service.WebCrawler;
import view.Console;
import view.Viewer;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    public static final String DEFAULT_LINK = "https://en.wikipedia.org/wiki/Elon_Musk";
    public static final ArrayList<String> DEFAULT_WORDS = new ArrayList<>();

    static {
        DEFAULT_WORDS.add("Gigafactory");
        DEFAULT_WORDS.add("Musk");
        DEFAULT_WORDS.add("Tesla");
        DEFAULT_WORDS.add("Elon Mask");
    }

    private static final int DEFAULT_PAGE_LIMITER = 10000;
    private static final int DEFAULT_PREDEFINED_LINK_DEPTH = 8;

    private Viewer viewer = new Console();


    public void start() {
        String choice = viewer.showMainMenu();
        manageMenu(choice);
    }

    private void manageMenu(String function) {
        switch (function) {
            case "1":
                execute(DEFAULT_LINK, DEFAULT_WORDS,DEFAULT_PAGE_LIMITER,DEFAULT_PREDEFINED_LINK_DEPTH);
                break;
            case "2":
                String seed = viewer.getSeed();
                int pageLimit = viewer.getPageLimit();
                int depthLimit = viewer.getLevelDepthLimit();
                List<String> words = viewer.getWords();
                execute(seed, words,pageLimit,depthLimit);
                break;
            case "0":
                viewer.exit();
                System.exit(0);
            default:
                viewer.gettingWrongValueInMainMenu();

        }
    }

    private void execute(String link, List<String> words, Integer page, Integer depth) {
        WebCrawler webCrawler = new WebCrawler(link,words,page,depth);
        try {
            webCrawler.bfsAlg();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

