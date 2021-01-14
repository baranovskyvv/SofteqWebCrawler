package util;

import model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter implements Printer {
    private static final Logger log = LogManager.getRootLogger();
    private String pathName;

    public CsvWriter(String pathName) {
        this.pathName = pathName;
    }

    public CsvWriter() {
    }

    public void cleanFiles() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(pathName), false))) {
            writer.print("");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public void print(Result result) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(pathName), true))) {
            if (result != null) {
                writer.print(result.toCSV());
                writer.print("\r");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void print(String result) {

    }
}
