package org.poker

import groovy.json.JsonSlurper
import org.jooq.DSLContext
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

public class ServerSpec extends Specification {
    public static final int NUMBER_OF_CARDS_IN_STANDARD_DECK = 52

    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    @Shared
    DSLContext context

    @Shared
    JsonSlurper slurper = new JsonSlurper()

    def setupSpec() {
    }

    def cleanupSpec() {
    }

    def "get cards"() {
        when:
        get('api/v1/cards')

        then:
        with(response) {
            statusCode == 200
            def cards = slurper.parseText(response.body.text)
            cards.size() == NUMBER_OF_CARDS_IN_STANDARD_DECK
        }
    }
}