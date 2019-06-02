package com.majewski.searchengine;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.String.format;

class SearchEngineCLI {

    public static void main(String[] args) {
        var sc = new Scanner(System.in);

        System.out.println("How many documents you want to insert?");
        var documentsCount = Integer.parseInt(sc.nextLine());
        System.out.println(format("Enter %d documents separated by new lines", documentsCount));
        var documents = new ArrayList<String>();
        while(documentsCount > 0) {
            var newDocument = sc.nextLine();
            documents.add(newDocument);
            documentsCount--;
        }

        System.out.println("Enter the term you want to search for");
        var query = sc.nextLine();

        var searchEngine = new SearchEngine();
        var documentsWithQuery = searchEngine.search(documents, query);
        System.out.println();
        System.out.println(format("Query found in documents: %s", documentsWithQuery));
    }

}
