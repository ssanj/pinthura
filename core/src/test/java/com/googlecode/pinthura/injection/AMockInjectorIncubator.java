package com.googlecode.pinthura.injection;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.factory.boundary.ClassBoundary;
import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.reflection.FieldFinder;
import com.googlecode.pinthura.reflection.FieldSetter;
import com.googlecode.pinthura.util.Deux;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class AMockInjectorIncubator {

    private IMocksControl mockControl;
    private MockInjector mockInjector;
    private FieldFinder mockFieldFinder;
    private FieldSetter mockFieldSetter;
    private EasyMockWrapper mockEasyMockWrapper;
    private Object instance;
    private Deux<FieldBoundary,IMocksControl> fieldMockControlDeux;
    private List<Deux<FieldBoundary,ClassBoundary>> fieldsDeux;
    private Map<String, Deux<FieldBoundary,ClassBoundary>> fieldsDeuxByName;
    private String prefix;

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

    public <T> AMockInjectorIncubator supplyField(final String fieldName) {
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
        List<FieldBoundary<?>> fieldsPrefixedWithMock = new ArrayList<FieldBoundary<?>>();
        for (Deux<FieldBoundary, ClassBoundary> fieldBoundaryClassBoundaryDeux : fieldsDeux) {
            fieldsPrefixedWithMock.add(fieldBoundaryClassBoundaryDeux.getOne());
        }

        EasyMock.expect(mockFieldFinder.findByPrefix(prefix, instance)).andReturn(fieldsPrefixedWithMock);
        mockControl.replay();
        mockInjector.inject(instance);
        return this;
    }

    public AMockInjectorIncubator observe() {
        return this;
    }

    public AMockInjectorIncubator noErrors() {
        return this;
    }

    public AMockInjectorIncubator areReturned() {
        return this;
    }

    public void done() {
        mockControl.verify();
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
}
