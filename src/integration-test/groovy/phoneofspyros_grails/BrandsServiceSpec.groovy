package phoneofspyros_grails

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class BrandsServiceSpec extends Specification {

    BrandsService brandsService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Brands(...).save(flush: true, failOnError: true)
        //new Brands(...).save(flush: true, failOnError: true)
        //Brands brands = new Brands(...).save(flush: true, failOnError: true)
        //new Brands(...).save(flush: true, failOnError: true)
        //new Brands(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //brands.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        brandsService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Brand> brandsList = brandsService.list(max: 2, offset: 2)

        then:
        brandsList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        brandsService.count() == 5
    }

    void "test delete"() {
        Long brandsId = setupData()

        expect:
        brandsService.count() == 5

        when:
        brandsService.delete(brandsId)
        datastore.currentSession.flush()

        then:
        brandsService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Brand brands = new Brand()
        brandsService.save(brands)

        then:
        brands.id != null
    }
}
