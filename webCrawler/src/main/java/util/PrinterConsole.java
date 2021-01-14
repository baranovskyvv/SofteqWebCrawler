package util;

import model.Result;

public class PrinterConsole implements Printer {
    @Override
    public void print(String s) {
        System.out.println(s);
    }

    @Override
    public void print(Result result) {
        System.out.println(result.toString());
    }
}
