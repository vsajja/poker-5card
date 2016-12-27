import com.zaxxer.hikari.HikariConfig
import groovy.json.JsonSlurper
import jooq.generated.tables.pojos.Card
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.config.ConfigData
import ratpack.config.ConfigDataBuilder
import ratpack.groovy.sql.SqlModule
import ratpack.handling.RequestLogger
import ratpack.hikari.HikariModule
import org.poker.postgres.PostgresConfig
import org.poker.postgres.PostgresModule

import javax.sql.DataSource

import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json
import static ratpack.jackson.Jackson.jsonNode

import static jooq.generated.Tables.*;

final Logger log = LoggerFactory.getLogger(this.class)

ratpack {
    bindings {
        final ConfigData configData = ConfigData.of { ConfigDataBuilder builder ->
            builder.props(
                    ['postgres.user'        : 'xgzyfcwcbfyode',
                     'postgres.password'    : '0cdd1c4170b7b7543849ea7b7676ca9356cf8c30336540041d5052708479e7af',
                     'postgres.portNumber'  : 5432,
                     'postgres.databaseName': 'd1u98ejkb4rplf',
                     'postgres.serverName'  : 'ec2-54-221-210-126.compute-1.amazonaws.com'])
            builder.build()
        }

        bindInstance PostgresConfig, configData.get('/postgres', PostgresConfig)

        module HikariModule, { HikariConfig config ->
            config.dataSource =
                    new PostgresModule().dataSource(
                            configData.get('/postgres', PostgresConfig))
        }
        module SqlModule
    }

    handlers {
        all RequestLogger.ncsa(log)

        get {
            redirect('index.html')
        }

        prefix('api/v1') {
            all {
                response.headers.add('Access-Control-Allow-Origin', '*')
                response.headers.add('Access-Control-Allow-Headers', 'origin, x-requested-with, content-type')
                response.headers.add('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
                next()
            }

            path('cards') {
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

                        List<Card> cards = context.selectFrom(CARD)
                                .orderBy(CARD.RANK.asc())
                                .fetch()
                                .into(Card.class)

                        assert cards.size() == 52

                        render json(cards)
                    }
                }
            }

            path('hands') {
                byMethod {
                    post {
                        parse(jsonNode()).map { params ->
                            List<Card> cardsA = []
                            List<Card> cardsB = []

                            JsonSlurper slurper = new JsonSlurper()

                            params[0].each {
                                def cardObj = slurper.parseText(it.toString())
                                def card = new Card(cardObj.cardId, cardObj.name, cardObj.rank, cardObj.rankStr,
                                        cardObj.suit, cardObj.imageSrc)
                                cardsA.add(card)
                            }

                            params[1].each {
                                def cardObj = slurper.parseText(it.toString())
                                def card = new Card(cardObj.cardId, cardObj.name, cardObj.rank, cardObj.rankStr,
                                        cardObj.suit, cardObj.imageSrc)

                                cardsB.add(card)
                            }

                            log.info(cardsA.toString())
                            log.info(cardsB.toString())

                            Hand handA = new Hand(cardsA)
                            Hand handB = new Hand(cardsB)

                            log.info(handA.handType.ordinal().toString())
                            log.info(handB.handType.ordinal().toString())
                        }.then {
                            println "then"
                            render "then"
                        }
                    }
                }
            }
        }

        // code to initialize the cards table in the database
        /*
        prefix('init/data') {
            get('poker') {
                new File('ui/app/images/cards').eachFile {
                    String name = it.name - '.png'
                    String imageSrc = 'images/cards/' + it.name

                    def card = name.split('_of_')

                    String rankStr = card[0]
                    String suit = card[1]

                    Map ranks = [
                            '2' : 2,
                            '3' : 3,
                            '4' : 4,
                            '5' : 5,
                            '6' : 6,
                            '7' : 7,
                            '8' : 8,
                            '9' : 9,
                            '10': 10,
                            'jack' : 11,
                            'queen' : 12,
                            'king' : 13,
                            'ace' : 14
                    ]

//                    DataSource dataSource = registry.get(DataSource.class)
//                    DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)
//                    log.info("inserting: $name")
//                    context.insertInto(CARD)
//                            .set(CARD.NAME, name)
//                            .set(CARD.IMAGE_SRC, imageSrc)
//                            .set(CARD.RANK_STR, rankStr)
//                            .set(CARD.RANK, ranks[rankStr])
//                            .set(CARD.SUIT, suit)
//                            .execute()
                }

                render 'init/data/poker'
            }
        }
        */

        files {
            dir 'dist'
        }
    }
}

public enum HandType {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH
}

class Hand {
    List<Card> cards = []
    HandType handType

    public Hand(List<Card> cards) {
        this.cards = cards
        this.handType = evaluate()
    }

    HandType evaluate() {
        return HandType.STRAIGHT_FLUSH
    }

    public boolean isStraight() {

    }

    public boolean isFlush() {
        Map suits = cards.groupBy { Card card -> card.suit }
        return suits.findAll { k, v -> v.size() == 5 }.size() == 1
    }

    public boolean isFourOfAKind() {
        Map ranks = cards.groupBy { Card card -> card.rank }
        return ranks.findAll { k, v -> v.size() == 4 }.size() == 1
    }
}