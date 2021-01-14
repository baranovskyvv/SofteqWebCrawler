package view;

import java.util.List;

public interface Viewer {
    String showMainMenu();

    void exit();

    String getValue();

    void gettingWrongValueInMainMenu();

    String getSeed();

    int getPageLimit();

    int getLevelDepthLimit();

    List<String> getWords();
}
