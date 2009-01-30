package com.googlecode.pinthura.factory.instantiator.injected;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.factory.Factory;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;
import com.googlecode.pinthura.factory.instantiator.ClassInstanceFactory;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

public final class FactoryAnnoationClassInstance {
    private final Factory mockFactory;
    private final Class<?> clazz;
    private final Object mockInstance;
    private final ClassInstance mockClassInstance;

    @SuppressWarnings({"unchecked"})
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS_ON_MOCKS)
    public FactoryAnnoationClassInstance(final IMocksControl mockControl,
        final ClassInstanceFactory mockClassInstanceFactory, final Class<?> clazz) {
        this.clazz = clazz;
        mockFactory = mockControl.createMock(Factory.class);
        mockInstance = mockControl.createMock(clazz);
        mockClassInstance = mockControl.createMock(ClassInstance.class);

        EasyMock.expect(mockClassInstanceFactory.createClassInstance((Class<Object>) clazz, mockInstance)).
                andReturn(mockClassInstance);
    }

    public Factory getFactory() {
        return mockFactory;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getInstance() {
        return mockInstance;
    }

    public ClassInstance getClassInstance() {
        return mockClassInstance;
    }

    @SuppressWarnings({"RedundantIfStatement"})
    @SuppressionReason(SuppressionReason.Reason.GENERATED_CODE)
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactoryAnnoationClassInstance that = (FactoryAnnoationClassInstance) o;

        if (!clazz.equals(that.clazz)) return false;
        if (!mockFactory.equals(that.mockFactory)) return false;
        if (!mockInstance.equals(that.mockInstance)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mockFactory.hashCode();
        result = 31 * result + clazz.hashCode();
        result = 31 * result + mockInstance.hashCode();
        return result;
    }
}
