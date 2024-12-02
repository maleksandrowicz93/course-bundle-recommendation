package com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier

import com.github.maleksandrowicz93.courseBundle.commonLanguage.ResourceProvider
import spock.lang.Specification
import spock.lang.Subject

class StaticFileBasedResourceProvidersSupplierSpec extends Specification {

    @Subject
    def supplier = new ResourceProvidersSupplierFactory().staticFileBased()

    def "configuration file is correctly load from resources"() {
        given:
            def providerA = new ResourceProvider("provider_a", Set.of("math", "science"))
            def providerB = new ResourceProvider("provider_b", Set.of("reading", "science"))
            def providerC = new ResourceProvider("provider_c", Set.of("history", "math"))

        when:
            def resourceProviders = supplier.get()

        then:
            with(resourceProviders.all()) {
                size() == 3
                contains(providerA)
                contains(providerB)
                contains(providerC)
            }
    }
}
