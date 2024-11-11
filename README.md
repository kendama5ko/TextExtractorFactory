# TextExtractorFactory
```mermaid
classDiagram
Main --> TextExtractorAndWriter
TextExtractorAndWriter --|> Factory
TextExtractorAndWriter --> SheetsWriter
SheetsWriter --> SheetsDataReader
SheetsWriter --> RowColumnManager
TextExtractorAndWriter --> RegexExtractor
```
```mermaid
classDiagram
    class TextExtractorAndWriter {
        +processText(String text)
        +createProduct()
    }
    class RegexExtractor {
        +extractData(String text)
    }
    class SheetsWriter {
        +write(String[] data)
    }
    
    TextExtractorAndWriter --> RegexExtractor: uses
    TextExtractorAndWriter --> SheetsWriter: uses
```

```mermaid
sequenceDiagram
    participant Client
    participant TextExtractorAndWriter
    participant RegexExtractor
    participant SheetsWriter
    
    Client->>TextExtractorAndWriter: processText(text)
    activate TextExtractorAndWriter
    
    TextExtractorAndWriter->>RegexExtractor: extractData(text)
    activate RegexExtractor
    RegexExtractor-->>TextExtractorAndWriter: extracted data
    deactivate RegexExtractor
    
    TextExtractorAndWriter->>SheetsWriter: write(extracted data)
    activate SheetsWriter
    SheetsWriter->>TextExtractorAndWriter: write complete
    deactivate SheetsWriter
    
    TextExtractorAndWriter-->>Client: process complete
    deactivate TextExtractorAndWriter
    

```