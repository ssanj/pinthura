package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

public final class ASuppliedFactorySorterIncubator {

    private final IMocksControl mockControl;
    private final Object randomInstance;
    private final ClassInstanceFactory mockClassInstanceFactory;
    private final MethodParam mockMethodParam;
    private final ClassInstance[] classInstances;
    private SuppliedFactorySorter sorter;
    private ClassInstance mockInstance1;
    private ClassInstance mockInstance2;
    private ClassInstance mockClassInstance;


    public ASuppliedFactorySorterIncubator() {
        mockControl = EasyMock.createControl();
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);
        mockClassInstance = mockControl.createMock(ClassInstance.class);

        classInstances = new ClassInstance[3];
        randomInstance = new RandomDataChooserBuilder().build().chooseOneOf("testing", 1, 2.9, new UrlBoundaryImpl());
    }

    public ASuppliedFactorySorterIncubator supplyClassInstances() {
        return this;
    }

    public ASuppliedFactorySorterIncubator supplyVacantMiddleIndex() {
        mockInstance1 = mockControl.createMock(ClassInstance.class);
        mockInstance2 = mockControl.createMock(ClassInstance.class);
        classInstances[0] = mockInstance1;
        classInstances[2] = mockInstance2;
        return this;
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public ASuppliedFactorySorterIncubator performSort() {
        sorter = new SuppliedFactorySorterImpl(mockClassInstanceFactory);
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        EasyMock.expect(mockMethodParam.getParameterTypes());
        EasyMock.expectLastCall().andReturn(Arrays.asList(mockClassBoundary));
        EasyMock.expect(mockMethodParam.getArguments());
        EasyMock.expectLastCall().andReturn(Arrays.asList(randomInstance));
        EasyMock.expect(mockClassInstanceFactory.createClassInstance(mockClassBoundary, randomInstance)).andReturn(mockClassInstance);
        mockControl.replay();

        sorter.sort(mockMethodParam, classInstances);

        return this;
    }

    public ASuppliedFactorySorterIncubator middleIndex() {
        assertThat(classInstances[0], sameInstance(mockInstance1));
        assertThat(classInstances[1], sameInstance(mockClassInstance));
        assertThat(classInstances[2], sameInstance(mockInstance2));       
        return this;
    }

    public ASuppliedFactorySorterIncubator isFilled() {
        return this;
    }

    public ASuppliedFactorySorterIncubator done() {
        mockControl.verify();
        return this;
    }

    public ASuppliedFactorySorterIncubator observe() {
        mockControl.verify();
        return this;
    }
}
