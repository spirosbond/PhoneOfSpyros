package phoneofspyros_grails
import grails.rest.*

@Resource(uri='/brands', formats=['json', 'xml'])
class Brand {
    Date dateCreated
    Date lastUpdated
//    Integer n_devices
    String name
    String url

    static constraints = {
        name unique: true, blank: false, nullable: false
//        n_devices min: 0
        url blank: true, nullable: true
    }
}
