package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.util.Deux;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AMockInjectorIncubator {

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

    public AMockInjectorIncubator() {
        mockControl = EasyMock.createControl();
        mockFieldFinder = mockControl.createMock(FieldFinder.class);
        mockFieldSetter = mockControl.createMock(FieldSetter.class);
        mockEasyMockWrapper = mockControl.createMock(EasyMockWrapper.class);


        fieldsDeux = new ArrayList<Deux<FieldBoundary, ClassBoundary>>();
        fieldsDeuxByName = new HashMap<String, Deux<FieldBoundary, ClassBoundary>>();
        mockInjector = new MockInjectorBuilder().
                            withFieldFinder(mockFieldFinder).
                            withFieldSetter(mockFieldSetter).
                            withEasyMockWrapper(mockEasyMockWrapper).
                            build();
    }

    public AMockInjectorIncubator supplyPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }

    public AMockInjectorIncubator supplyField(final String fieldName) {
        Deux<FieldBoundary, ClassBoundary> fieldDeux = createFieldClassBoundaryDeux();
        fieldsDeux.add(fieldDeux);
        fieldsDeuxByName.put(fieldName, fieldDeux);
        EasyMock.expect(fieldDeux.getOne().getName()).andReturn(fieldName);
        return this;
    }

    public <T> AMockInjectorIncubator supplyMockField(final String fieldName, final T value) {
        expectInjectedField(fieldsDeuxByName.get(fieldName), value);
        return this;
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public AMockInjectorIncubator supplyMockControl(final String mockControlName) {
        fieldMockControlDeux = createFieldMockControlDeux();
        EasyMock.expect(mockFieldFinder.findByName(mockControlName, instance)).andReturn(fieldMockControlDeux.getOne());
        EasyMock.expect(mockEasyMockWrapper.createControl()).andReturn(fieldMockControlDeux.getTwo());
        mockFieldSetter.setValue(fieldMockControlDeux.getOne(), instance, fieldMockControlDeux.getTwo());
        return this;
    }

    public AMockInjectorIncubator supplyInstance(final Object instance) {
        this.instance = instance;
        return this;
    }

    public AMockInjectorIncubator performInject() {
        if (!exceptionSet) {
            expectPrefixedFields();
        }

        mockControl.replay();

        try {
            mockInjector.inject(instance);
        } catch(MockInjectionException e) {
            exception = e;
        }

        return this;
    }

    private void expectPrefixedFields() {
        List<FieldBoundary<?>> fieldsPrefixedWithMock = new ArrayList<FieldBoundary<?>>();
        for (Deux<FieldBoundary, ClassBoundary> fieldBoundaryClassBoundaryDeux : fieldsDeux) {
            fieldsPrefixedWithMock.add(fieldBoundaryClassBoundaryDeux.getOne());
        }

        EasyMock.expect(mockFieldFinder.findByPrefix(prefix, instance)).andReturn(fieldsPrefixedWithMock);
    }

    public AMockInjectorIncubator observe() {
        return this;
    }

    public AMockInjectorIncubator noErrors() {
        mockControl.verify();
        return this;
    }

    public AMockInjectorIncubator areReturned() {
        return this;
    }

    public void done() {
        //do nothing.        
    }

    private Deux<FieldBoundary, ClassBoundary> createFieldClassBoundaryDeux() {
        return new Deux.DeuxImpl<FieldBoundary, ClassBoundary>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(ClassBoundary.class));
    }

    private Deux<FieldBoundary, IMocksControl> createFieldMockControlDeux() {
        return new Deux.DeuxImpl<FieldBoundary, IMocksControl>(mockControl.createMock(FieldBoundary.class),
                mockControl.createMock(IMocksControl.class));
    }

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)    
    private <T> void expectInjectedField(final Deux<FieldBoundary, ClassBoundary> fieldAndClassBoundary, final T value) {
        EasyMock.expect(fieldMockControlDeux.getOne().get(instance)).andReturn(fieldMockControlDeux.getTwo());
        EasyMock.expect(fieldAndClassBoundary.getOne().getType()).andReturn(fieldAndClassBoundary.getTwo());
        EasyMock.expect(mockEasyMockWrapper.createMock(fieldMockControlDeux.getTwo(), fieldAndClassBoundary.getTwo())).andReturn(value);
        mockFieldSetter.setValue(fieldAndClassBoundary.getOne(),  instance, value);
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @SuppressionReason(SuppressionReason.Reason.TEST_VALUE)
    public AMockInjectorIncubator supplyExceptionalCondition() {
        exceptionSet = true;
        EasyMock.expect(mockFieldFinder.findByName(EasyMock.isA(String.class), EasyMock.eq(instance))).
                andThrow(new MockInjectionException(EXCEPTION_MESSAGE));
        return this;
    }

    public AMockInjectorIncubator isThrown() {
        return this;
    }

    public AMockInjectorIncubator exception() {
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), equalTo(EXCEPTION_MESSAGE));
        return this;
    }
}
