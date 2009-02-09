package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.data.UrlBoundaryImpl;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceImpl;
import com.googlecode.pinthura.test.types.Tres;
import com.googlecode.pinthura.util.Deux;
import com.googlecode.pinthura.util.RandomDataChooser;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MethodReturnOfConcreteClass")
@SuppressionReason(SuppressionReason.Reason.INCUBATOR)
public final class ASuppliedFactorySorterIncubator {

    private final IMocksControl mockControl;
    private final ClassInstanceFactory mockClassInstanceFactory;
    private final RandomDataChooser randomDataChooser;
    private final MethodParam mockMethodParam;
    private final List<Tres<ClassInstance, ClassBoundary, Object>> classInstanceClassBoundaryInstanceList;
    private ClassInstance[] classInstances;
    private int currentIndex;


    public ASuppliedFactorySorterIncubator() {
        mockControl = EasyMock.createControl();
        mockClassInstanceFactory = mockControl.createMock(ClassInstanceFactory.class);
        mockMethodParam = mockControl.createMock(MethodParam.class);

        randomDataChooser = new RandomDataChooserBuilder().build();
        classInstanceClassBoundaryInstanceList = new ArrayList<Tres<ClassInstance, ClassBoundary, Object>>();

    }

    public ASuppliedFactorySorterIncubator supplyClassInstaceArrayOfSize(final int size) {
        classInstances = new ClassInstance[size];
        return this;
    }

    public ASuppliedFactorySorterIncubator supplyInstance() {
        return this;
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public ASuppliedFactorySorterIncubator atIndex(final int index) {
        Deux<Class, Object> deux = getRandomClassAndInstance();
        classInstances[index] = new ClassInstanceImpl(deux.one(), deux.two());
        return this;
    }

    private void addDynamicInstanceAt() {
        ClassInstance mockClassInstance = mockControl.createMock(ClassInstance.class);
        ClassBoundary mockClassBoundary = mockControl.createMock(ClassBoundary.class);
        Object randomInstance = getRandomInstance();
        classInstanceClassBoundaryInstanceList.add(new Tres<ClassInstance, ClassBoundary, Object>(mockClassInstance, mockClassBoundary,
                randomInstance));
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public ASuppliedFactorySorterIncubator performSort() {
        expectDynamicClasses();
        expectMethodParam();
        mockControl.replay();

        SuppliedFactorySorter sorter = new SuppliedFactorySorterImpl(mockClassInstanceFactory);
        sorter.sort(mockMethodParam, classInstances);

        return this;
    }

    @SuppressWarnings({"unchecked", "MethodParameterOfConcreteClass"})
    @SuppressionReason({SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS, SuppressionReason.Reason.TEST_TYPE})
    private void expectMethodParam() {
        EasyMock.expect(mockMethodParam.getParameterTypes());
        EasyMock.expectLastCall().andReturn(getSuppliedClassBoundaries());
        EasyMock.expect(mockMethodParam.getArguments());
        EasyMock.expectLastCall().andReturn(getSuppliedRandomInstance());

        for (Tres<ClassInstance, ClassBoundary, Object> tres : classInstanceClassBoundaryInstanceList) {
            EasyMock.expect(mockClassInstanceFactory.createClassInstance(tres.two(), tres.three())).andReturn(tres.one());
        }
    }

    private void expectDynamicClasses() {
        for (ClassInstance classInstance : classInstances) {
            if (classInstance == null) {
                addDynamicInstanceAt();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_CREATE_GENERIC_ARRAY)
    private Deux<Class,Object> getRandomClassAndInstance() {
       Deux[] instances = new Deux[] { new Deux(String.class, "testing"),
                                     new Deux(Integer.class, 1),
                                     new Deux(Double.class, 2.9d),
                                     new Deux(UrlBoundary.class, new UrlBoundaryImpl())};
        return randomDataChooser.chooseOneOf(instances);
    }

    private Object getRandomInstance() {
        return randomDataChooser.chooseOneOf("testing", 1, 2.9d, new UrlBoundaryImpl());
    }

    @SuppressWarnings("MethodParameterOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.TEST_TYPE)
    private List<Object> getSuppliedRandomInstance() {
        List<Object> instanceList = new ArrayList<Object>();

        for (Tres<ClassInstance, ClassBoundary, Object> tres : classInstanceClassBoundaryInstanceList) {
            instanceList.add(tres.three());
        }

        return instanceList;
    }

    //TODO: move this into a Collections type class for both Deux and Tres.
    @SuppressWarnings("MethodParameterOfConcreteClass")
    @SuppressionReason(SuppressionReason.Reason.TEST_TYPE)
    private List<ClassBoundary> getSuppliedClassBoundaries() {
        List<ClassBoundary> classBoundaries = new ArrayList<ClassBoundary>();

        for (Tres<ClassInstance, ClassBoundary, Object> tres : classInstanceClassBoundaryInstanceList) {
            classBoundaries.add(tres.two());
        }
        return classBoundaries;
    }

    public  ASuppliedFactorySorterIncubator index(final int index) {
        currentIndex = index;
        return this;
    }

    public ASuppliedFactorySorterIncubator isUnchanged() {
        assertThat(Proxy.isProxyClass(classInstances[currentIndex].getClass()), equalTo(false));
        return this;
    }

    public ASuppliedFactorySorterIncubator isCreated() {
        assertThat(Proxy.isProxyClass(classInstances[currentIndex].getClass()), equalTo(true));
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

    public ASuppliedFactorySorterIncubator dynamically() {
        return this;
    }
}