package org.example;

import org.example.factory.Factory;
import org.example.factory.core.service.TextExtractorAndWriter;
import org.example.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        String inputText = """

85.155点
ビブラートボーナス
0.423点


全国平均84.401点
音程安定性表現力リズムビブラート＆
ロングトーン音程安定性表現力リズムビブラート＆
ロングトーン
分析レポート
なかなかの盛り上げ上手ですね。次はアプローチも気を抜かずに最後まで張り切ってみて。
音程
正確率78%
表現力
76点　/　抑揚54点　しゃくり16回　こぶし10回　フォール2回
ロングトーン
上手さ
安定性
震えがちまっすぐ
リズム
タメ走り
ビブラート上手さ合計5.4秒11回
タイプボックス形(A-1)
声域
                
                """; // 入力テキスト

        Factory factory = new TextExtractorAndWriter();

        factory.createProduct();

        MainWindow mainWindow = new MainWindow();
        mainWindow.makeWindow(factory);
    }
}
