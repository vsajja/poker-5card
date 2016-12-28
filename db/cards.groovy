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