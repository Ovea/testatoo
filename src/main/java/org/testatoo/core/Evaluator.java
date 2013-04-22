/**
 * Copyright (C) 2013 Ovea <dev@testatoo.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testatoo.core;

import org.testatoo.core.component.Component;
import org.testatoo.core.nature.*;

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
public interface Evaluator<T> {

    String DEFAULT_NAME = Evaluator.class.getName() + ".DEFAULT";

    /**
     * @return The implementation used by this Evaluator. This can be useful to recover the underlying implementation for specific cases
     */
    T implementation();

    String name();

    /**
     * To open the page.
     *
     * @param url the url corresponding to the page we want to open
     */
    void open(String url);

    /**
     * To know if a given component is visible
     *
     * @param component the component we want to know if visible
     * @return True if the component is visible
     */
    Boolean isVisible(Component component);

    /**
     * To know if a given component is enabled
     *
     * @param component the component we want to know if enabled
     * @return True if the component is enabled
     */
    Boolean isEnabled(Component component);

    /**
     * To know if a given component ise focused
     *
     * @param component the component
     * @return True if the component is focused
     */
    Boolean isFocused(Component component);

    /**
     * To know if a checkable element is checked
     *
     * @param component an element that can be check (ex : radioButton, checkBox)
     *
     * @return True if the element is checked
     */
    Boolean isChecked(Checkable component);

    /**
     * To get the label of an element with label
     *
     * @param labelSupport an element that can have a associated label
     * @return the label associated to the element
     */
    String label(LabelSupport labelSupport);

    /**
     * To get the value displayed in a field
     *
     * @param valueSupport the field we want the value
     * @return the string displayed in the field
     */
    String value(ValueSupport valueSupport);

    /**
     * To get the text displayed on a TextSupport component
     *
     * @param textSupport component
     * @return the displayed text
     */
    String text(TextSupport textSupport);

    /**
     * To get the title of a given element
     *
     * @param titleSupport an element that can have a title
     * @return the title of the element
     */
    String title(TitleSupport titleSupport);

    /**
     * To get the reference of a given component
     *
     * @param referenceSupport the component
     * @return the reference of the link
     */
    String reference(ReferenceSupport referenceSupport);


}
