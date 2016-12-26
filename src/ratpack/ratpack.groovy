import com.zaxxer.hikari.HikariConfig
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

            path('poker') {
                byMethod {
                    get {
                        DataSource dataSource = registry.get(DataSource.class)
                        DSLContext context = DSL.using(dataSource, SQLDialect.POSTGRES)

//                        def knots = context.selectCount().from(KNOT).asField('knots')
//                        def result = context.select(knots).fetchOneMap()

                        render json([])
                    }
                }
            }
        }

        prefix('test') {
            get('poker') {
                new File('ui/app/images/cards').eachFile {
                    println it.name
                    println 'images/cards/' + it.name
                }

                render 'test'
            }
        }

        files {
            dir 'dist'
        }
    }
}