package org.example.factory.api.google.spreadsheet.operation;

public interface DataWriter {

    /**
     * Google Sheets APIからスプレッドシートに 'data' を書き込む
     *
     * @param data RegexExtractor.extractDataメソッドで抽出したデータ
     */
    void write(String[] data);
}
