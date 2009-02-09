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
package com.googlecode.pinthura.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The purpose of this annotation is to document the reasons for using the <code>SuppressWarnings</code> annotation.
 * If you can't find the appropriate reason for the suppression within <code>Reason</code> use a value of <code>Reason.OTHER</code>
 * and define the reason using the <code>desc</code> property. Alternatively if this is useful add it to <code>Reason</code>.
 */
@Target({ PACKAGE, FIELD, TYPE, CONSTRUCTOR, METHOD, PARAMETER, LOCAL_VARIABLE })
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressionReason {

    /**
     * The reasons for the Suppression. Could be one or more <code>Reason</code> values.
     * @return The reasons for the Suppression.
     */
    Reason[] value();

    /**
     * An additional description if using the <code>Reason.OTHER</code> value or to clarify one of the other <code>Reason</code> values.
     * @return A description if any.
     */
    String desc() default "";

    enum Reason {
                    BUILDER_PATTERN, /* The builder pattern is being used and hence instances will be returned from methods. */
                    TEST_VALUE, /* A test value is being used. */
                    TEST_TYPE, /* A test type is being used. eg. Deux, Tres.*/
                    CANT_INFER_GENERICS, /* can't infer generics for one reason or another. use desc if you need to. */
                    CANT_CREATE_GENERIC_ARRAY, /* can't create generic arrays.  */
                    CANT_INFER_GENERICS_ON_MOCKS, /* can't infer generic on mock objects. */
                    INCUBATOR, /* The incubator pattern is being used, instances of the class will be returned */
                    METHOD_CHAIN, /* Methods are chained so instances will be returned as in the builder or incubator patterns. */
                    INJECTED, /* The value is injected and so is not set. */
                    GENERATED_CODE, /* The code is generated so may have wierd logic and/or formatting. */
                    OTHER /* Some other reason than the above. Use desc to define what the reason is. */
                }
}
