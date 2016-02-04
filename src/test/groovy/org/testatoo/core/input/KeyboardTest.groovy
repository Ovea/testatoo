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
package org.testatoo.core.input

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.testatoo.WebDriverConfig
import org.testatoo.bundle.html5.component.Button
import org.testatoo.bundle.html5.component.Span
import org.testatoo.bundle.html5.component.input.InputTypeText
import org.testatoo.core.component.field.TextField

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.input.Key.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class KeyboardTest {

    @Rule
    public WebDriverConfig driver = new WebDriverConfig()

    @Before
    public void before() {
        browser.open 'http://localhost:8080/keyboard.html'
        // TODO remove when FF issue on new driver is fixed => https://code.google.com/p/selenium/issues/detail?id=7937
        mouse.clickOn($('#button') as Button)
        Thread.sleep(500);
    }

    @Test
    public void should_type_letters_on_keyboard() {
        (0..25).each {
            char letter = (char) (('a' as char) + it)
            Span current_span = $("#span_$letter") as Span

            assert current_span.missing
            keyboard.type "$letter"
            assert current_span.available
        }
    }

    @Test
    public void should_type_number_on_keyboard() {
        (0..9).each {
            Span current_span = $("#span_$it") as Span
            assert  current_span.missing
            keyboard.type "$it"
            assert current_span.available
        }
    }

    @Test
    public void should_type_special_key_on_keyboard() {
        [
                '#span_esc'      : ESCAPE,
                '#span_f1'       : F1,
                '#span_f2'       : F2,
                '#span_f3'       : F3,
                '#span_f4'       : F4,
                '#span_f5'       : F5,
                '#span_f6'       : F6,
                '#span_f7'       : F7,
                '#span_f8'       : F8,
                '#span_f9'       : F9,
                '#span_f10'      : F10,
                '#span_f11'      : F11,
                '#span_f12'      : F12,
                '#span_insert'   : INSERT,
                '#span_del'      : DELETE,
                '#span_pageup'   : PAGE_UP,
                '#span_pagedown' : PAGE_DOWN,
                '#span_home'     : HOME,
                '#span_end'      : END,
                '#span_backspace': BACK_SPACE,
                '#span_divide'   : DIVIDE,
                '#span_multiply' : MULTIPLY,
                '#span_substract': SUBTRACT,
                '#span_add'      : ADD,
                '#span_equals'   : EQUALS,
                '#span_tab'      : TAB,
                '#span_return'   : RETURN,
                '#span_space'    : SPACE,
                '#span_left'     : LEFT,
                '#span_up'       : UP,
                '#span_right'    : RIGHT,
                '#span_down'     : DOWN
        ].each { k, v ->
            Span current_span = $(k) as Span
            assert current_span.missing
            keyboard.type v
            assert current_span.available
        }
    }

    @Test
    public void should_use_key_modifier_on_keyboard() {
        Span span = $('#span_Ctrl_Alt_Shift_x') as Span
        assert span.missing
        keyboard.type(CTRL + ALT + SHIFT + 'x')
        assert span.available

        TextField textField = $('#textfield') as InputTypeText

        assert textField.value == ''
        mouse.clickOn textField
        keyboard.type(SHIFT + 'testatoo')
        assert textField.value == 'TESTATOO'

        textField.clear()
        assert textField.value == ''
        keyboard.type('~!@#$%^&*()_+')
        assert textField.value == '~!@#$%^&*()_+'

        textField.clear()
        assert textField.value == ''
        keyboard.type(SHIFT + '`1234567890-=')
        assert textField.value == '~!@#$%^&*()_+'
    }
}
