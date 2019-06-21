package com.majewski.searchengine;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

class DocumentTokenizer {

    static List<String> tokenizeDocumentContent(String documentContent) {
        if (isBlank(documentContent)) {
            return List.of();
        }

        return Arrays.asList(documentContent.split("\\s+"));
    }

}
