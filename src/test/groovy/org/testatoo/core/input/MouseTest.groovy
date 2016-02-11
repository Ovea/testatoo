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
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.testatoo.WebDriverConfig
import org.testatoo.bundle.html5.component.*
import org.testatoo.bundle.html5.component.list.Select

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.input.Key.*
import static org.testatoo.core.input.Mouse.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class MouseTest {

    @ClassRule
    public static WebDriverConfig driver = new WebDriverConfig()

    @BeforeClass
    public static void before() {
        browser.open 'http://localhost:8080/mouse.html'
    }

    @Before
    public void setup() {
        browser.navigate.refresh()
    }

    @Test
    public void should_be_able_to_click() {
        Button button = $('#button_1') as Button
        assert  button.text == 'Button'
        clickOn button
        assert button.text == 'Button Clicked!'

        browser.navigate.refresh()

        button = $('#button_1') as Button
        assert  button.text == 'Button'
        button.click()
        assert button.text == 'Button Clicked!'

        CheckBox checkBox = $('#checkbox') as CheckBox
        assert checkBox.unchecked
        clickOn checkBox
        assert checkBox.checked

        Radio radio = $('#radio') as Radio
        assert radio.unchecked
        clickOn radio
        assert radio.checked

        Select dropDown = $('#elements') as Select
        dropDown.selectedItem.value == 'H'

        clickOn dropDown.items[2]
        dropDown.selectedItem.value == 'Pol'
    }

    @Test
    public void should_be_able_to_doubleClick() {
        Button button = $('#button_2') as Button

        assert button.text == 'Button'
        doubleClickOn button
        assert button.text == 'Button Double Clicked!'

        browser.navigate.refresh()

        button = $('#button_2') as Button

        assert button.text == 'Button'
        button.doubleClick()
        assert button.text == 'Button Double Clicked!'
    }

    @Test
    public void should_be_able_to_rightClick() {
        Button button = $('#button_5') as Button

        assert button.text == 'Button'
        rightClickOn button
        assert button.text == 'Button Right Clicked!'

        browser.navigate.refresh()

        button = $('#button_5') as Button

        assert button.text == 'Button'
        button.rightClick()
        assert button.text == 'Button Right Clicked!'
    }

    @Test
    public void should_be_able_to_mouseOver() {
        Button button = $('#button_3') as Button
        assert button.text == 'Button'
        hoveringMouseOn button
        assert button.text == 'Button Mouse Over!'
    }

    @Test
    public void should_be_able_to_mouseOut() {
        Button button = $('#button_4') as Button
        assert button.text == 'Button'

        // To simulate mouse out
        // 1 - mouse over the component
        hoveringMouseOn button
        // 2 - mouse over an another component
        hoveringMouseOn $('#button_5') as Button
        // The mouse out is triggered
        assert button.text == 'Button Mouse Out!'
    }

    @Test
    public void should_be_able_to_dragAndDrop() {
        DropPanel dropPanel = $('#droppable') as DropPanel
        assert dropPanel.title == 'Drop here'

        Div dragPanel = $('#draggable') as Div
        drag dragPanel on dropPanel
        assert dropPanel.title == 'Dropped!'

        browser.navigate.refresh()

        dropPanel = $('#droppable') as DropPanel
        assert dropPanel.title == 'Drop here'

        dragPanel = $('#draggable') as Div
        dragPanel.drag().on(dropPanel)
        assert dropPanel.title == 'Dropped!'
    }

    @Test
    public void should_be_able_to_use_mouse_with_key_modifier() {
        Span span_Ctrl_mouseleft = $('#span_Ctrl_mouseleft') as Span
        Span span_Shift_mouseleft = $('#span_Shift_mouseleft') as Span

        assert span_Ctrl_mouseleft.missing
        assert span_Shift_mouseleft.missing

        CTRL.click $('#_Ctrl_mouseleft') as Div
        SHIFT.click $('#_Shift_mouseleft') as Div

        assert span_Ctrl_mouseleft.available
        assert span_Shift_mouseleft.available

        // Not testable cause Rightclick Handled by the browser
        CTRL.rightClick $('#_Ctrl_mouseright') as Div
        [CTRL, ALT].rightClick $('#_Ctrl_mouseright') as Div

        Span span_Alt_Shift_mouseleft = $('#span_Alt_Shift_mouseleft') as Span
        assert span_Alt_Shift_mouseleft.missing
        (ALT + SHIFT).click $('#_Alt_Shift_mouseleft') as Div
        assert span_Alt_Shift_mouseleft.available

        Span span_Crtl_Shift_mouseleft = $('#span_Crtl_Shift_mouseleft') as Span
        assert span_Crtl_Shift_mouseleft.missing
        [CTRL, SHIFT].click $('#_Ctrl_Shift_mouseleft') as Div
        assert span_Crtl_Shift_mouseleft.missing

        // For code coverage
        [SPACE].click $('#_Ctrl_Shift_mouseleft') as Div
        ['data'].click $('#_Ctrl_Shift_mouseleft') as Div
    }

    class DropPanel extends Div {
        String getTitle() {
            config.evaluator.eval(id, "it.find('h1').text()")
        }
    }
}
