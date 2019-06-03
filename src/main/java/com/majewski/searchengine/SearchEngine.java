package com.majewski.searchengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.majewski.searchengine.DocumentTokenizer.tokenizeDocument;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trim;

class SearchEngine {

    List<String> search(List<String> documents, String queryInput) {
        var query = trim(queryInput);
        checkIfParametersAreValid(documents, query);

        var documentWordsMap = documentsToNameAndWordsMap(documents);
        var documentTFIDFMap = documentTFIDFMap(documents, documentWordsMap, query);

        return documentsContainingQuerySortedByTFIDF(documentTFIDFMap);
    }

    private void checkIfParametersAreValid(List<String> documents, String query) {
        if (isNull(documents))
            throw new IllegalArgumentException("Documents collection can't be null");

        if (isBlank(query))
            throw new IllegalArgumentException("Query must not be blank");
    }

    private Map<String, List<String>> documentsToNameAndWordsMap(List<String> documents) {
        var documentNameMap = new HashMap<String, List<String>>();
        var counter = 1;
        for (String document : documents) {
            documentNameMap.put(format("document %d", counter), tokenizeDocument(document));
            counter++;
        }
        return documentNameMap;
    }

    private Map<String, Double> documentTFIDFMap(List<String> documents, Map<String, List<String>> documentsMap,
                                                 String query) {
        var idf = TFIDFCalculator.idf(documents, query);
        return documentsMap.entrySet()
                           .stream()
                           .collect(toMap(Map.Entry::getKey, entry -> documentTFIDF(entry.getValue(), query, idf)));
    }

    private double documentTFIDF(List<String> documentWords, String query, double idf) {
        return TFIDFCalculator.tfIdf(documentWords, query, idf);
    }

    private List<String> documentsContainingQuerySortedByTFIDF(Map<String, Double> queryOccurrenceCount) {
        return queryOccurrenceCount.entrySet()
                                   .stream()
                                   .filter(entry -> entry.getValue() > 0)
                                   .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                                   .map(Map.Entry::getKey)
                                   .collect(Collectors.toList());
    }

}
