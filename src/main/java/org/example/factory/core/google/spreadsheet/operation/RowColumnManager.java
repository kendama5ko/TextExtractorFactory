package org.example.factory.core.google.spreadsheet.operation;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.InsertRangeRequest;
import com.google.api.services.sheets.v4.model.Request;
import org.example.factory.api.google.spreadsheet.operation.SheetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RowColumnManager implements SheetManager {
    private final Sheets sheets;
    private final int SHEET_ID;
    private final String FILE_ID;

    public RowColumnManager(Sheets sheets, int sheetId, String fileId) {
        this.sheets = sheets;
        SHEET_ID = sheetId;
        FILE_ID = fileId;
    }

    /**
     * スプレッドシートに行を追加する。
     * startRowIndexとendRowIndexの間に行が追加される。
     * (endRowIndex - startRowIndex) の数値分の行が追加される
     *
     * @param startRowIndex 指定したindexの下に行を追加
     * @param endRowIndex   指定したindexの上まで行を追加
     * @throws IOException
     */
    public void insertRowAbove(int startRowIndex, int endRowIndex) throws IOException {
        // Google Sheets APIのリクエストを作成し、行を追加する処理
        List<Request> requests = new ArrayList<>();

        // endRowIndexで指定した行の上に行を挿入する (新しいendRowIndexの行を作成)
        requests.add(new Request()
                .setInsertRange(new InsertRangeRequest()
                        .setRange(new GridRange()
                                // シートIDを指定
                                .setSheetId(SHEET_ID)

                                // どの行の下に追加したいかを指定 (今回は3行目の下に追加したいので3)
                                .setStartRowIndex(startRowIndex)

                                // どの行の上まで追加したいかを指定（今回は4）
                                .setEndRowIndex(endRowIndex))

                        .setShiftDimension("ROWS"))); // 行の挿入を指定

        // リクエストをバッチで送信
        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheets.spreadsheets().batchUpdate(FILE_ID, body).execute();
    }
}
