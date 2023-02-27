package phoneofspyros_grails

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PhoneServiceSpec extends Specification {

    PhonesService phonesService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Phones(...).save(flush: true, failOnError: true)
        //new Phones(...).save(flush: true, failOnError: true)
        //Phones phones = new Phones(...).save(flush: true, failOnError: true)
        //new Phones(...).save(flush: true, failOnError: true)
        //new Phones(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //phones.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        phonesService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Phone> phonesList = phonesService.list(max: 2, offset: 2)

        then:
        phonesList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        phonesService.count() == 5
    }

    void "test delete"() {
        Long phonesId = setupData()

        expect:
        phonesService.count() == 5

        when:
        phonesService.delete(phonesId)
        datastore.currentSession.flush()

        then:
        phonesService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Phone phones = new Phone()
        phonesService.save(phones)

        then:
        phones.id != null
    }
}
