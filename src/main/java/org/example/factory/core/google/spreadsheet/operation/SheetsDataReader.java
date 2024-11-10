package org.example.factory.core.google.spreadsheet.operation;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.example.factory.api.google.spreadsheet.operation.DataReader;

import java.io.IOException;
import java.util.List;

public class SheetsDataReader implements DataReader {
    private static final String FILEID =
            "1mVZ_s2KjmEATftnagsp33w7UUpigiwDc42d1lA2IxMM";

    /**
     * sheetsインスタンスのスプレッドシートからrangeで指定された範囲のデータを取得する
     * @param range 取得したいデータの範囲を記述（例： "シート名!A1:Z1" ）
     * @return 指定された範囲のデータを List<List<Object>> で返す
     * @throws IOException
     */
    @Override
    public List<List<Object>> getData(Sheets sheets, String range) throws IOException {
        // rangeのデータを取得
        ValueRange response = sheets.spreadsheets().values()
                .get(FILEID, range)
                .execute();

        return response.getValues();
    }
}
