package org.example.factory.core.service;

import org.example.factory.Factory;
import org.example.factory.api.extractor.DataExtractor;
import org.example.factory.core.extractor.RegexExtractor;
import org.example.factory.api.google.spreadsheet.operation.DataWriter;
import org.example.factory.core.google.spreadsheet.operation.SheetsWriter;
import org.example.factory.api.google.spreadsheet.auth.SheetsService;
import org.example.factory.core.google.spreadsheet.auth.SheetsServiceCreator;

public class TextExtractorAndWriter extends Factory {
    private DataExtractor extractor;
    private DataWriter writer;

    public void createProduct() {
        this.extractor = new RegexExtractor();
        SheetsService sheetsService = new SheetsServiceCreator();
        this.writer = new SheetsWriter(sheetsService);
    }

    /**
     * RegexExtractor.extractDataメソッドからデータを抽出し、SheetsWriter.writeメソッドでデータを書き込む
     *
     * @param text オリジナルのテキストデータ
     */
    public void processText(String text) {
        String[] extractedData = extractor.extractData(text);
        writer.write(extractedData);
    }
}
