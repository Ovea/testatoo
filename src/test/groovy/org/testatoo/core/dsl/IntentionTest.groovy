/**
 * Copyright (C) 2016 Ovea (dev@ovea.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testatoo.core.dsl

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.testatoo.WebDriverConfig
import org.testatoo.bundle.html5.component.CheckBox
import org.testatoo.bundle.html5.component.Div
import org.testatoo.bundle.html5.component.Form
import org.testatoo.bundle.html5.component.Radio
import org.testatoo.bundle.html5.component.input.InputTypeEmail
import org.testatoo.bundle.html5.component.input.InputTypePassword
import org.testatoo.bundle.html5.component.input.InputTypeText
import org.testatoo.bundle.html5.component.list.MultiSelect
import org.testatoo.bundle.html5.component.list.Select
import org.testatoo.core.ComponentException
import org.testatoo.core.component.Dropdown
import org.testatoo.core.component.ListBox
import org.testatoo.core.component.field.EmailField
import org.testatoo.core.component.field.PasswordField
import org.testatoo.core.component.field.TextField

import static org.junit.Assert.fail
import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.dsl.Actions.*
import static org.testatoo.core.input.Mouse.clickOn

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class IntentionTest {

    @Rule
    public WebDriverConfig driver = new WebDriverConfig()

    @Before
    public void before() {
        browser.open 'http://localhost:8080/dsl.html'
    }

    @Test
    public void should_be_able_to_check_and_uncheck_a_checkbox() {
        CheckBox checkbox = $('#checkbox') as CheckBox

        assert checkbox.unchecked
        check checkbox
        assert checkbox.checked
        uncheck checkbox
        assert checkbox.unchecked

        try {
            uncheck checkbox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'CheckBox CheckBox:checkbox is already unchecked and cannot be unchecked'
        }

        check checkbox
        assert checkbox.checked

        try {
            check checkbox
            fail()
        } catch (ComponentException e) {
            assert e.message == 'CheckBox CheckBox:checkbox is already checked and cannot be checked'
        }
    }

    @Test
    public void should_be_able_to_only_check_a_radio() {
        Radio radio = $('#radio') as Radio
        radio.unchecked

        check radio
        radio.checked

        // Try to check again fail
        try {
            check radio
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Radio Radio:radio is already checked and cannot be checked'
        }
    }

    @Test
    public void given_input_with_value_when_fill_value_the_field_is_reset_before() {
        TextField textField = $('#text_field') as InputTypeText

        clickOn textField
        type 'Some fields'

        assert textField.value == 'Some fields'
        fill textField with 'Other fields'
        assert textField.value == 'Other fields'
    }

    @Test
    public void given_input_with_value_when_fill_value_we_trigger_a_blur_event() {
        TextField field = $('#firstname') as InputTypeText
        Div panel = $('#firstname_blur') as Div

        assert panel.hidden
        fill field with 'invalid value'
        assert panel.visible
    }

    @Test
    public void given_input_with_value_when_clear_value_we_trigger_a_change_and_blur_event() {
        TextField field = $('#lastname') as InputTypeText
        Div panel = $('#lastname_reset') as Div

        assert panel.hidden
        clear field
        assert panel.visible
    }

    @Test
    public void should_be_able_to_clear_input() {
        TextField textField = $('#text_field') as InputTypeText
        fill textField with 'Some fields'

        assert textField.filled
        assert textField.value == 'Some fields'

        clear textField

        assert textField.empty
        assert textField.value == ''
    }

    @Test
    public void should_be_able_to_set_form_easily() {
        Form form = $('#dsl-form') as Form
        EmailField email = $('#dsl-form [type=email]') as InputTypeEmail
        PasswordField password = $('#dsl-form [type=password]') as InputTypePassword
        Message message = $('#dsl-form .alert') as Message

        assert message.title == 'The form was submitted 0 time(s)'

        fill email with 'my@email.org'
        fill password with 'password'

        assert email.value == 'my@email.org'
        assert password.value == 'password'

        reset form

        assert email.value == ''
        assert password.value == ''

        submit form

        assert message.title == 'The form was submitted 1 time(s)'
    }

    @Test
    public void should_be_able_to_select_element_in_dropdown_an_listbox() {
        Dropdown dropdown = $('#elements') as Select
        assert dropdown.selectedItem.value == 'H'
        
        on dropdown select 'Pol'
        assert dropdown.selectedItem.value == 'Pol'

        ListBox listBox = $('#cities') as MultiSelect

        listBox.items.containsAll('Montreal', 'Quebec', 'Montpellier', 'New York', 'Casablanca', 'Munich')
        listBox.selectedItems.containsAll('New York', 'Munich')

        on listBox unselect 'New York', 'Munich'
        assert listBox.item('New York').unselected
        assert listBox.item('Munich').unselected

        on listBox select 'New York', 'Munich'
        assert listBox.item('New York').selected
        assert listBox.item('Munich').selected

        on listBox select 'Montreal'
        assert listBox.items[0].selected

        on listBox select 'Montpellier'
        listBox.items[2].selected

        on listBox select listBox.items[4]
        assert listBox.items[4].selected

        on listBox unselect listBox.items[4]
        assert listBox.items[4].unselected

        select listBox.items[4]
        assert listBox.items[4].selected

        unselect listBox.items[4]
        assert listBox.items[4].unselected

        on listBox unselect(listBox.items[0], listBox.items[2])
        assert listBox.items[0].unselected
        assert listBox.items[1].unselected

        on listBox select(listBox.items[0], listBox.items[2])
        assert listBox.items[0].selected
        assert listBox.items[2].selected

        unselect listBox.items[0], listBox.items[2]
        assert listBox.items[0].unselected
        assert listBox.items[2].unselected

        select listBox.items[0], listBox.items[2]
        assert listBox.items[0].selected
        assert listBox.items[2].selected

        try {
            on listBox select 'Quebec'
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be selected'
        }
    }

    class Message extends Div {
        @Override
        String getTitle() {
            config.evaluator.eval(id, "it.text()")
        }
    }
}
