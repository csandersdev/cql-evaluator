package org.opencds.cqf.cql.evaluator.fhir.dal;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;

/**
 * This interface is a minimal Fhir CRUD API. It's based on the
 * <a href="https://www.hl7.org/fhir/http.html">FHIR HTTP API</a>, but constrained to provide only the
 * operations necessary for the cql-evaluator modules to function.
 * @see <a href="https://www.hl7.org/fhir/http.html">https://www.hl7.org/fhir/http.html</a>
 */
public interface FhirDal {
    /**
     * Fetches an {@code IBaseResource} by {@code id}. The {@code IIdType} must have the resourceType defined.
     * 
     * Returns null if no resource is found.
     * @param id the id of the resource
     * @return the resource
     */
    public IBaseResource read(IIdType id);

    /**
     * Creates the {@code IBaseResource}.
     * 
     * Default behavior is to overwrite the resource if it already exists.
     * @param resource the resource
     */
    public void create(IBaseResource resource);

    /**
     * Updates the {@code IBaseResource}.
     * 
     * Default behavior is to create the resource if it does not exist.
     * @param resource the resource
     */
    public void update(IBaseResource resource);

    /**
     * Deletes an {@code IBaseResource} by {@code id}. The {@code IIdType} must have the resourceType defined.
     * 
     * Default behavior is no-op if the resource does not exist.
     * @param id the id of the resource
     */
    public void delete(IIdType id);

    /**
     * Returns an {@code Iterable<IBaseResource>} of all the resources of type {@code resourceType}. 
     * 
     * Default behavior is to return null if no resources are found.
     * @param resourceType the type of resources to return.
     * @return the resources
     */
    public Iterable<IBaseResource> search(String resourceType);

    /**
     * Returns an {@code Iterable<IBaseResource>} of all the resources of type {@code resourceType} with a url property value of {@code url}.
     * 
     * Only knowledge artifact resources have the url property. A search on a non-knowledge artifact resource will throw an {@code IllegalArgumentException}
     * 
     * Default behavior is to return null if no resources are found.
     * @param resourceType the type of resources to return.
     * @param url the value of the url property
     * @return the resources
     * @throws IllegalArgumentException if {@code resourceType} is not a knowledge artifact
     */
    public Iterable<IBaseResource> searchByUrl(String resourceType, String url);
}

