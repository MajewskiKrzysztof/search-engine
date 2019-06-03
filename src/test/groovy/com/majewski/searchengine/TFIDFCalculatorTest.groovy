package com.majewski.searchengine

import org.testng.Assert
import spock.lang.Specification

class TFIDFCalculatorTest extends Specification {

    def calculator = new TFIDFCalculator()

    def "tf() should return valid frequency in document for given query"() {
        given:
        def document = ["This", "String", "is", "a", "tokenize", "StRing"]
        def query = "string"

        when:
        def frequency = calculator.tf(document, query)

        then:
        Assert.assertTrue(frequency == 2d / 6)
    }

    def "tf() should return 0 if query is not found in document"() {
        given:
        def document = ["This", "String", "is", "a", "tokenize", "StRing"]
        def query = "ninja"

        when:
        def frequency = calculator.tf(document, query)

        then:
        Assert.assertTrue(frequency == 0)
    }

    def "tf() should return 0 if document is empty"() {
        given:
        def document = []
        def query = "ninja"

        when:
        def frequency = calculator.tf(document, query)

        then:
        Assert.assertTrue(frequency == 0)
    }

    def "tf() should return 0 if query is null"() {
        given:
        def document = []
        def query = null

        when:
        def frequency = calculator.tf(document, query)

        then:
        Assert.assertTrue(frequency == 0)
    }

    def "idf() should return valid idf of documents with given query"() {
        given:
        def documents = [
                "test string 1",
                "not this time :p",
                "JUST A TEST",
                "aNoThEr string with test"
        ]
        def query = "test"

        when:
        def idf = calculator.idf(documents, query)

        then:
        Assert.assertTrue(idf == Math.log(1d + (4d / 3)))
    }

    def "idf() should return valid idf if all documents contain the query"() {
        given:
        def documents = [
                "test string 1",
                "perfect test it is",
                "JUST A TEST",
                "aNoThEr string with test"
        ]
        def query = "test"

        when:
        def idf = calculator.idf(documents, query)

        then:
        Assert.assertTrue(idf == Math.log(2d))
    }

    def "idf() should return 0 if query is not found in any document"() {
        given:
        def documents = [
                "test string 1",
                "not this time :p",
                "JUST A TEST",
                "aNoThEr string with test"
        ]
        def query = "ninja"

        when:
        def idf = calculator.idf(documents, query)

        then:
        Assert.assertTrue(idf == 0)
    }

    def "idf() should return 0 if documents list is empty"() {
        given:
        def documents = []
        def query = "ninja"

        when:
        def idf = calculator.idf(documents, query)

        then:
        Assert.assertTrue(idf == 0)
    }

    def "idf() should return 0 if query is null"() {
        given:
        def documents = [
                "test string 1",
                "not this time :p",
                "JUST A TEST",
                "aNoThEr string with test"
        ]
        def query = null

        when:
        def idf = calculator.idf(documents, query)

        then:
        Assert.assertTrue(idf == 0)
    }

    def "tfIdf() should return valid tfIdf for document with given query"() {
        given:
        def idf = Math.log(4d / 3)

        def document = ["test", "String", "document"]
        def query = "test"

        when:
        def tfIdf = calculator.tfIdf(document, query, idf)

        then:
        Assert.assertTrue(tfIdf == 1d / 3 * idf)
    }

    def "tfIdf() should return 0 if query is not found in given document"() {
        given:
        def idf = Math.log(4d / 3)

        def document = ["test", "String", "document"]
        def query = "ninja"

        when:
        def tfIdf = calculator.tfIdf(document, query, idf)

        then:
        Assert.assertTrue(tfIdf == 0)
    }

    def "tfIdf() should return 0 if document is empty"() {
        given:
        def idf = Math.log(4d / 3)

        def document = []
        def query = "ninja"

        when:
        def tfIdf = calculator.tfIdf(document, query, idf)

        then:
        Assert.assertTrue(tfIdf == 0)
    }

    def "tfIdf() should return 0 if query is null"() {
        given:
        def idf = Math.log(4d / 3)

        def document = ["test", "String", "document"]
        def query = null

        when:
        def tfIdf = calculator.tfIdf(document, query, idf)

        then:
        Assert.assertTrue(tfIdf == 0)
    }

}
