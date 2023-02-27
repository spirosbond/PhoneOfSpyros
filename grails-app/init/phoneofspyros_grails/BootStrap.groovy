package phoneofspyros_grails

class BootStrap {

    def parserService

    def init = { servletContext ->

        /*Brand samsung = new Brand(name:"_Samsung").save()
        Brand google = new Brand(name:"_Google", url:"http://www.google.com").save()

        Phone galaxy_s20 = new Phone(brand: samsung, model: "_Galaxy S20", description: "Galaxy flagship", url: "http://www.samsung.com").save()
        Phone pixel_7 = new Phone(brand: google, model: "_Pixel 7", description: "The phone!", image: "pixel.jpg", url: "http://www.google.com").save()

        Feature ft_pixel_7_screen_size = new Feature(name: FeatureNames.screen_size, value_str: '6.3', value_num: 6.3, phone: pixel_7, hasFeature: true).save()
        Feature ft_galaxy_s20_screen_size = new Feature(name: FeatureNames.screen_size, value_str: '6.7', value_num: 6.7, phone: galaxy_s20, hasFeature: true).save()
        Feature ft_pixel_7_price = new Feature(name: FeatureNames.price, value_str: '650', value_num: 650, phone: pixel_7, hasFeature: true).save()
        Feature ft_galaxy_s20_price = new Feature(name: FeatureNames.price, value_str: '700', value_num: 700, phone: galaxy_s20, hasFeature: true).save()
        Feature ft_pixel_7_ram = new Feature(name: FeatureNames.ram, value_str: '8', value_num: 8, phone: pixel_7, hasFeature: true).save()
        Feature ft_galaxy_s20_ram = new Feature(name: FeatureNames.ram, value_str: '10', value_num: 10, phone: galaxy_s20).save()
*/

        parserService.parse_phones()
        println 'finished bootstrap'

    }
    def destroy = {
    }
}
