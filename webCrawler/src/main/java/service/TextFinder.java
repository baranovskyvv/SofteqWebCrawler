package service;

import model.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TextFinder {
    static final Logger log = LogManager.getRootLogger();

    static Result getResult(List<String> keyWords, String link) throws Exception {

        String textPage = PageHandler.parseForSearch(link);

        Map<String, Integer> result = new HashMap<>();
        for (String word : keyWords) {
            result.put(word, StringUtils.countMatches(textPage, word));
        }
        return new Result(link, result);
    }

}



