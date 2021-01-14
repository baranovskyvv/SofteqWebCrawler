package view;

import util.Printer;
import util.PrinterConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Console implements Viewer {
    private static Printer printer = new PrinterConsole();

    static {
        printer.print("Добрый день! Вас приветствует программа \"WebCrawler\"!");

    }

    public String showMainMenu() {
        printer.print("Введите номер функции которая для Вас необходима:" + "\n" +
                "1. Использовать параметры по умолчанию." + "\n" +
                "2. Использовать параметры поиска по частным критериям." + "\n" +
                "0. Выход");
        return getValue();
    }

    public void exit() {
        printer.print("Спасибо, что пользуетесь нашим программным продуктом!");
    }

    public String getValue() {
        String value = null;
        try {
            value = enterValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value.strip();
    }

    private String enterValue() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public void gettingWrongValueInMainMenu() {
        printer.print("Ошибка. Данного значения не существует. Проверьте номер функции и попробуйте еще раз.");
    }

    public String getSeed() {
        printer.print("Введите начальный сайт:");
        return getValue();
    }

    public int getPageLimit() {
        printer.print("Введите лимит страниц:");
        return Integer.parseInt(getValue());
    }

    public int getLevelDepthLimit() {
        printer.print("Введите ограничение по глубине:");
        return Integer.parseInt(getValue());
    }

    public List<String> getWords() {
        printer.print("Введите слово и нажмите Enter для добавления его в список(чтобы окончить ввод отавьте строку пустой и нажмите Enter):");
        List<String> words = new ArrayList<>();
        String word;
        do {
            word = getValue();
            if (!word.isBlank()) {
                words.add(word);
            }
        } while (!word.isBlank());
        return words;
    }
}