package org.example.factory.api.google.spreadsheet.operation;

import java.io.IOException;

public interface SheetManager {
    void insertRowAbove(int start, int end) throws IOException;
}
