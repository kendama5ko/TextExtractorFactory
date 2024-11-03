package org.example.factory.api.google.spreadsheet.auth;

import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface SheetsService {
    /**
     * Google Sheets APIのインスタンスを作成して返します。
     * 認証情報を用いてGoogle Sheets APIにアクセスできるように設定されます。
     *
     * @return Google Sheets APIインスタンス
     * @throws IOException 認証情報の読み込みに失敗した場合
     * @throws GeneralSecurityException セキュリティ設定に関連するエラーが発生した場合
     */
    Sheets createSheets() throws IOException, GeneralSecurityException;
}
