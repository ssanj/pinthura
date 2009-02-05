package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.util.CreationBroker;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import static org.junit.Assert.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@SuppressWarnings("MethodReturnOfConcreteClass")
@SuppressionReason(SuppressionReason.Reason.INCUBATOR)
public final class AFactoryCreatorIncubator {

    private final IMocksControl mockControl;
    private final InvocationHandler mockInvocationHandler;
    private final CreationBroker mockCreationBroker;
    private Class<?> factoryClass;
    private Object result;
    private FactoryCreator factoryCreator;

    public AFactoryCreatorIncubator() {
        mockControl = EasyMock.createControl();
        mockInvocationHandler = mockControl.createMock(InvocationHandler.class);
        mockCreationBroker = mockControl.createMock(CreationBroker.class);
    }

    public AFactoryCreatorIncubator supplyParameterFactoryClass(final Class<?> factoryClass) {
        this.factoryClass = factoryClass;
        return this;
    }


    public AFactoryCreatorIncubator performCreate() {
        mockCreationBroker.setInstance(EasyMock.eq(FactoryCreator.class), EasyMock.isA(FactoryCreatorImpl.class));
        mockControl.replay();

        factoryCreator = new FactoryCreatorImpl(mockInvocationHandler, mockCreationBroker);
        result = factoryCreator.create(factoryClass);

        return this;
    }

    public AFactoryCreatorIncubator observe() {
        return this;
    }

    public AFactoryCreatorIncubator expectedInstance() {
        assertThat("Proxy created is not assignable to " + factoryClass.getName(), factoryClass.isAssignableFrom(result.getClass()),
                equalTo(true));
        assertThat(result, IsNull.notNullValue());
        assertThat(Proxy.isProxyClass(result.getClass()), equalTo(true));
        assertThat(Proxy.getInvocationHandler(result), IsSame.sameInstance(mockInvocationHandler));
        return this;
    }

    public AFactoryCreatorIncubator isCreated() {
        return this;
    }


    public AFactoryCreatorIncubator isCached() {
        assertThat("Instance created was not cached.", result == factoryCreator.create(factoryClass), equalTo(true));
        return this;
    }

    public void done() {
        mockControl.verify();
    }
}
