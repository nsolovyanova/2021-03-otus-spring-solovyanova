package ru.otus.spring.dao;

import lombok.var;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class ParserCsvDaoSimple implements ParserCsvDao {

    @Override
    public List<Question> getParseQuestionsFromCsv(InputStream input) {
        List<Question> questionsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            var SplitChars = ';';
            String line = null;
            Scanner scanner = null;
            int index = 0;
            int id = 0;
            String text = "";
            String realAnswer = "";

            while ((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                List<String> dataList = new ArrayList<>();
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    if (index == 0) {
                        id =Integer.parseInt(data);
                    } else if (index == 1) {
                        text = data;
                    } else if (index == 2) {
                        realAnswer = data;
                    } else if (index == 3) {
                        if (data.indexOf(SplitChars) >= 0) {
                             dataList.addAll(Arrays.asList(data.split(String.valueOf(SplitChars))));
                        }
                    } else {
                        System.out.println("Некорректные данные::" + data);
                    }
                    index++;
                }
                index = 0;
                questionsList.add(new Question(id,text,realAnswer,dataList));
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return questionsList;
    }
}
