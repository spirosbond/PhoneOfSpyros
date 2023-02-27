package phoneofspyros_grails
import grails.rest.*

@Resource(uri='/features', formats=['json', 'xml'])
class Feature {

    Date dateCreated
    Date lastUpdated
    FeatureNames name
    String value_str
    Float value_num
    String description
    Boolean hasFeature = false
    static belongsTo = [phone: Phone]

    static constraints = {
        name blank: false, nullable: false
        name unique: ['value_str', 'value_num', 'phone']
        value_str blank: false, nullable: false
        value_num blank: true, nullable: true
        description blank: true, nullable: true
        hasFeature blank: false, nullable: false
        phone blank: false, nullable: false
    }

    static mapping = {
        description type: 'text'
        value_str type: 'text'
//        phone lazy: false
    }

}

enum FeatureNames {
    ip, //
    screen_size, //
    storage,//
    ram, //
    chipset, //
    network, //
    release_date, //
    price,
    n_of_sims, //
    os,//
    screen_tech,//
    screen_hz,//
    wireless_charging,//
    battery_size, //
    camera_px //
}
