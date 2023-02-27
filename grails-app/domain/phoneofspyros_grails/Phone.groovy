package phoneofspyros_grails
import grails.rest.*

import java.time.LocalDate

@Resource(uri='/phones', formats=['json', 'xml'])
class Phone {

    Date dateCreated
    Date lastUpdated
    Brand brand
    String model
    String description
    String image
    String url
    LocalDate release_date
    static hasMany = [features: Feature]

    static mapping = {
        features fetch: 'join'
//        features lazy: false
//        brand fetch: 'join'
//        brand lazy: false
        description type: 'text'
    }

//    static fetchMode = [features: 'eager']

    static constraints = {
        brand blank: false, nullable: false
        model unique: true, blank: false, nullable: false
        description blank: false, nullable: false
        image blank: true, nullable: true
        url unique: true, blank: false, nullable: false
        release_date blank: true, nullable: true
        features blank: true, nullable: true
    }

}
