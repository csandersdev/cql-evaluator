package org.opencds.cqf.cql.evaluator.expression;

import static java.util.Objects.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.elm.execution.VersionedIdentifier;
import org.fhir.ucum.Canonical;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseBundle;
import org.hl7.fhir.instance.model.api.IBaseDatatype;
import org.hl7.fhir.instance.model.api.IBaseParameters;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Base64BinaryType;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DataRequirement;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Library;
import org.hl7.fhir.r4.model.Parameters;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Parameters.ParametersParameterComponent;
import org.opencds.cqf.cql.evaluator.builder.CqlEvaluatorBuilder;
import org.opencds.cqf.cql.evaluator.builder.DataProviderFactory;
import org.opencds.cqf.cql.evaluator.builder.EndpointConverter;
import org.opencds.cqf.cql.evaluator.builder.LibraryLoaderFactory;
import org.opencds.cqf.cql.evaluator.builder.TerminologyProviderFactory;
import org.opencds.cqf.cql.evaluator.library.CqlFhirParametersConverter;
import org.opencds.cqf.cql.evaluator.library.LibraryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.fhirpath.IFhirPath;

@SuppressWarnings("unused")
@Named
public class ExpressionEvaluator {

    private static Logger logger = LoggerFactory.getLogger(ExpressionEvaluator.class);

    protected FhirContext fhirContext;
    protected CqlFhirParametersConverter cqlFhirParametersConverter;
    protected LibraryLoaderFactory libraryLoaderFactory;
    protected DataProviderFactory dataProviderFactory;
    protected TerminologyProviderFactory terminologyProviderFactory;
    protected EndpointConverter endpointConverter;
    protected CqlEvaluatorBuilder cqlEvaluatorBuilder;
    protected IFhirPath fhirPath;
    protected LibraryProcessor libraryProcessor;
    protected OperationParametersParser operationParametersParser;

    @Inject
    public ExpressionEvaluator(FhirContext fhirContext, CqlFhirParametersConverter cqlFhirParametersConverter,
            LibraryLoaderFactory libraryLoaderFactory, DataProviderFactory dataProviderFactory,
            TerminologyProviderFactory terminologyProviderFactory, EndpointConverter endpointConverter,
            CqlEvaluatorBuilder cqlEvaluatorBuilder, OperationParametersParser operationParametersParser) {

        this.fhirContext = requireNonNull(fhirContext, "fhirContext can not be null");
        this.fhirPath = fhirContext.newFhirPath();
        this.cqlFhirParametersConverter = requireNonNull(cqlFhirParametersConverter, "cqlFhirParametersConverter");
        this.libraryLoaderFactory = requireNonNull(libraryLoaderFactory, "libraryLoaderFactory can not be null");
        this.dataProviderFactory = requireNonNull(dataProviderFactory, "dataProviderFactory can not be null");
        this.terminologyProviderFactory = requireNonNull(terminologyProviderFactory,
                "terminologyProviderFactory can not be null");

        this.endpointConverter = requireNonNull(endpointConverter, "endpointConverter can not be null");
        this.cqlEvaluatorBuilder = requireNonNull(cqlEvaluatorBuilder, "cqlEvaluatorBuilder can not be null");
        this.operationParametersParser = requireNonNull(operationParametersParser, "operationParametersParser can not be null");
        
        libraryProcessor = new LibraryProcessor(fhirContext, cqlFhirParametersConverter, libraryLoaderFactory, dataProviderFactory, terminologyProviderFactory, endpointConverter, cqlEvaluatorBuilder);
    }

    /**
     * Evaluates a CQL expression and returns the results as a Parameters resource. (Not yet implemented.)
     * @param parameters the parameters to evaluate Cql expression.
     * @return IBaseParameters The result of evaluating the given expression, returned as a FHIR type, either a resource, or a FHIR-defined type corresponding to the CQL return type, as defined in the Using CQL section of this implementation guide. If the result is a List of resources, the result will be a Bundle. If the result is a CQL system-defined or FHIR-defined type, the result is returned as a Parameters resource
     */
    public IBaseParameters evaluate(IBaseParameters parameters) {
        // Need to finish OperationParametersParser getParts and make sure to get all Libraries
        // and prefetchData parameters that return.
        throw new UnsupportedOperationException("Not yet implemented.");
        // requireNonNull(parameters, "parameters can not be null");
        // String subject = null;
        // String expression = null;
        // IBaseParameters evaluationParameters = null;
        // List<Pair<Canonical, String>> libraries = null;
        // Boolean useServerData = null;
        // IBaseBundle bundle = null;
        // List<Triple<String, DataRequirement, IBaseBundle>> prefetchData = null;
        // IBaseResource dataEndpoint = null;
        // IBaseResource contentEndpoint = null;
        // IBaseResource terminologyEndpoint = null;

        // Map<String, IBaseResource> resources = operationParametersParser.getResourceChildren(parameters);
        // Map<String, IBaseDatatype> values = operationParametersParser.getValueChildren(parameters);
        // Map<String, Object> parts = operationParametersParser.getParameterParts(parameters);

        // IBaseDatatype subjectBaseType = values.get("subject");
        // if (subjectBaseType == null) {
        //     logger.info("subject parameter was null");
        // } else {
        //     if (!(subjectBaseType instanceof StringType)) {
        //         throw new IllegalArgumentException("subject parameter must be a StringType");
        //     }
        //     subject = ((StringType) subjectBaseType).asStringValue();
        // }

        // IBaseDatatype expressionBaseType = values.get("expression");
        // if (expressionBaseType == null) {
        //     throw new IllegalArgumentException("expression parameter must not be null");
        // } else {
        //     if (!(expressionBaseType instanceof StringType)) {
        //         throw new IllegalArgumentException("subject parameter must be a StringType");
        //     }
        //     expression = ((StringType) expressionBaseType).asStringValue();
        // }

        // IBaseResource evaluationParametersBaseResource = resources.get("parameters");
        // if (evaluationParametersBaseResource == null) {
        //     logger.info("parameters parameter was null");
        // } else {
        //     if (!(evaluationParametersBaseResource instanceof IBaseParameters)) {
        //         throw new IllegalArgumentException("subject parameter must be a IBaseParameters");
        //     }
        //     evaluationParameters = (IBaseParameters) expressionBaseType;
        // }

        // Canonical libraryUrl = null;
        // // There should really be multiple libraries available
        // Object canonicalUrlObject = parts.get("url");
        // if (canonicalUrlObject == null) {
        //     logger.info("library.url parameter was null");
        // } else {
        //     if (!(canonicalUrlObject instanceof Canonical)) {
        //         throw new IllegalArgumentException("library.url parameter must be a Canonical");
        //     }
        //     libraryUrl = (Canonical) canonicalUrlObject;
        // }

        // String libraryName = null;
        // // There should really be multiple libraries available
        // Object libraryNameObject = parts.get("name");
        // if (libraryNameObject == null) {
        //     logger.info("library.name parameter was null");
        // } else {
        //     if (!(libraryNameObject instanceof StringType)) {
        //         throw new IllegalArgumentException("library.name parameter must be a StringType");
        //     }
        //     libraryName = ((StringType) libraryNameObject).asStringValue();
        // }

        // // There should really be multiple libraries available
        // if (libraryName != null || libraryUrl != null) {
        //     libraries = Arrays.asList((Pair.of(libraryUrl, libraryName)));
        // }
        
        // IBaseDatatype useServerDataBaseType = values.get("useServerData");
        // if (useServerDataBaseType == null) {
        //     logger.info("useServerData parameter was null");
        // } else {
        //     if (!(useServerDataBaseType instanceof BooleanType)) {
        //         throw new IllegalArgumentException("useServerData parameter must be a BooleanType");
        //     }
        //     useServerData = ((BooleanType) useServerDataBaseType).getValue();
        // }

        // IBaseResource dataBaseResource = resources.get("data");
        // if (dataBaseResource == null) {
        //     logger.info("bundle parameter was null");
        // } else {
        //     if (!(dataBaseResource instanceof IBaseBundle)) {
        //         throw new IllegalArgumentException("bundle parameter must be a IBaseBundle");
        //     }
        //     bundle = (IBaseBundle) dataBaseResource;
        // }

        // String prefetchDataKey = null;
        // // There should really be multiple prefetchData available
        // Object prefetchDataKeyObject = parts.get("key");
        // if (prefetchDataKeyObject == null) {
        //     logger.info("prefetchData.key parameter was null");
        // } else {
        //     if (!(prefetchDataKeyObject instanceof StringType)) {
        //         throw new IllegalArgumentException("prefetchData.Key parameter must be a StringType");
        //     }
        //     prefetchDataKey = ((StringType) prefetchDataKeyObject).asStringValue();
        // }

        // DataRequirement prefetchDataDescription = null;
        // // There should really be multiple prefetchData available
        // Object prefetchDataDescriptionObject = parts.get("description");
        // if (prefetchDataDescriptionObject == null) {
        //     logger.info("prefetchData.description parameter was null");
        // } else {
        //     if (!(prefetchDataDescriptionObject instanceof DataRequirement)) {
        //         throw new IllegalArgumentException("prefetchData.description parameter must be a DataRequirement");
        //     }
        //     prefetchDataDescription = (DataRequirement) prefetchDataDescriptionObject;
        // }

        // IBaseBundle prefetchDataData = null;
        // // There should really be multiple prefetchData available
        // Object prefetchDataDataObject = parts.get("data");
        // if (prefetchDataDataObject == null) {
        //     logger.info("prefetchData.data parameter was null");
        // } else {
        //     if (!(prefetchDataDataObject instanceof IBaseBundle)) {
        //         throw new IllegalArgumentException("prefetchData.data parameter must be a IBaseBundle");
        //     }
        //     prefetchDataData = (IBaseBundle) prefetchDataDataObject;
        // }

        // // There should really be multiple prefetchData available
        // if (prefetchDataKey != null || prefetchDataDescription != null || prefetchDataData != null) {
        //     prefetchData = Arrays.asList(Triple.of(prefetchDataKey, prefetchDataDescription, prefetchDataData));
        // }

        // IBaseResource dataEndpointBaseResource = resources.get("dataEndpoint");
        // if (dataEndpointBaseResource == null) {
        //     logger.info("dataEndpoint parameter was null");
        // } else {
        //     if (!(dataEndpointBaseResource instanceof IBaseResource)) {
        //         throw new IllegalArgumentException("dataEndpoint parameter must be a IBaseResource");
        //     }
        //     dataEndpoint = (IBaseResource) dataEndpointBaseResource;
        // }

        // IBaseResource contentEndpointBaseResource = resources.get("contentEndpoint");
        // if (contentEndpointBaseResource == null) {
        //     logger.info("contentEndpoint parameter was null");
        // } else {
        //     if (!(contentEndpointBaseResource instanceof IBaseResource)) {
        //         throw new IllegalArgumentException("contentEndpoint parameter must be a IBaseResource");
        //     }
        //     contentEndpoint = (IBaseResource) contentEndpointBaseResource;
        // }

        // IBaseResource terminologyEndpointBaseResource = resources.get("terminologyEndpoint");
        // if (terminologyEndpointBaseResource == null) {
        //     logger.info("terminologyEndpoint parameter was null");
        // } else {
        //     if (!(terminologyEndpointBaseResource instanceof IBaseResource)) {
        //         throw new IllegalArgumentException("terminologyEndpoint parameter must be a IBaseResource");
        //     }
        //     terminologyEndpoint = (IBaseResource) terminologyEndpointBaseResource;
        // }
        
        // return evaluate(subject, expression, evaluationParameters, libraries, useServerData, bundle, prefetchData, dataEndpoint, contentEndpoint, terminologyEndpoint);
    }

    /**
     * Evaluates a CQL expression and returns the results as a Parameters resource.
     * @param subject Subject for which the expression will be evaluated. This corresponds to the context in which the expression will be evaluated and is represented as a relative FHIR id (e.g. Patient/123), which establishes both the context and context value for the evaluation
     * @param expression Expression to be evaluated. Note that this is an expression of CQL, not the text of a library with definition statements.
     * @param parameters Any input parameters for the expression. Parameters defined in this input will be made available by name to the CQL expression. Parameter types are mapped to CQL as specified in the Using CQL section of this implementation guide. If a parameter appears more than once in the input Parameters resource, it is represented with a List in the input CQL. If a parameter has parts, it is represented as a Tuple in the input CQL.
     * @param library A library to be included. The library is resolved by url and made available by name within the expression to be evaluated.
     * @param useServerData Whether to use data from the server performing the evaluation. If this parameter is true (the default), then the operation will use data first from any bundles provided as parameters (through the data and prefetch parameters), second data from the server performing the operation, and third, data from the dataEndpoint parameter (if provided). If this parameter is false, the operation will use data first from the bundles provided in the data or prefetch parameters, and second from the dataEndpoint parameter (if provided).
     * @param bundle Data to be made available to the library evaluation. This parameter is exclusive with the prefetchData parameter (i.e. either provide all data as a single bundle, or provide data using multiple bundles with prefetch descriptions).
     * @param prefetchData Data to be made available to the library evaluation, organized as prefetch response bundles. Each prefetchData parameter specifies either the name of the prefetchKey it is satisfying, a DataRequirement describing the prefetch, or both.
     * @param dataEndpoint An endpoint to use to access data referenced by retrieve operations in the library. If provided, this endpoint is used after the data or prefetchData bundles, and the server, if the useServerData parameter is true.
     * @param contentEndpoint An endpoint to use to access content (i.e. libraries) referenced by the library. If no content endpoint is supplied, the evaluation will attempt to retrieve content from the server on which the operation is being performed.
     * @param terminologyEndpoint An endpoint to use to access terminology (i.e. valuesets, codesystems, and membership testing) referenced by the library. If no terminology endpoint is supplied, the evaluation will attempt to use the server on which the operation is being performed as the terminology server.
     * @return IBaseParameters The result of evaluating the given expression, returned as a FHIR type, either a resource, or a FHIR-defined type corresponding to the CQL return type, as defined in the Using CQL section of this implementation guide. If the result is a List of resources, the result will be a Bundle. If the result is a CQL system-defined or FHIR-defined type, the result is returned as a Parameters resource
     */
    // Canonical is not a canonical data type.
    public IBaseParameters evaluate(String subject, String expression, IBaseParameters parameters,
            List<Pair<String, String>> libraries, Boolean useServerData, IBaseBundle bundle,
            List<Triple<String, DataRequirement, IBaseBundle>> prefetchData, IBaseResource dataEndpoint,
            IBaseResource contentEndpoint, IBaseResource terminologyEndpoint) {
                
        Library localLibrary = new Library().setName("LocalLibrary").setVersion("1.0.0").setUrl("http://localhost/Library/LocalLibrary|1.0.0");
        localLibrary.setId(new IdType("localLibrary-1"));
        String cql = constructCqlLibrary(expression, libraries);
        ensureCql(localLibrary, cql);
        if (libraries == null || libraries.isEmpty()) {
            CqlTranslator translator = getTranslator(cql);
            ensureElm(localLibrary, translator);
        }
        BundleEntryComponent entryComponent = new BundleEntryComponent();
        entryComponent.setResource(localLibrary);
        if (bundle != null) {
            logger.info("Adding LocalLibrary to additionalData Bundle");
            List<IBase> entries = fhirPath.evaluate(bundle, "entry", IBase.class);
            entries.add(entryComponent);
        } else {
            logger.info("Adding LocalLibrary to Local additionalData Bundle");
            Bundle localBundle = new Bundle();
            localBundle.setId(new IdType("localBundle-1"));
            localBundle.setType(BundleType.COLLECTION);
            localBundle.setEntry(Arrays.asList(entryComponent));
            bundle = localBundle;
        }
        Set<String> expressions = new HashSet<String>();
        expressions.add("LocalExpression");

        return libraryProcessor.evaluate(localLibrary.getUrl(), subject, parameters, contentEndpoint, terminologyEndpoint, dataEndpoint, bundle, expressions);
    }

    private String constructCqlLibrary(String expression, List<Pair<String, String>> libraries) {
        String cql = null;
        logger.info("Constructing LocalLibrary for local evaluation");
        if (libraries == null || libraries.isEmpty()) {
            cql = String.format("library LocalLibrary version \'1.0.0\'\n\ndefine \"LocalExpression\":\n       %s", expression);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("library LocalLibrary version \'1.0.0\'\n\n");
            for (Pair<String, String> library : libraries) {
                VersionedIdentifier vi = getVersionedIdentifer(library.getLeft());
                sb.append(String.format("include \"%s\"", vi.getId()));
                if (vi.getVersion() != null) {
                    sb.append(String.format(" version \'%s\'", vi.getVersion()));
                }
                if (library.getRight() != null) {
                    sb.append(String.format(" called \"%s\"", library.getRight()));
                }
                sb.append("\n");
            }
            sb.append(String.format("\ndefine \"LocalExpression\":\n       %s", expression));
            cql = sb.toString();
        }
        System.out.println(cql);
        return cql;
    }

    protected VersionedIdentifier getVersionedIdentifer(String url) {
        if (!url.contains("/Library/")) {
            throw new IllegalArgumentException("Invalid resource type for determining library version identifier: Library");
        }
        String [] urlsplit = url.split("/Library/");
        if (urlsplit.length != 2) {
            throw new IllegalArgumentException("Invalid url, Library.url SHALL be <CQL namepsace url>/Library/<CQL library name>");
        }
        String cqlNamespaceUrl = urlsplit[0];

        String cqlName = urlsplit[1];
        VersionedIdentifier versionedIdentifier = new VersionedIdentifier();
        if (cqlName.contains("|")) {
            String[] nameVersion = cqlName.split("\\|");
            String name = nameVersion[0];
            String version = nameVersion[1];
            versionedIdentifier.setId(name);
            versionedIdentifier.setVersion(version);
        } else {
            versionedIdentifier.setId(cqlName);
        }
        return versionedIdentifier;
    }

    public void ensureCql(Library library, String cql) {
        library.getContent().removeIf(a -> a.getContentType().equals("text/cql"));
        Attachment elm = new Attachment();
        elm.setContentType("text/cql");
        elm.setData(cql.getBytes());
        library.getContent().add(elm);
    }

    public void ensureElm(Library library, CqlTranslator translator) {
        library.getContent().removeIf(a -> a.getContentType().equals("application/elm+xml"));
        String xml = translator.toXml();
        Attachment elm = new Attachment();
        elm.setContentType("application/elm+xml");
        elm.setData(xml.getBytes());
        library.getContent().add(elm);
    }

    private CqlTranslator getTranslator(String cql) {
        ModelManager modelManager = new ModelManager();
        LibraryManager libraryManager = new LibraryManager(modelManager);
        System.out.println(cql);
        CqlTranslator translator = getTranslator(cql, libraryManager, modelManager);
        if (translator.getErrors().size() > 0) {
            throw new RuntimeException("Errors during library compilation.");
        }
        return translator;
    }

    public static CqlTranslator getTranslator(String cql, LibraryManager libraryManager, ModelManager modelManager) {
        return getTranslator(new ByteArrayInputStream(cql.getBytes(StandardCharsets.UTF_8)), libraryManager,
                modelManager);
    }

    public static CqlTranslator getTranslator(InputStream cqlStream, LibraryManager libraryManager,
            ModelManager modelManager) {
        ArrayList<CqlTranslator.Options> options = new ArrayList<>();
        options.add(CqlTranslator.Options.EnableAnnotations);
        options.add(CqlTranslator.Options.EnableLocators);
        options.add(CqlTranslator.Options.DisableListDemotion);
        options.add(CqlTranslator.Options.DisableListPromotion);
        options.add(CqlTranslator.Options.DisableMethodInvocation);
        CqlTranslator translator;
        try {
            translator = CqlTranslator.fromStream(cqlStream, modelManager, libraryManager,
                    options.toArray(new CqlTranslator.Options[options.size()]));
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("Errors occurred translating library: %s", e.getMessage()));
        }

        return translator;
    }
}
