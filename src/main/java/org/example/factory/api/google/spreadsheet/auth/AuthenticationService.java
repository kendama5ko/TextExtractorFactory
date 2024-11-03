package org.example.factory.api.google.spreadsheet.auth;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

public interface AuthenticationService {
    /**
     * サービスアカウントの認証情報を取得し、Google Sheets APIのスコープに対するアクセスを設定します。
     *
     * @return Google Sheets APIのスコープにアクセスできる認証情報
     * @throws IOException サービスアカウント認証情報のファイルを読み込む際にエラーが発生した場合
     */
    GoogleCredentials getCredentials() throws IOException;

}
