package phoneofspyros_grails


import grails.rest.*
import grails.converters.*

class AdvisorController {
	static responseFormats = ['json', 'xml']

    def index() {

    }

    def phones_top_flagship (Feature f) {

        println params

//        Feature[] features_ram = Feature.createCriteria().listDistinct {
//            and {
//                eq "name", FeatureNames.ram
//                ge "value_num", 8.0f
//            }
//        }
//        Feature[] features_screen = Feature.createCriteria().listDistinct {
//            and{
//                eq"name",FeatureNames.screen_size
//                between"value_num",5.0f,6.3f
//            }
//        }
//        Feature[] features_storage = Feature.createCriteria().listDistinct {
//            and{
//                eq "name",FeatureNames.storage
//                ge"value_num",256.0f
//            }
//        }


//            and{
//                eq"name",FeatureNames.wireless_charging
//                eq"value_str", "1"
//            }
//            and{
//                eq"name",FeatureNames.ip
//                isNotNull"value_str"
//            }
        Phone[] phones_result;
        Collection<?> keys = params.keySet()
        keys.eachWithIndex { key, index->

            if (!key.equals("action") && !key.equals("controller")) {
                println index
                println get_feature_enum(key) //print out params-name
                println params.get(key) //print out params-value
                Phone[] phones_filter = []
                switch (get_feature_enum(key)){
                    case FeatureNames.storage:
                        phones_filter = Phone.createCriteria().listDistinct {
                            features {
                                and {
                                    eq "name", get_feature_enum(get_feature_enum(key))
                                    ge "value_num", Float?.parseFloat(params.get(key))
                                }
                            }
                        }
                        break
                    case FeatureNames.wireless_charging:
                        if(params.get(key).equals("true"))
                        phones_filter = Phone.createCriteria().listDistinct {
                            features {
                                and {
                                    eq "name", get_feature_enum(key)
                                    eq "value_str", "1"
                                }
                            }
                        }
                        break
                    case FeatureNames.screen_size:
                        def range = params.get(key).split(',')
                        phones_filter = Phone.createCriteria().listDistinct {
                            features {
                                and {
                                    eq "name", get_feature_enum(get_feature_enum(key))
                                    between "value_num", Float?.parseFloat(range[0]), Float?.parseFloat(range[1])
                                }
                            }
                        }
                        break
                    case FeatureNames.camera_px:
                        break
                    case FeatureNames.battery_size:
                        break
                    case FeatureNames.chipset:
                        break
                    case FeatureNames.ip:
                        if(params.get(key).equals("true"))
                        phones_filter = Phone.createCriteria().listDistinct {
                            features {
                                and {
                                    eq "name", get_feature_enum(key)
                                    isNotNull"value_str"
                                }
                            }
                        }
                        break
                    case FeatureNames.n_of_sims:
                        break
                    case FeatureNames.network:
                        break
                    case FeatureNames.os:
                        def oss = params.get(key)?.split(',')
                        println "oss: " + oss
                        oss?.each { os ->
                            println "os: " + os
                            phones_filter += (Phone.createCriteria().listDistinct {
                                features {
                                    and {
                                        eq "name", get_feature_enum(key)
                                        ilike "value_str", "%" + os + "%"
                                    }
                                }


                            })
                        }
                        break
                    case FeatureNames.price:
                        break
                    case FeatureNames.ram:
                        phones_filter = Phone.createCriteria().listDistinct {
                            features {
                                and {
                                    eq "name", get_feature_enum(key)
                                    ge "value_num", Float?.parseFloat(params.get(key))
                                }
                            }
                        }
                        break
                    case FeatureNames.release_date:
                        break
                    case FeatureNames.screen_hz:
                        break
                    case FeatureNames.screen_tech:
                        break
                    default:
                        break
                }


                if(index==0){
                    println "first param"
                    phones_result = phones_filter
                    println phones_result
                } else if (phones_filter.size() > 0) {
                    println "filter down: " + phones_filter
                    phones_result = phones_result.findAll {phones_filter.contains(it)}
                    println phones_result
                }

            }
        }

        /*Phone[] phones_ram = Phone.createCriteria().listDistinct {

            features {
                    and {
                        eq "name", FeatureNames.ram
                        ge "value_num", 8.0f
                    }
            }
        }
        Phone[] phones_storage = Phone.createCriteria().listDistinct {
            features {
                    and {
                        eq "name",FeatureNames.storage
                        ge"value_num",128.0f
                    }
            }
        }
        Phone[] phones_screen = Phone.createCriteria().listDistinct {
            features {
                    and {
                        eq"name",FeatureNames.screen_size
                        between"value_num",5.0f,6.3f
                    }
            }
        }
        Phone[] phones_wireless_charge = Phone.createCriteria().listDistinct {
            features {
                    and {
                        eq"name",FeatureNames.wireless_charging
                        eq"value_str", "1"
                    }
            }
        }
        Phone[] phones_ip = Phone.createCriteria().listDistinct {
            features {
                    and {
                        eq"name",FeatureNames.ip
                        isNotNull"value_str"
                    }
            }
        }

        phones_result = phones_ram.findAll {phones_storage.contains(it)}.findAll {phones_screen.contains(it)}.findAll {phones_wireless_charge.contains(it)}.findAll {phones_ip.contains(it)}
       */
        JSON.use('deep')

//        println results.phone
//        println phones_ram
//        println phones_storage
//        println phones_screen
//        println phones_wireless_charge
//        println phones_ip
//        println phones_result

        render phones_result as JSON
    }

    def phones_top_budget () {

    }

    def phones_criteria () {

    }

    def get_feature_enum(def s){
        switch (s){
            case FeatureNames.storage.toString():
                return FeatureNames.storage
            case FeatureNames.wireless_charging.toString():
                return FeatureNames.wireless_charging
            case FeatureNames.screen_size.toString():
                return FeatureNames.screen_size
            case FeatureNames.camera_px.toString():
                return FeatureNames.camera_px
            case FeatureNames.battery_size.toString():
                return FeatureNames.battery_size
            case FeatureNames.chipset.toString():
                return FeatureNames.chipset
            case FeatureNames.ip.toString():
                return FeatureNames.ip
            case FeatureNames.n_of_sims.toString():
                return FeatureNames.n_of_sims
            case FeatureNames.network.toString():
                return FeatureNames.network
            case FeatureNames.os.toString():
                return FeatureNames.os
            case FeatureNames.price.toString():
                return FeatureNames.price
            case FeatureNames.ram.toString():
                return FeatureNames.ram
            case FeatureNames.release_date.toString():
                return FeatureNames.release_date
            case FeatureNames.screen_hz.toString():
                return FeatureNames.screen_hz
            case FeatureNames.screen_tech.toString():
                return FeatureNames.screen_tech
            default:
                return ""
        }
    }
}
