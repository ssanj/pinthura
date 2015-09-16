# A Report Generator Example #

This (somewhat contrived) example demonstrates how to use the `FactoryCreatorImpl` class to dynamically generate an instance of the `Report` interface defined on the `ReportFactory` interface.

The `ReportFactory` interface:

```
package com.googlecode.pinthura.factory.report;

public interface ReportFactory {

    Report create(Information info);
}
```

The `ReportImpl` class that is created by the `ReportFactory`:

```
package com.googlecode.pinthura.factory.report;

public final class ReportImpl implements Report {

    private final Information info;

    public ReportImpl(final Information info) {
        this.info = info;
    }

    public void generate() {
        String groupName = info.getGroupName();
        int memberCount = info.getMemberCount();
        System.out.println("-----------------------------");
        System.out.println("Group " + groupName + " has " + memberCount + " members");
        System.out.println("-----------------------------");
    }
}
```

The `ReportGeneratorImpl` class that uses the `ReportFactory` to create a `Report`:

```
package com.googlecode.pinthura.factory.report;

public final class ReportGeneratorImpl implements ReportGenerator {

    private final ReportFactory reportFactory;

    public ReportGeneratorImpl(final ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    public void generate(final Information info) {
        reportFactory.create(info).generate();
    }
}
```

The `ReportGeneratorRunner` class used to run the example:

```
package com.googlecode.pinthura.factory.report;

import com.googlecode.pinthura.factory.FactoryCreatorImpl;
import com.googlecode.pinthura.factory.InvocationFactoryImpl;
import com.googlecode.pinthura.factory.FactoryCreator;

public final class ReportGeneratorRunner {

    private static final int POPULATION = 5023;

    private ReportGeneratorRunner() { }

    public static void main(final String[] args) {
        FactoryCreator factoryCreator = new FactoryCreatorImpl(new InvocationFactoryImpl());
        ReportFactory reportFactory = factoryCreator.create(ReportFactory.class);
        ReportGenerator generator = new ReportGeneratorImpl(reportFactory);
        generator.generate(new InformationImpl("Tok'ra", POPULATION));
    }
}

```

The output from running the above:

```
-----------------------------
Group Tok'ra has 5023 members
-----------------------------
```