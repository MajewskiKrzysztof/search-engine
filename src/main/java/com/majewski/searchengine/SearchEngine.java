package com.majewski.searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.majewski.searchengine.DocumentTokenizer.tokenizeDocumentContent;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trim;

class SearchEngine {

    static List<String> search(List<Document> documents, String queryInput) {
        var query = trim(queryInput);
        checkIfParametersAreValid(documents, query);

        var documentWordsMap = generateDocumentNameAndWordsMap(documents);
        var documentTFIDFMap = generateDocumentTFIDFMap(documents, documentWordsMap, query);

        return getDocumentsWithQuerySortedByTFIDF(documentTFIDFMap);
    }

    private static void checkIfParametersAreValid(List<Document> documents, String query) {
        if (isNull(documents)) {
            throw new IllegalArgumentException("Documents collection can't be null");
        }

        if (isBlank(query)) {
            throw new IllegalArgumentException("Query must not be blank");
        }
    }

    private static Map<String, List<String>> generateDocumentNameAndWordsMap(List<Document> documents) {
        var documentNameAndWordsMap = new HashMap<String, List<String>>();
        var counter = 1;
        for (var document : documents) {
            var documentName = format("document %d", counter);
            var documentWords = tokenizeDocumentContent(document.getContent());
            documentNameAndWordsMap.put(documentName, documentWords);
            counter++;
        }
        return documentNameAndWordsMap;
    }

    private static Map<String, Double> generateDocumentTFIDFMap(List<Document> documents,
                                                         Map<String, List<String>> documentNameWordsMap, String query) {
        var idf = TFIDFCalculator.idf(documents, query);
        return documentNameWordsMap.entrySet()
                                   .stream()
                                   .collect(toMap(Map.Entry::getKey,
                                                  entry -> documentTFIDF(entry.getValue(), query, idf)));
    }

    private static double documentTFIDF(List<String> documentWords, String query, double idf) {
        return TFIDFCalculator.tfIdf(documentWords, query, idf);
    }

    private static List<String> getDocumentsWithQuerySortedByTFIDF(Map<String, Double> queryOccurrenceCount) {
        return queryOccurrenceCount.entrySet()
                                   .stream()
                                   .filter(entry -> entry.getValue() > 0)
                                   .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                   .map(Map.Entry::getKey)
                                   .collect(Collectors.toList());
    }

}
