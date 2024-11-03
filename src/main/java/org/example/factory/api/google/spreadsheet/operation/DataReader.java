package org.example.factory.api.google.spreadsheet.operation;

import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.util.List;

public interface DataReader {
    /**
     * sheetsインスタンスのスプレッドシートからrangeで指定された範囲のデータを取得する
     *
     * @param range 取得したいデータの範囲を記述（例： "シート名!A1:Z1" ）
     * @return 指定された範囲のデータを List<List<Object>> で返す
     * @throws IOException
     */
    List<List<Object>> getData(Sheets sheets, String range) throws IOException;
}
