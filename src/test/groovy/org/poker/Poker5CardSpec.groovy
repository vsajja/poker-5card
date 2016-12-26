package org.poker

import groovy.json.JsonSlurper
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
public class Poker5CardSpec extends Specification {
    @AutoCleanup
    @Shared
    GroovyRatpackMainApplicationUnderTest sut = new GroovyRatpackMainApplicationUnderTest()

    @Delegate
    TestHttpClient httpClient = sut.httpClient

    @Shared
    DSLContext context

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    def setupSpec() {
//        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
//            builder.props(
//                    ['postgres.user'        : 'xgzyfcwcbfyode',
//                     'postgres.password'    : '0cdd1c4170b7b7543849ea7b7676ca9356cf8c30336540041d5052708479e7af',
//                     'postgres.portNumber'  : 5432,
//                     'postgres.databaseName': 'd1u98ejkb4rplf',
//                     'postgres.serverName'  : 'ec2-54-221-210-126.compute-1.amazonaws.com'])
//            builder.build()
//        }
//        DataSource dataSource = new PostgresModule().dataSource(configData.get('/postgres', PostgresConfig))
//        context = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    def cleanupSpec() {

    }

    def "ping poker"() {
        when:
        get('api/v1/poker')

        then:
        response.statusCode == 200
    }
}