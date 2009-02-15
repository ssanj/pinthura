/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.pinthura.factory.locator.deriver;

import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryImpl;
import com.googlecode.pinthura.data.Square;
import com.googlecode.pinthura.data.UrlBoundary;
import com.googlecode.pinthura.factory.MethodParam;
import com.googlecode.pinthura.test.types.Deux;
import com.googlecode.pinthura.util.builder.RandomDataChooserBuilder;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public final class AnImplSuffixingDeriverUnderTest {

    private IMocksControl mockControl;
    private ClassNameDeriver locator;
    private MethodParam mockMethodParam;
    private Deux<ClassBoundary, String> classBoundaryStringDeux;

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.SIMPLIFY_GENERICS)
    @Before
    public void setup() {
        mockControl = EasyMock.createControl();
        mockMethodParam = mockControl.createMock(MethodParam.class);
        locator = new ImplSuffixingDeriver();
        classBoundaryStringDeux = new RandomDataChooserBuilder().build().
                chooseOneOf(new Deux<ClassBoundary, String>(new ClassBoundaryImpl<UrlBoundary>(UrlBoundary.class),
                                                            "com.googlecode.pinthura.data.UrlBoundaryImpl"),
                            new Deux<ClassBoundary, String>(new ClassBoundaryImpl<Square>(Square.class),
                                                            "com.googlecode.pinthura.data.SquareImpl"),
                            new Deux<ClassBoundary, String>(new ClassBoundaryImpl<MethodParam>(MethodParam.class),
                                                            "com.googlecode.pinthura.factory.MethodParamImpl"));
    }

    @Test
    public void shouldReturnAnInterfaceNameSuffixedWithAnImpl() {
        expectImplementation(classBoundaryStringDeux);
    }

    private void expectImplementation(final Deux<ClassBoundary, String> interfaceAndImplementationDeux) {
        EasyMock.expect(mockMethodParam.getReturnType());
        EasyMock.expectLastCall().andReturn(interfaceAndImplementationDeux.one());
        mockControl.replay();

        assertThat(locator.derive(mockMethodParam), equalTo(interfaceAndImplementationDeux.two()));

        mockControl.verify();
    }
}
