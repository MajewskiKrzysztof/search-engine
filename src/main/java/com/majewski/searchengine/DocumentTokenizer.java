package com.majewski.searchengine;

import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

class DocumentTokenizer {

    static List<String> tokenizeDocument(String document) {
        if (isBlank(document))
            return List.of();

        return Arrays.asList(document.split("\\s+"));
    }

}
