# An XML Version Filter Example #

This example demonstrates how different versions of an XML message are transformed into the same business object. Currently versions 1 & 2 are supported. All other versions are not supported.

**Each `FilterLink` accepts an `XMLInformation` instance as Input and returns a `BusinessInformationObject` as Output.**

The `VersionOneFilter`:

```
package com.googlecode.pinthura.example.filter.version;

import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class VersionOneFilter implements FilterLink<XMLInformation, BusinessInformationObject>  {

    private final XMLTransformer xmlTransformer;

    public VersionOneFilter(final XMLTransformer xmlTransformer) {
        this.xmlTransformer = xmlTransformer;
    }

    public BusinessInformationObject filter(final XMLInformation xmlInformation) throws MatchNotFoundException {
        if (XMLVersionEnum.ONE.equals(xmlInformation.getVersion())) {
            return xmlTransformer.transform(xmlInformation);
        }

        throw new MatchNotFoundException();
    }

    public String getFilterName() {
        return "Version One Filter";
    }
}
```

The `VersionTwoFilter`:

```
package com.googlecode.pinthura.example.filter.version;

import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;

public final class VersionTwoFilter implements FilterLink<XMLInformation, BusinessInformationObject> {

    private final XMLTransformer xmlTransformer;

    public VersionTwoFilter(final XMLTransformer xmlTransformer) {
        this.xmlTransformer = xmlTransformer;
    }

    public BusinessInformationObject filter(final XMLInformation xmlInformation) throws MatchNotFoundException {
        if (XMLVersionEnum.TWO.equals(xmlInformation.getVersion())) {
            return xmlTransformer.transform(xmlInformation);
        }

        throw new MatchNotFoundException();
    }

    public String getFilterName() {
        return "Version Two Filter";
    }
}
```


The `XMLVersionFilterRunner`:

```
package com.googlecode.pinthura.example.filter.version;

import com.googlecode.pinthura.filter.FilterChainImpl;
import com.googlecode.pinthura.filter.FilterLink;
import com.googlecode.pinthura.filter.MatchNotFoundException;

import java.util.ArrayList;
import java.util.List;

public final class XMLVersionFilterRunner {

    private static class UnknownVersion implements XMLVersion {

        public String toString() {
            return "Unknown Version";
        }
    }

    private XMLVersionFilterRunner() { }

    public static void main(final String[] args) {
        List<FilterLink<XMLInformation, BusinessInformationObject>> filters =
                new ArrayList<FilterLink<XMLInformation, BusinessInformationObject>>();
        filters.add(new VersionOneFilter(new VersionOneXMLTransformer()));
        filters.add(new VersionTwoFilter(new VersionTwoXMLTransformer()));

        FilterLink<XMLInformation, BusinessInformationObject> chainOfResp =
                new FilterChainImpl<XMLInformation, BusinessInformationObject>(filters);

        BusinessInformationObject bo1 = chainOfResp.filter(new XMLInformationImpl(XMLVersionEnum.ONE));
        BusinessInformationObject bo2 = chainOfResp.filter(new XMLInformationImpl(XMLVersionEnum.TWO));

        System.out.println(bo1.getInformation());
        System.out.println(bo2.getInformation());

        try {
            chainOfResp.filter(new XMLInformationImpl(new UnknownVersion()));
        } catch (MatchNotFoundException e) {
            throw new UnknownXMLVersionException(e);
        }
    }
}
```

The output of `XMLVersionFilterRunner` is:

```
Version One information
Version Two information
Exception in thread "main" com.googlecode.pinthura.example.filter.version.UnknownXMLVersionException:
com.googlecode.pinthura.filter.MatchNotFoundException:
No match found for: [XMLInformationImpl - version: Unknown Version] with filters: Version One Filter, Version Two Filter
	at
com.googlecode.pinthura.example.filter.version.XMLVersionFilterRunner.main(XMLVersionFilterRunner.java:54)
	...
```