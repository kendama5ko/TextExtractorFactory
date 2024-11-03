package org.example.factory.api.extractor;

public interface DataExtractor {
    /**
     * 与えられた文字列 `text` から正規表現に一致する部分を見つけ出し、
     * それを `String[]` 配列として返します。
     * 配列の各要素は、一致したデータを順番に格納します。
     *
     * @param text 対象となるテキスト
     * @return 正規表現に一致したデータの配列。
     *         一致するデータがない場合は、空の配列を返します。
     */
    String[] extractData(String text);
}
