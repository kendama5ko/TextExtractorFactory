package org.example.factory.core.extractor;

import org.example.factory.api.extractor.DataExtractor;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor implements DataExtractor {

    /**
     * 与えられた文字列 `text` から正規表現に一致する部分を見つけ出し、
     * それを `String[]` 配列として返します。
     * 配列の各要素は、一致したデータを順番に格納します。
     *
     * @param text 対象となるテキスト
     * @return 正規表現に一致したデータの配列。
     *         一致するデータがない場合は、空の配列を返します。
     */
    @Override
    public String[] extractData(String text) {
        String[] result = new String[12];

        // 点数
        Pattern scorePattern = Pattern.compile("(\\d{2,}\\.\\d{3})\\s+ビブラート");
        Matcher scoreMatcher = scorePattern.matcher(text);
        if (scoreMatcher.find()) {
            result[0] = scoreMatcher.group(1);  // 点数
        }

        // 全国平均
        Pattern avgPattern = Pattern.compile("全国平均\n(\\d{2,}\\.\\d{3})点");
        Matcher avgMatcher = avgPattern.matcher(text);
        if (avgMatcher.find()) {
            result[1] = avgMatcher.group(1);  // 全国平均
        }

        // 音程正確率
        Pattern accuracyPattern = Pattern.compile("正確率(\\d{2})%");
        Matcher accuracyMatcher = accuracyPattern.matcher(text);
        if (accuracyMatcher.find()) {
            result[2] = accuracyMatcher.group(1);  // 音程正確率
        }

        // 表現力
        Pattern expressionPattern = Pattern.compile("%\\s*(\\d{2})点　/");
        Matcher expressionMatcher = expressionPattern.matcher(text);
        if (expressionMatcher.find()) {
            result[3] = expressionMatcher.group(1);  // 表現力
        }

        // 抑揚
        Pattern modulationPattern = Pattern.compile("抑揚(\\d{2})点");
        Matcher modulationMatcher = modulationPattern.matcher(text);
        if (modulationMatcher.find()) {
            result[4] = modulationMatcher.group(1);  // 抑揚
        }

        // しゃくり
        Pattern shakuriPattern = Pattern.compile("しゃくり(\\d{1,2})回");
        Matcher shakuriMatcher = shakuriPattern.matcher(text);
        if (shakuriMatcher.find()) {
            result[5] = shakuriMatcher.group(1);  // しゃくり
        }

        // こぶし
        Pattern kobushiPattern = Pattern.compile("こぶし(\\d{1,2})回");
        Matcher kobushiMatcher = kobushiPattern.matcher(text);
        if (kobushiMatcher.find()) {
            result[6] = kobushiMatcher.group(1);  // こぶし
        }

        // フォール
        Pattern fallPattern = Pattern.compile("フォール(\\d{1,2})回");
        Matcher fallMatcher = fallPattern.matcher(text);
        if (fallMatcher.find()) {
            result[7] = fallMatcher.group(1);  // フォール
        }

        // ビブラート
        Pattern vibratoPattern = Pattern.compile("合計(\\d+\\.\\d+)秒\n(\\d+)回");
        Matcher vibratoMatcher = vibratoPattern.matcher(text);
        if (vibratoMatcher.find()) {
            result[8] = "合計" + vibratoMatcher.group(1) + "秒" + vibratoMatcher.group(2) + "回";  // ビブラート
        }

        // タイプ
        Pattern typePattern = Pattern.compile("タイプ(\\S+)");
        Matcher typeMatcher = typePattern.matcher(text);
        if (typeMatcher.find()) {
            result[9] = typeMatcher.group(1);  // タイプ
        }

        // 分析レポート
        Pattern reportPattern = Pattern.compile("ロングトーン.*?ロングトーン\\s*(.*?)\\s*正確率",
                Pattern.DOTALL);
        Matcher reportMatcher = reportPattern.matcher(text);
        if (reportMatcher.find()) {
            result[10] = reportMatcher.group(1).trim();  // 分析レポート
        }

        // 日付を取得
        LocalDate systemDate = LocalDate.now();
        result[11] = String.valueOf(systemDate);

        return result;
    }
}
