package ru.otus.spring.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class ConsoleReaderImpl implements ConsoleReader {

    private final BufferedReader reader;

    public ConsoleReaderImpl() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String getNextLine(String message) {
        String string = "";
        System.out.println(message);
        try {
            string = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
