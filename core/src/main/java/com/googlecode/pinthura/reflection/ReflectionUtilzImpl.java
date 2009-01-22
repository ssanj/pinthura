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
package com.googlecode.pinthura.reflection;

import com.googlecode.pinthura.factory.boundary.FieldBoundary;
import com.googlecode.pinthura.factory.instantiator.ClassInstance;

//TODO: Delete.
public final class ReflectionUtilzImpl implements ReflectionUtilz {

    public <T> FieldBoundary[] findFields(ClassInstance classInstance, final String prefix) {
//        FieldBoundary[] fields = classInstance.getClazz().getDeclaredFields();
//
//        List<FieldBoundary> filteredFields = new ArrayList<FieldBoundary>();
//        for (FieldBoundary field : fields) {
//            if (field.getName().startsWith(prefix)) {
//                filteredFields.add(field);
//            }
//        }
//
//        return Arrayz.fromCollection(filteredFields, FieldBoundary.class);
        return null;
    }
}
