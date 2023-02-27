package phoneofspyros_grails

import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Transactional
class ParserService {
    static parser_api = "http://localhost:3001"
//    static parser_api = "http://gsmarena-api.herokuapp.com"
    static supported_brands = ["Samsung"/*, "Apple", "Google"*/]
    static jsonSlurper = new JsonSlurper()

    int parse_phones(){
        def connection = new URL(parser_api + "/brands").openConnection() as HttpURLConnection

        connection.setRequestProperty('Accept', 'application/json')

        // get the response code - automatically sends the request
        def resp = connection.inputStream.text
        def brands = jsonSlurper.parseText(resp)

        brands.each { brand ->

            if (brand.name in supported_brands) {
//                println brand
                Brand my_brand = new Brand(name: brand.name, url: brand.url)
                my_brand.save()
//                println my_brand

                connection = new URL(parser_api + "/brand/" + brand.url).openConnection() as HttpURLConnection

                connection.setRequestProperty('Accept', 'application/json')

                // get the response code - automatically sends the request
                resp = connection.inputStream.text

                def devices = jsonSlurper.parseText(resp)

                parse_devices(devices, my_brand)

            }

        }

        return 1
    }

    int parse_devices(def devices, Brand my_brand) {
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy, MMMM dd")

        devices.data.each { device ->
            println "----------------------------------------------"
            println device
            def my_device = Phone.findByUrl(device.url)
            if(my_device){
                println 'Device already parsed: ' + my_device
                return
            }
//            sleep(10000) // to avoid IP blocking
            def connection = new URL(parser_api + "/device/" + device.url).openConnection() as HttpURLConnection

            connection.setRequestProperty('Accept', 'application/json')

            // get the response code - automatically sends the request
            def resp = connection.inputStream.text

            def device_details = jsonSlurper.parseText(resp)

            device_details.spec_detail.each { spec ->
                if (spec.category.equalsIgnoreCase("Launch")) {
                    println spec.specs
                    try {
                        def result = (spec.specs[1].value =~ /(20[0-9]*, [aA-zZ]*)/)[0][1] + " 01"
                        def date = LocalDate.parse(result, date_formatter)
                        println date
                        if (date.isAfter(LocalDate.now().minusYears(3))) {
                            println 'saving...'
                            my_device = new Phone(brand: my_brand, model: device.name, description: device.description, image: device.img, url: device.url, release_date: date)
                            my_device.save()
                        } else {
                            println 'Too old. Skipping...'
                        }
                    } catch (Exception ex) {
                        println("Catching the exception: " + ex.toString());
                    }

                }


            }

            my_device = Phone.findByUrl(device.url)
            if(my_device){
                println 'adding specs: '+ my_device
                device_details.quick_spec.each { spec ->
                    if(spec.name.equalsIgnoreCase("Display size")){
                        def screen_size = (spec.value =~ /([0-9]*[.]?[0-9]*)/)[0][1]
                        Feature my_feature = new Feature(name: FeatureNames.screen_size, value_str: screen_size, value_num: (screen_size == "" ? "" : Float.parseFloat(screen_size)), phone: my_device, hasFeature: true).save()
                    } else if(spec.name.equalsIgnoreCase("Camera pixels")){
                        def camera_px = (spec.value =~ /([0-9]*[.]?[0-9]*)/)[0][1]
//                        println "Camera px: " + camera_px
                        Feature my_feature = new Feature(name: FeatureNames.camera_px, value_str: camera_px, value_num: (camera_px == "" ? "" : Float.parseFloat(camera_px)), phone: my_device, hasFeature: true).save()
                    } else if(spec.name.equalsIgnoreCase("RAM size")){
                        def ram = (spec.value =~ /([0-9]*[\/]?[0-9]*)/)[0][1]
                        ram.split("\\/").each{ val->
//                                println "RAM: " + val
                            Feature my_feature = new Feature(name: FeatureNames.ram, value_str: val, value_num: (val == "" ? "" : Float.parseFloat(val)), phone: my_device, hasFeature: true).save()
                        }
                    } else if(spec.name.equalsIgnoreCase("Shipset")){
                        Feature my_feature = new Feature(name: FeatureNames.chipset, value_str: spec.value, phone: my_device, hasFeature: true).save()
                    } else if(spec.name.equalsIgnoreCase("Battery size")){
                        def battery_size = (spec.value =~ /([0-9]*[.]?[0-9]*)/)[0][1]
                        Feature my_feature = new Feature(name: FeatureNames.battery_size, value_str: battery_size, value_num: (battery_size == "" ? "" : Float.parseFloat(battery_size)), phone: my_device, hasFeature: true).save()
                    }

                }

                device_details.spec_detail.each { spec ->
                    if (spec.category.equalsIgnoreCase("Network")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("Speed")) {
                                Feature my_feature = new Feature(name: FeatureNames.network, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save()
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Body")) {
                        spec.specs.each { item ->
//                            println "Item: " + item.name
//                            println "Size: " + item.name.size()
                            if (item.name.equalsIgnoreCase("SIM")) { //TODO: set the value_num to number of sim cards supported?
                                Feature my_feature = new Feature(name: FeatureNames.n_of_sims, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save()
                            } else if(item.name.size() == 1){
//                                println "Found space: " + item.name
                                if (item.value.contains("IP")){
                                    Feature my_feature = new Feature(name: FeatureNames.ip, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save()
                                }
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Display")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("Type")) {
                                Feature my_feature = new Feature(name: FeatureNames.screen_tech, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save()
                                my_feature = new Feature(name: FeatureNames.screen_hz, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save() // TODO: isolate HZ from string
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Platform")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("OS")) {
                                Feature my_feature = new Feature(name: FeatureNames.os, description: item.value, value_str: item.value, phone: my_device, hasFeature: true).save()
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Memory")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("Internal")) {
                                item.value.replaceAll("\\s","").split(",").each { val->
                                    def storage = (val =~ /([0-9]*)/)[0][1]
                                    if(storage != ""){
                                        int storage_int = Integer.parseInt(storage)
                                        if (storage_int < 10) storage_int = storage_int * 1024
                                        Feature my_feature = new Feature(name: FeatureNames.storage, value_str: storage_int, value_num: storage_int, phone: my_device, hasFeature: true).save()
                                    }
                                }
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Battery")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("Charging")) {
//                                println item.value
                                if (item.value.toLowerCase().contains('wireless')){ //TODO: Make clever script to capture wireless charge W instead of yes/no
//                                    println "wireless"
                                    Feature my_feature = new Feature(name: FeatureNames.wireless_charging, value_str: 1, phone: my_device, hasFeature: true).save()
                                }
                            }
                        }
                    } else if (spec.category.equalsIgnoreCase("Misc")) {
                        spec.specs.each { item ->
                            if (item.name.equalsIgnoreCase("Price")) { //TODO: Want to isolate the number? What about currency?
                                item.value.split("\\/").each { val ->
                                    Feature my_feature = new Feature(name: FeatureNames.price, value_str: val, phone: my_device, hasFeature: true).save()
                                }
                            }
                        }
                    }
                }
            }
//            println my_device

        }
        if (devices.next) {
            def connection = new URL(parser_api + "/brand/" + devices.next).openConnection() as HttpURLConnection

            connection.setRequestProperty('Accept', 'application/json')

            // get the response code - automatically sends the request
            def resp = connection.inputStream.text

            devices = jsonSlurper.parseText(resp)

            parse_devices(devices, my_brand)
        }
        return 1
    }
}
