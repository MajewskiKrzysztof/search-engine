package com.majewski.searchengine;

import java.util.List;

import static com.majewski.searchengine.DocumentTokenizer.tokenizeDocument;

class TFIDFCalculator {

    private static double tf(List<String> document, String query) {
        if (document.isEmpty())
            return 0;

        var queryCount = document.stream().filter(word -> word.equalsIgnoreCase(query)).count();
        return ((double) queryCount) / document.size();
    }

    /**
     * Using logarithmically scaled frequency to avoid log(1) when all documents contain query.
     */
    static double idf(List<String> documents, String query) {
        var documentsWithQuery = 0d;
        for (var document : documents) {
            var docHasWord = tokenizeDocument(document).stream().anyMatch(word -> word.equalsIgnoreCase(query));
            if (docHasWord)
                documentsWithQuery++;
        }

        return documentsWithQuery == 0 ? 0 : Math.log(1d + (documents.size() / documentsWithQuery));
    }

    static double tfIdf(List<String> document, String query, double idf) {
        return tf(document, query) * idf;
    }

}
