package ru.otus.spring.dao;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.ParseQuestionsException;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class ParserCsvDaoSimple implements ParserCsvDao {
    private static final Logger logger = LoggerFactory.getLogger(ParserCsvDaoSimple.class);

    private Question getData(Scanner scanner) {
        var splitChars = ';';
        int index = 0;
        int id = 0;
        String text = "";
        String realAnswer = "";
        List<String> dataList = new ArrayList<>();
        scanner.useDelimiter(",");

        while (scanner.hasNext()) {
            String data = scanner.next();
            if (index == 0) {
                id = Integer.parseInt(data);
            } else if (index == 1) {
                text = data;
            } else if (index == 2) {
                realAnswer = data;
            } else if (index == 3) {
                if (data.indexOf(splitChars) >= 0) {
                    dataList.addAll(Arrays.asList(data.split(String.valueOf(splitChars))));
                }
            } else {
                logger.error("Некорректные данные::" + data);
            }
            index++;
        }

        return new Question(id, text, realAnswer, dataList);
    }

    @Override
    public List<Question> getParseQuestionsFromCsv(InputStream input) {
        List<Question> questionsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line = null;
            Scanner scanner = null;
            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                questionsList.add(getData(scanner));
            }
        } catch (Exception e) {
            throw new ParseQuestionsException("Error parsing from csv.", e);
        }

        return questionsList;
    }
}
