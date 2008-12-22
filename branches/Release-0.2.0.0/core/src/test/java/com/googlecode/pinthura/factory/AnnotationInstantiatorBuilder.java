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
package com.googlecode.pinthura.factory;

import com.googlecode.pinthura.factory.instantiator.AnnotationInstantiator;
import com.googlecode.pinthura.factory.instantiator.AnnotatedClassExtractorImpl;
import com.googlecode.pinthura.factory.instantiator.ConstructorLocatorImpl;
import com.googlecode.pinthura.factory.instantiator.ConstructorInstantiatorImpl;
import com.googlecode.pinthura.annotation.AnnotationFinderImpl;

public final class AnnotationInstantiatorBuilder {

    public AnnotationInstantiator build() {
        ConstructorLocatorImpl constructorLocator = new ConstructorLocatorImpl();
        ConstructorInstantiatorImpl constructorInstantiator = new ConstructorInstantiatorImpl();

        return new AnnotationInstantiator(new AnnotatedClassExtractorImpl(new AnnotationFinderImpl()), constructorLocator,
                constructorInstantiator);
    }
}