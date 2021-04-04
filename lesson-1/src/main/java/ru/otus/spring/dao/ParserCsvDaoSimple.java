package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ParserCsvDaoSimple implements ParserCsvDao {
    private Question question;

    @Override
    public Question getParseQuestionsFromCsv(InputStream input) {

        CsvToBean csv = new CsvToBean();

        CSVReader csvReader = new CSVReader(new InputStreamReader(input));

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
}
