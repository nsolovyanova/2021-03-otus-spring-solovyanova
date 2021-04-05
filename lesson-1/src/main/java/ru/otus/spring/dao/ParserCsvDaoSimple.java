package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
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
    private List<Question> questionsList = new ArrayList<>();

    @Override
    public List<Question> getParseQuestionsFromCsv(InputStream input) throws IOException {
        // открываем файл
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        // считываем построчно
        String line = null;
        Scanner scanner = null;
        int index = 0;

        while ((line = reader.readLine()) != null) {
            Question emp = new Question();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String[] dataList = null;
                String data = scanner.next();
                if (index == 0) {
                    emp.setId(Integer.parseInt(data));
                } else if (index == 1) {
                    emp.setText(data);
                } else if (index == 2) {
                    emp.setRealAnswer(data);
                } else if (index == 3) {
                    var SplitChars = ';';
                    if (data.indexOf(SplitChars) >= 0) {
                        dataList = data.split(String.valueOf(SplitChars));
                    }
                    emp.setAnswers(dataList);
                } else {
                    System.out.println("Некорректные данные::" + data);
                }

                index++;
            }
            index = 0;
            questionsList.add(emp);
        }

        //закрываем наш ридер
        reader.close();

        return questionsList;

    }

    /*
    public Question getParseQuestionsFromCsv(InputStream input) {

        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader( new InputStreamReader(input));

        csv.setMappingStrategy(setColumMapping());
        csv.setCsvReader(csvReader);
        List list = csv.parse();

        for (Object object : list) {
            question = (Question) object;
            System.out.println(question);
        }

        return question;
    }

    private static ColumnPositionMappingStrategy setColumMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Question.class);
        String[] columns = new String[]{"id", "text", "answerA", "answerB", "answerC", "answerD"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
    */
}
