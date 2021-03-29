package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.domain.Question;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class QuestionDaoSimple implements QuestionDao {
    private final String questionResourceFile;
    private Question question;

    @Override
    public Question getQuestionsFromCsv() {

        CsvToBean csv = new CsvToBean();

        try {
            InputStream resource = getClass().getClassLoader().getResourceAsStream(questionResourceFile);
            CSVReader csvReader = new CSVReader(new InputStreamReader(resource));

            csv.setMappingStrategy(setColumMapping());
            csv.setCsvReader(csvReader);
            List list = csv.parse();
            for (Object object : list) {
                question = (Question) object;
                System.out.println(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
