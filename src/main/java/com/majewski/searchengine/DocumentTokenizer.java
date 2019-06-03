package com.majewski.searchengine;

import java.util.Arrays;
import java.util.List;

class DocumentTokenizer {

    static List<String> tokenizeDocument(String document) {
        return Arrays.asList(document.split("\\s+"));
    }

}
