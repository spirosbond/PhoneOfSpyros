package phoneofspyros_grails


import grails.rest.*
import grails.converters.*
import groovy.json.JsonSlurper

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ParserController {
    static responseFormats = ['json', 'xml']
    def parserService


    def index() {

        parserService.parse_phones()

        redirect uri: "/"
    }


}
