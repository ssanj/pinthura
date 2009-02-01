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
 * The purpose of this annotation is to document the reason for using the @SuppressWarnings annotation.
 */
@Target({ PACKAGE, FIELD, TYPE, CONSTRUCTOR, METHOD, PARAMETER, LOCAL_VARIABLE })
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressionReason {

    Reason[] value();

    String desc() default "";

    enum Reason {
                    BUILDER_PATTERN,
                    TEST_VALUE,
                    CANT_INFER_GENERICS,
                    CANT_INFER_GENERICS_ON_MOCKS,
                    INCUBATOR,
                    METHOD_CHAIN,
                    INJECTED,
                    GENERATED_CODE,
                    OTHER
                }
}
