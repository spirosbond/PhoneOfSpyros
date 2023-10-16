package phoneofspyros_grails


import grails.rest.*
import grails.converters.*

class BrandController {
	static responseFormats = ['json', 'xml']

    def index() { }

    def all() {
        print 'brand/all'
        render Brand.list() as JSON
    }
}
