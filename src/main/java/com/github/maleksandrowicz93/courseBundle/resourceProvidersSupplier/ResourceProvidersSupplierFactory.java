package com.github.maleksandrowicz93.courseBundle.resourceProvidersSupplier;

public class ResourceProvidersSupplierFactory {

    public ResourceProvidersSupplier staticFileBased() {
        return new StaticFileBasedResourceProvidersSupplier("config/resourceProviders.json");
    }
}
