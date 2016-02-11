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
package org.testatoo.bundle.html5.component.list

import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.testatoo.WebDriverConfig
import org.testatoo.core.ComponentException
import org.testatoo.core.component.Item
import org.testatoo.core.component.ListBox
import org.testatoo.core.support.MultiSelectable

import static org.junit.Assert.fail
import static org.testatoo.core.Testatoo.$
import static org.testatoo.core.Testatoo.getBrowser

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class MultiSelectTest {

    @ClassRule
    public static WebDriverConfig driver = new WebDriverConfig()

    @BeforeClass
    public static void before() {
        browser.open 'http://localhost:8080/components.html'
    }

    @Test
    public void should_have_expected_behaviours() {
        assert MultiSelect in ListBox
        assert ListBox in MultiSelectable

        ListBox cities = $('#cities') as MultiSelect

        assert cities.items.size() == 6
        assert cities.visibleItems.size() == 3
        assert cities.multiSelectable

        Item montreal = cities.item('Montreal')
        Item quebec = cities.item('Quebec')
        Item montpellier = cities.item('Montpellier')
        Item newYork = cities.item('New York')
        Item casablanca = cities.item('Casablanca')
        Item munich = cities.item('Munich')

        assert montreal.unselected
        assert montpellier.enabled
        assert cities.item('Montreal').unselected

        assert quebec.unselected
        assert quebec.disabled
        assert cities.item('Quebec').unselected

        assert montpellier.unselected
        assert cities.item('Montpellier').unselected

        assert newYork.selected
        assert cities.item('New York').selected

        assert casablanca.unselected
        assert cities.item('Casablanca').unselected

        assert munich.selected
        assert cities.item('Munich').selected

        assert cities.selectedItems.containsAll(newYork, munich)

        cities.select('Montpellier', 'Montreal')
        assert cities.item('Montpellier').selected
        assert cities.item('Montreal').selected
        assert cities.selectedItems.containsAll(newYork, munich, montpellier, montreal)

        montreal.unselect()
        montpellier.unselect()

        assert cities.item('Montreal').unselected
        assert cities.item('Montpellier').unselected
        assert cities.item('New York').selected
        assert cities.item('Munich').selected

        cities.select(montreal, montpellier)
        assert cities.item('Montreal').selected
        assert cities.item('Montpellier').selected
        assert cities.item('New York').selected
        assert cities.item('Munich').selected

        montpellier.click() // Now just Montpellier is selected
        assert montpellier.selected
        assert montreal.unselected
        assert newYork.unselected
        assert munich.unselected

        try {
            quebec.select()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be selected'
        }

        try {
            quebec.unselect()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Quebec is disabled and cannot be unselected'
        }

        try {
            newYork.unselect()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option New York is already unselected and cannot be unselected'
        }

        try {
            montpellier.select()
            fail()
        } catch (ComponentException e) {
            assert e.message == 'Option Montpellier is already selected and cannot be selected'
        }

        MultiSelect planets = $('#planets') as MultiSelect
        assert planets.visibleItems.size() == 5
        assert !planets.multiSelectable

        assert planets.groupItems.size() == 2
        assert planets.groupItems[0].value == 'Cat-1'
        assert planets.groupItem('Cat-1').value == 'Cat-1'
    }

    @Test
    public void groupItem_should_have_expected_behaviours() {
        ListBox planets = $('#planets') as MultiSelect
        OptionGroup groupItem = planets.groupItem('Cat-1')

        assert groupItem.items.size() == 4
        assert groupItem.items[0].value == 'Mercury'
        assert groupItem.item('Earth').value == 'Earth'
    }
}
