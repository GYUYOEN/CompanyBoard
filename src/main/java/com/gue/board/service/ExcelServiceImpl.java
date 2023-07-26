package com.gue.board.service;

import com.gue.board.common.Data;
import com.gue.board.common.Types;
import com.gue.board.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

    private final DataRepository dataRepository;

    @Override
    public void loadAll() throws Exception {
        int count = 0;

        count += this.loadTest();
    }


    private int loadTest() throws Exception {
        int count = 0;
        Types.ExcelNumber dataType = Types.ExcelNumber.TEST;
        Reader reader = this.getFileReader(dataType);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(reader);

        dataRepository.delAllTest();

        try {
            for(CSVRecord record : records) {
                if(record.get(0).isEmpty()) continue;

                Data.Item info = new Data.Item();
                info.itemNo = Integer.parseInt(record.get("id"));
                info.itemName = record.get("name");
                info.itemPrice = Integer.parseInt(record.get("price"));
                info.itemStatus = Integer.parseInt(record.get("status"));
                info.condition = Integer.parseInt(record.get("condition"));
                info.conditionText = record.get("condition_text");
                info.textExplanation = record.get("textExp");

                dataRepository.addItem(info);

                ++count;
            }
        } catch (Exception e) {
            e.addSuppressed(new Exception("loadItem() / " + dataType.getFileName() + " : Line - " + (count + 1)));
            throw e;
        } finally {
            reader.close();
        }

        logger.info("GameServer data load : {} - {}", dataType.getFileName(), count);

        return count;
    }

    private Reader getFileReader(Types.ExcelNumber dataNumber) throws Exception {
        String fileName = dataRepository.getPathData() + dataNumber.getFileName();
        InputStream inputStream = new FileInputStream(fileName);
        InputStreamReader reader = new InputStreamReader(new BufferedInputStream(inputStream), "UTF-8");
        return reader;
    }
}
