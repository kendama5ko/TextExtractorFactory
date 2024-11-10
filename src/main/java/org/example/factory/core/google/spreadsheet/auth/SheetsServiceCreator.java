package org.example.factory.core.google.spreadsheet.auth;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.example.factory.api.google.spreadsheet.auth.SheetsService;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class SheetsServiceCreator implements SheetsService {
    private static final String APPLICATION_NAME = "InjectToSheets";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/dataextractor-2024-10-17-3311967df236.json";

    /**
     * Google Sheets APIのインスタンスを作成して返します。
     * 認証情報を用いてGoogle Sheets APIにアクセスし、スプレッドシートの読み書き等が利用可能になります。
     * <p>
     * [メソッドの流れ]
     * Sheets.Builder
     * └─Google Sheets APIを操作するためのビルダーのインスタンスを作成
     * <p>
     * HttpCredentialsAdapter
     * └─ GoogleのHttpRequestに認証情報を適用するためのクラスです。
     * <p>
     * GoogleNetHttpTransport.newTrustedTransport()
     * └─ 安全なHTTP通信を行うために、TLS（SSL）通信をサポートする信頼できるトランスポート層を作成します。
     * <p>
     * GsonFactory
     * └─ JSON形式のデータをJavaオブジェクトに変換したり、その逆を行うためのGoogleのライブラリです。
     * <p>
     * GsonFactory.getDefaultInstance()
     * └─ JSONデータのシリアライズ/デシリアライズを行うためのGsonライブラリのファクトリインスタンスを作成します。
     * <p>
     * build()
     * └─ 設定したHTTPトランスポート、JSONファクトリ、認証情報を使ってSheetsオブジェクトを構築します。
     *
     * @return Google Sheets APIインスタンス
     * @throws IOException              認証情報の読み込みに失敗した場合
     * @throws GeneralSecurityException セキュリティ設定に関連するエラーが発生した場合
     */
    @Override
    public Sheets createSheets() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = getCredentials();

        HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credentialsAdapter)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * サービスアカウントの認証情報を取得し、Google Sheets APIに対するスプレッドシートの読み書き権限を持つ認証情報を作成する。
     * <p>
     * [メソッドの流れ]
     * FileInputStream serviceAccountStream
     * └─ 作成したGCPサービスアカウントのキー（このキーは同じものを再DL不可のため注意）を読み込みます。
     * <p>
     * ServiceAccountCredentials.fromStream(serviceAccountStream)
     * └─ キーからサービスアカウントの認証情報を作成します。
     * <p>
     * createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS))
     * └─ 認証情報にスコープを適用します。今回はSheetsScopes.SPREADSHEETSでスプレッドシートへのアクセス権限を設定。
     *
     * @return Google Sheets APIのスコープにアクセスできる認証情報
     * @throws IOException サービスアカウント認証情報のファイルを読み込む際にエラーが発生した場合
     */
    public GoogleCredentials getCredentials() throws IOException {
        FileInputStream serviceAccountStream =
                new FileInputStream(CREDENTIALS_FILE_PATH);

        return ServiceAccountCredentials.fromStream(serviceAccountStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }
}
