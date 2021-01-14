//package service;
//
//
//import contoller.MainController;
//import model.DepthLevel;
//import model.Result;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import util.CsvWriter;
//import util.Printer;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;


//public class WebCrawler {
//    private static final Logger log = LogManager.getRootLogger();
//
//    private static final String ALL_STAT_DATA = "src\\main\\resources\\AllStatData.csv";
//    private static final String TOP_10_PAGES = "src\\main\\resources\\Top10pages.csv";
//
//    private static DepthLevel depthLevel = new DepthLevel();
//
//    private static Queue<String> queue = new LinkedList<>();
//    private static Set<String> marked = new HashSet<>();
//    private static Set<Result> results = new LinkedHashSet<>();
//
//    public void bfsAlgorithm(String firstLink, int pageLimiter, int depthLimit, List<String> words) {
//        clearLastData();
//        queue.add(firstLink);
//        marked.add(firstLink);
//        try {
//            results.add(TextFinder.getResult(words, firstLink));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        while (!queue.isEmpty()) {
//            if (depthLevel.getLevel() > depthLimit) {
//                log.info("закончился лимит глубины");
//                break;
//            }
//            if (depthLevel.getCurrentCounter() > depthLevel.getBorder()) {
//                depthLevel.levelUp();
//                depthLevel.setBorder(depthLevel.getNewBorder());
//            } else {
//                depthLevel.currentCounterUp();
//                String crawledUrl = queue.poll();
//                log.info("Site number " + depthLevel.getCurrentCounter() + " crawled======           " + crawledUrl + "               ======");
//                boolean isChecked = false;
//
//                while (!isChecked) {
//                    try {
//                        isChecked = true;
//                    } catch (Exception e) {
//                        log.error("*** Interrupted URL:" + crawledUrl + "    " + e.getMessage());
//                        crawledUrl = queue.poll();
//                        isChecked = false;
//                    }
//
//                    Set<String> setLinks = null;
//                    setLinks = LinkHandler.getLinks(crawledUrl);
//
//                    depthLevel.newLimiterUp(setLinks.size());
//
//
//                    ScheduledExecutorService execService
//                            = Executors.newScheduledThreadPool(25);
//                    for (String link : setLinks) {
//                        if (marked.size() >= pageLimiter)
//                            return;
//                        if (!marked.contains(link)) {
//                            marked.add(link);
//                            try {
//                                results.add(TextFinder.getResult(words, link));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                            log.info("sited added for crawling: " + link);
//                            queue.add(link);
//
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//
//
//
//
//    public void bfsAlg(String firstLink, List<String> words, int limitPage, int limitDepth) throws Exception {
//        clearLastData();
//        queue.add(firstLink);
//
//        while (!queue.isEmpty()) {
//
//            if (depthLevel.getCurrentCounter() > depthLevel.getBorder()) {
//                depthLevel.levelUp();
//                depthLevel.setBorder(depthLevel.getNewBorder());
//            }
//            String currentLink = queue.poll();
//
//            ScheduledExecutorService execService
//                    = Executors.newScheduledThreadPool(50);
//
//            if (!marked.contains(currentLink)) {
//
//                execService.schedule(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            results.add(TextFinder.getResult(words, currentLink));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, 3L, TimeUnit.SECONDS);
//                marked.add(currentLink);
//                log.info("Site number crawled======           " + currentLink + "               ======");
//
//                for (String link : LinkHandler.getLinks(currentLink)) {
//                    if (depthLevel.getLevel() < limitDepth) {
//                        if (depthLevel.getCurrentCounter() < limitPage) {
//                            queue.add(link);
//                            log.info("sited added for crawling: " + link);
//
//                            depthLevel.currentCounterUp();
//                        }
//                    }
//                }
//
//
//            }
//        }
//    }
//
//
//
//
//    private void clearLastData() {
//        new CsvWriter(ALL_STAT_DATA).cleanFiles();
//        new CsvWriter(TOP_10_PAGES).cleanFiles();
//    }
//
//    public void showResult() {
//        Printer printer = new CsvWriter(ALL_STAT_DATA);
//        log.info("\n\nResults:");
//        log.info("Websites crawled : " + marked.size() + "\n");
//
//        for (Result result : results) {
//            log.info("*" + result.toString());
//            printer.print(result);
//        }
//    }
//
//    public void showTop10() {
//        Printer printer = new CsvWriter(TOP_10_PAGES);
//        System.out.println("\n\nTop 10:");
//        PriorityQueue<Result> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
//        priorityQueue.addAll(results);
//        for (int i = 0; i < 10; i++) {
//            Result result = priorityQueue.poll();
//            log.info(result);
//            printer.print(result);
//        }
//    }
//
//}

package service;


import model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.CsvWriter;
import util.Printer;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class WebCrawler {
    private static final Logger log = LogManager.getRootLogger();

    private static final String ALL_STAT_DATA = "src\\main\\resources\\AllStatData.csv";
    private static final String TOP_10_PAGES = "src\\main\\resources\\Top10pages.csv";

    private final Integer pageLimit;
    private final Integer depthLimit;

    private Queue<String> linkQueue;
    private List<String> desiredWords;
    private Set<String> lowerstLinkQueue = new LinkedHashSet<>();
    private Set<String> marked = new HashSet<>();
    private ThreadPoolExecutor threadPoolExecutor;
    private AtomicInteger depthLevel = new AtomicInteger(1);

    private static Set<Result> results = new LinkedHashSet<>();

    public WebCrawler(String firstLink, List<String> desiredWords, Integer pageLimit, Integer depthLimit) {
        this.linkQueue = new LinkedList<>(Collections.singletonList(firstLink));
        this.desiredWords = desiredWords;
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
        this.pageLimit = pageLimit;
        this.depthLimit = depthLimit;
    }

    public void bfsAlg() {
        if (depthLevel.get() == 1) {
            clearLastData();
            checkLevelStatisticsCollected();
        } else {
            linkQueue.addAll(lowerstLinkQueue);
        }
        initializeExecutorsLookup(depthLimit, pageLimit);

    }

    private void initializeExecutorsLookup(Integer depthLimit, Integer pageLimit) {
        linkQueue
                .forEach(link -> threadPoolExecutor.submit(() -> {
                    try {
                        if (depthLevel.get() == depthLimit || marked.size() > pageLimit) {
                            showStatisticsAndFinish();
                        }
                        if (!marked.contains(link) && link != null) {
                            marked.add(link);
                            results.add(TextFinder.getResult(desiredWords, link));
                            lowerstLinkQueue.addAll(LinkHandler.getLinks(link));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
    }

    private synchronized void showStatisticsAndFinish() {
        showResult();
        showTop10();
        System.exit(0);
    }

    private void checkLevelStatisticsCollected() {
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(() -> {
            System.out.println("Active threads" + threadPoolExecutor.getActiveCount());
            if (threadPoolExecutor.getActiveCount() == 0) {
                depthLevel.incrementAndGet();
                bfsAlg();
            }
        }, 5L, 10L, TimeUnit.SECONDS);
    }


    private void clearLastData() {
        new CsvWriter(ALL_STAT_DATA).cleanFiles();
        new CsvWriter(TOP_10_PAGES).cleanFiles();
    }

    public void showResult() {
        Printer printer = new CsvWriter(ALL_STAT_DATA);
        log.info("\n\nResults:");
        log.info("Websites crawled : " + marked.size() + "\n");

        for (Result result : results) {
            log.info("*" + result.toString());
            printer.print(result);
        }
    }

    public void showTop10() {
        Printer printer = new CsvWriter(TOP_10_PAGES);
        System.out.println("\n\nTop 10:");
        PriorityQueue<Result> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
        priorityQueue.addAll(results);
        for (int i = 0; i < 10; i++) {
            Result result = priorityQueue.poll();
            log.info(result);
            printer.print(result);
        }
    }

}



