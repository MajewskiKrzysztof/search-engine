package com.majewski.searchengine

import org.testng.Assert
import spock.lang.Specification

class SearchEngineTest extends Specification {

    def searchEngine = new SearchEngine()

    def "search() should return two document containing query word with case insensitivity"() {
        given:
        def documents = [
                "This is not the string you are looking for!",
                "more  then   one    space and few      tabs in     me and test",
                "&^%@#(*&%@#*& sjfdsa d asdasd kaskd asbjkh gbkas d98^*&A% *12 1§2 1 2 13123 .. ////",
                "I hAvE tEst inside ME",
                "just a simple document",
                ""
        ]
        def query = "test"

        when:
        def filteredDocuments = searchEngine.search(documents, query)

        then:
        Assert.assertTrue(filteredDocuments.size() == 2)
        Assert.assertTrue(filteredDocuments.contains("document 2"))
        Assert.assertTrue(filteredDocuments.contains("document 4"))
    }

    def "search() should return two document containing query word and ignore all spaces in query with case insensitivity"() {
        given:
        def documents = [
                "This is not the string you are looking for!",
                "more  then   one    space and few      tabs in     me and test",
                "&^%@#(*&%@#*& sjfdsa d asdasd kaskd asbjkh gbkas d98^*&A% *12 1§2 1 2 13123 .. ////",
                "I hAvE tEst inside ME",
                "just a simple document",
                ""
        ]
        def query = "   test "

        when:
        def filteredDocuments = searchEngine.search(documents, query)

        then:
        Assert.assertTrue(filteredDocuments.size() == 2)
        Assert.assertTrue(filteredDocuments.contains("document 2"))
        Assert.assertTrue(filteredDocuments.contains("document 4"))
    }

    def "search() should return three documents containing 'test' word ordered by query word occurrence with case insensitivity"() {
        given:
        def documents = [
                "This is not the string you are looking for!",
                "just a simple test TEST",
                "&^%@#(*&%@#*& sjfdsa d asdasd kaskd asbjkh gbkas d98^*&A% *12 1§2 1 2 13123 .. ////",
                "I hAvE tEst inside ME",
                "just a simple document",
                "test test test test",
                ""
        ]
        def query = "test"

        when:
        def filteredDocuments = searchEngine.search(documents, query)

        then:
        Assert.assertTrue(filteredDocuments.size() == 3)
        Assert.assertEquals(filteredDocuments[0], ("document 6"))
        Assert.assertEquals(filteredDocuments[1], ("document 2"))
        Assert.assertEquals(filteredDocuments[2], ("document 4"))
    }

    def "search() should return empty list if query word is not found in any document"() {
        given:
        def documents = [
                "This is not the string you are looking for!",
                "more  then   one    space and few      tabs in     me",
                "&^%@#(*&%@#*& sjfdsa d asdasd kaskd asbjkh gbkas d98^*&A% *12 1§2 1 2 13123 .. ////",
                "I hAvE tExT inside ME",
                "just a simple document",
                ""
        ]
        def query = "test"

        when:
        def filteredDocuments = searchEngine.search(documents, query)

        then:
        Assert.assertTrue(filteredDocuments.size() == 0)
    }

    def "search() should return empty list if documents collection is empty"() {
        given:
        def documents = []
        def query = "test"

        when:
        def filteredDocuments = searchEngine.search(documents, query)

        then:
        Assert.assertTrue(filteredDocuments.size() == 0)
    }

    def "search() should throw IllegalArgumentException exception if documents collection is null"() {
        given:
        def documents = null
        def query = "test"

        when:
        searchEngine.search(documents, query)

        then:
        def e = thrown IllegalArgumentException
        Assert.assertEquals(e.getMessage(), "Documents collection can't be null")
    }

    def "search() should throw IllegalArgumentException exception if query is null"() {
        given:
        def documents = []
        def query = null

        when:
        searchEngine.search(documents, query)

        then:
        def e = thrown IllegalArgumentException
        Assert.assertEquals(e.getMessage(), "Query must not be blank")
    }

    def "search() should throw IllegalArgumentException exception if query is blank"() {
        given:
        def documents = []
        def query = ""

        when:
        searchEngine.search(documents, query)

        then:
        def e = thrown IllegalArgumentException
        Assert.assertEquals(e.getMessage(), "Query must not be blank")
    }

}
