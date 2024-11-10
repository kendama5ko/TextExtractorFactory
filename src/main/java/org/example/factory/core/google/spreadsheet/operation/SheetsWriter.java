package org.example.factory.core.google.spreadsheet.operation;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.example.factory.api.google.spreadsheet.operation.DataWriter;
import org.example.factory.api.google.spreadsheet.operation.SheetManager;
import org.example.factory.api.google.spreadsheet.operation.DataReader;
import org.example.factory.api.google.spreadsheet.auth.SheetsService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SheetsWriter implements DataWriter {

    private final SheetsService sheetsService;
    //    private final DataReader dataReader;
    private static final String APPLICATION_NAME = "InjectToSheets";
    private static final String FILE_ID =
            "1mVZ_s2KjmEATftnagsp33w7UUpigiwDc42d1lA2IxMM";
    private static final String RANGE = "精密採点!D4:O4";
    private static final int SHEET_ID = 76597951;
    private Sheets sheets;


    public SheetsWriter(SheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    /**
     * Google Sheets APIからスプレッドシートに 'data' を書き込む
     *
     * @param data RegexExtractor.extractDataメソッドで抽出したデータ
     */
    @Override
    public void write(String[] data) {
        try {
            this.sheets = sheetsService.createSheets();

            // D4:N4のデータを取得
            DataReader sheetsReader = new SheetsDataReader();
            List<List<Object>> dataOfCells = sheetsReader.getData(sheets, RANGE);

            // データがあれば行を挿入、なければそのままデータを書き込む
            insertRowIfDataExists(dataOfCells);

            // dataの中身をdoubleかStringに変換する
            List<Object> rowData = convertData(data);

            // 配列の各要素をセルにセットする(Arrays.asListよりもCollections.singletonListの方が軽量）
            ValueRange injectData =
                    new ValueRange().setValues(Collections.singletonList(rowData));

            // Sheetsからスプレッドシートに書き込む
            sheets.spreadsheets().values()
                    .update(FILE_ID, RANGE, injectData)
                    .setValueInputOption("RAW") // データの形式はRAWもしくはUSER_ENTERED
                    .execute();


        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    /**
     * 受け取ったデータの中身がnullであれば何もしない
     * データがnullでなければ、insertRowAboveメソッドを呼び出し新たに行を挿入する
     *
     * @param dataOfCells getDataメソッドで得た指定した範囲のデータ
     * @throws IOException
     */
    private void insertRowIfDataExists(List<List<Object>> dataOfCells) throws IOException {
        // start と end の間に行が追加される
        int start = 3;
        int end = 4;

        SheetManager manager = new RowColumnManager(sheets, SHEET_ID, FILE_ID);

        // データがない場合
        if (dataOfCells == null || dataOfCells.isEmpty()) {
            System.out.println("D4:N4には既にデータがありません。行は追加せずデータを書き込みます。");

            // データがある場合
        } else {
            // 新しい行を追加
            manager.insertRowAbove(start, end);
            System.out.println("D4:N4にはデータがあります。行を追加してデータを書き込みます。");
        }
    }

    /**
     * 抽出したテキストの中身をdoubleかStringに変換する
     *
     * @param data RegexExtractor.extractDataで抽出したテキスト
     * @return doubleかStringに変換されたdata
     */
    public List<Object> convertData(String[] data) {
        List<Object> rowData = new ArrayList<>();

        // dataの要素のnullチェック
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                data[i] = "no data";
            }
        }

        for (String item : data) {
            // 数値かどうかの判定を行い、数値であればDoubleに変換、そうでなければそのまま文字列
            try {
                double numericValue = Double.parseDouble(item);
                rowData.add(numericValue); // 数値として追加
            } catch (NumberFormatException e) {
                rowData.add(item); // 文字列として追加
            }
        }

        return rowData;
    }
}
