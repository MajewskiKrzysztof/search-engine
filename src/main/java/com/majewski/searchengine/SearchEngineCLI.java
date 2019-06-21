package com.majewski.searchengine;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.String.format;

class SearchEngineCLI {

    public static void main(String[] args) {
        var inputScanner = new Scanner(System.in);

        System.out.println("How many documents you want to insert?");
        var documentsCount = Integer.parseInt(inputScanner.nextLine());
        System.out.println(format("Enter %d documents separated by new lines", documentsCount));
        var documents = new ArrayList<Document>();
        while (documentsCount > 0) {
            var newContent = inputScanner.nextLine();
            documents.add(new Document(newContent));
            documentsCount--;
        }

        System.out.println("Enter the term you want to search for");
        var query = inputScanner.nextLine();

        var documentsWithQuery = SearchEngine.search(documents, query);
        System.out.println();
        System.out.println(format("Query found in documents: %s", documentsWithQuery));
    }

}
