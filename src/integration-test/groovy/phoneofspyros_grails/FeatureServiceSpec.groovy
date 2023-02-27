package phoneofspyros_grails

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class FeatureServiceSpec extends Specification {

    FeaturesService featuresService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Features(...).save(flush: true, failOnError: true)
        //new Features(...).save(flush: true, failOnError: true)
        //Features features = new Features(...).save(flush: true, failOnError: true)
        //new Features(...).save(flush: true, failOnError: true)
        //new Features(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //features.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        featuresService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Feature> featuresList = featuresService.list(max: 2, offset: 2)

        then:
        featuresList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        featuresService.count() == 5
    }

    void "test delete"() {
        Long featuresId = setupData()

        expect:
        featuresService.count() == 5

        when:
        featuresService.delete(featuresId)
        datastore.currentSession.flush()

        then:
        featuresService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Feature features = new Feature()
        featuresService.save(features)

        then:
        features.id != null
    }
}
