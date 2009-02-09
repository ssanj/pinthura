package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.reflect.FieldBoundary;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.test.ExceptionAsserter;
import com.googlecode.pinthura.test.ExceptionAsserterImpl;
import com.googlecode.pinthura.test.types.Deux;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"MethodReturnOfConcreteClass", "InstanceVariableOfConcreteClass", "MethodParameterOfConcreteClass"})
@SuppressionReason({SuppressionReason.Reason.INCUBATOR, SuppressionReason.Reason.TEST_TYPE})
public final class AnEasyMockInjectorIncubator {

    private static final String EXCEPTION_MESSAGE = "Test exception";

    private final IMocksControl mockControl;
    private final MockInjector mockInjector;
    private final FieldFinder mockFieldFinder;
    private final FieldSetter mockFieldSetter;
    private final EasyMockWrapper mockEasyMockWrapper;
    private Object instance;
    private Deux<FieldBoundary,IMocksControl> fieldMockControlDeux;
    private final List<Deux<FieldBoundary,ClassBoundary>> fieldsDeux;
    private final Map<String, Deux<FieldBoundary,ClassBoundary>> fieldsDeuxByName;
    private String prefix;
    private MockInjectionException exception;
    private boolean exceptionSet;
    private final MockConfigurer mockMockConfigurer;
    private ExceptionAsserter asserter;

    public AnEasyMockInjectorIncubator() {
        mockControl = EasyMock.createControl();
        mockFieldFinder = mockControl.createMock(FieldFinder.class);
        mockFieldSetter = mockControl.createMock(FieldSetter.class);
        mockEasyMockWrapper = mockControl.createMock(EasyMockWrapper.class);
        mockMockConfigurer = mockControl.createMock(MockConfigurer.class);

        fieldsDeux = new ArrayList<Deux<FieldBoundary, ClassBoundary>>();
        fieldsDeuxByName = new HashMap<String, Deux<FieldBoundary, ClassBoundary>>();
        asserter = new ExceptionAsserterImpl();
        mockInjector = new EasyMockInjectorBuilder().
                            withFieldFinder(mockFieldFinder).
                            withFieldSetter(mockFieldSetter).
                            withEasyMockWrapper(mockEasyMockWrapper).
                            withMockConfigurer(mockMockConfigurer).
                            build();
    }

    public AnEasyMockInjectorIncubator supplyPrefix(final String prefix) {
        this.prefix = prefix;
        EasyMock.expect(mockMockConfigurer.getMockPrefix()).andReturn(prefix);
        return this;
    }

    public AnEasyMockInjectorIncubator supplyField(final String fieldName) {
        Deux<FieldBoundary, ClassBoundary> fieldDeux = createFieldClassBoundaryDeux();
        fieldsDeux.add(fieldDeux);
        fieldsDeuxByName.put(fieldName, fieldDeux);
        EasyMock.expect(fieldDeux.one().getName()).andReturn(fieldName);
        return this;
    }

    public <T> AnEasyMockInjectorIncubator supplyMockField(final String fieldName, final T value) {
        expectInjectedField(fieldsDeuxByName.get(fieldName), value);
        return this;
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public AnEasyMockInjectorIncubator supplyMockControl(final String mockControlName) {
        fieldMockControlDeux = createFieldMockControlDeux();
        EasyMock.expect(mockMockConfigurer.getMockControlName()).andReturn(mockControlName).times(2);
        EasyMock.expect(mockFieldFinder.findByName(mockControlName, instance)).andReturn(fieldMockControlDeux.one());
        EasyMock.expect(mockEasyMockWrapper.createControl()).andReturn(fieldMockControlDeux.two());
        mockFieldSetter.setValue(fieldMockControlDeux.one(), instance, fieldMockControlDeux.two());
        return this;
    }

    public AnEasyMockInjectorIncubator supplyInstance(final Object instance) {
        this.instance = instance;
        return this;
    }

    public AnEasyMockInjectorIncubator performInject() {
        replay();
        perform();
        return this;
    }

    private void perform() {
        try {
            mockInjector.inject(instance);
        } catch(MockInjectionException e) {
            exception = e;
        }
    }

    private void replay() {
        if (!exceptionSet) {
            expectPrefixedFields();
        }

        mockControl.replay();
    }

    private void expectPrefixedFields() {
        List<FieldBoundary<?>> fieldsPrefixedWithMock = new ArrayList<FieldBoundary<?>>();
        for (Deux<FieldBoundary, ClassBoundary> fieldBoundaryClassBoundaryDeux : fieldsDeux) {
            fieldsPrefixedWithMock.add(fieldBoundaryClassBoundaryDeux.one());
        }

        EasyMock.expect(mockFieldFinder.findByPrefix(prefix, instance)).andReturn(fieldsPrefixedWithMock);
    }

    private Deux<FieldBoundary, ClassBoundary> createFieldClassBoundaryDeux() {
        return new Deux<FieldBoundary, ClassBoundary>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(ClassBoundary.class));
    }

    private Deux<FieldBoundary, IMocksControl> createFieldMockControlDeux() {
        return new Deux<FieldBoundary, IMocksControl>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(IMocksControl.class));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    private <T> void expectInjectedField(final Deux<FieldBoundary, ClassBoundary> fieldAndClassBoundary, final T value) {
        EasyMock.expect(fieldMockControlDeux.one().get(instance)).andReturn(fieldMockControlDeux.two());
        EasyMock.expect(fieldAndClassBoundary.one().getType()).andReturn(fieldAndClassBoundary.two());
        EasyMock.expect(mockEasyMockWrapper.createMock(fieldMockControlDeux.two(), fieldAndClassBoundary.two())).andReturn(value);
        mockFieldSetter.setValue(fieldAndClassBoundary.one(),  instance, value);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    public AnEasyMockInjectorIncubator supplyExceptionalCondition() {
        exceptionSet = true;
        EasyMock.expect(mockMockConfigurer.getMockControlName()).andThrow(new RuntimeException(EXCEPTION_MESSAGE));
        return this;
    }

    public AnEasyMockInjectorIncubator observe() {
        return this;
    }

    public AnEasyMockInjectorIncubator noErrors() {
        mockControl.verify();
        return this;
    }

    public AnEasyMockInjectorIncubator areReturned() {
        return this;
    }

    public void done() {
        //do nothing.
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public AnEasyMockInjectorIncubator exception() {
        asserter.assertValidException(exception.getCause(), RuntimeException.class);
        asserter.assertExceptionMessage(exception.getCause(), EXCEPTION_MESSAGE);
        return this;
    }

    public AnEasyMockInjectorIncubator isThrown() {
        return this;
    }
}
