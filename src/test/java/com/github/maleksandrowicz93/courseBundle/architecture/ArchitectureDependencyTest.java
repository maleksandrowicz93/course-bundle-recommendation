package com.github.maleksandrowicz93.courseBundle.architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


public class ArchitectureDependencyTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("com.github.maleksandrowicz93.courseBundle");

    @Test
    void checkApplicationLayers() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                             .layer("commonLanguage").definedBy("com.github.maleksandrowicz93.courseBundle.commonLanguage")
                             .layer("quoteCalculator").definedBy("com.github.maleksandrowicz93.courseBundle.quoteCalculator")
                             .layer("resourceProvidersSupplier").definedBy("com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier")
                             .layer("recommendation").definedBy("com.github.maleksandrowicz93.courseBundle.recommendation")
                             .whereLayer("commonLanguage").mayNotAccessAnyLayer()
                             .whereLayer("quoteCalculator").mayOnlyAccessLayers("commonLanguage")
                             .whereLayer("resourceProvidersSupplier").mayOnlyAccessLayers("commonLanguage")
                             .whereLayer("recommendation").mayNotBeAccessedByAnyLayer()
                             .check(classes);
    }
}
