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
package com.googlecode.pinthura.factory.instantiator;

import com.googlecode.pinthura.annotation.AnnotationFinder;
import com.googlecode.pinthura.annotation.SuppressionReason;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundary;
import com.googlecode.pinthura.boundary.java.lang.ClassBoundaryFactory;
import com.googlecode.pinthura.factory.Implementation;
import com.googlecode.pinthura.factory.MethodParam;

/**
 * Extracts the <code>Implementation</code> annotation from a <code>MethodParam</code> object.
 */
public final class AnnotatedClassExtractorImpl implements AnnotatedClassExtractor {

    private final AnnotationFinder annotationFinder;
    private final ClassBoundary<Implementation> annotatation;
    private final ClassBoundaryFactory classBoundaryFactory;

    public AnnotatedClassExtractorImpl(final AnnotationFinder annotationFinder, final ClassBoundaryFactory classBoundaryFactory) {
        this.annotationFinder = annotationFinder;
        this.classBoundaryFactory = classBoundaryFactory;
        annotatation = classBoundaryFactory.create(Implementation.class);
    }

    @SuppressWarnings("unchecked")
    @SuppressionReason(SuppressionReason.Reason.CANT_INFER_GENERICS)
    public <T> ClassBoundary<T> extract(final MethodParam methodParam) {
        return (ClassBoundary<T>) classBoundaryFactory.create(annotationFinder.find(methodParam.getMethod(), annotatation).value());
    }
}
