/**
 * Copyright (C) 2014 Ovea (dev@ovea.com)
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
package org.testatoo.core.internal

import org.testatoo.bundle.html5.list.Item
import org.testatoo.core.Component
import org.testatoo.core.ComponentException
import org.testatoo.core.action.*
import org.testatoo.core.dsl.Block
import org.testatoo.core.dsl.Blocks
import org.testatoo.core.dsl.Key
import org.testatoo.core.property.Properties
import org.testatoo.core.property.matcher.PropertyMatcher
import org.testatoo.core.state.States

import java.time.Duration

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class TestatooExtensions {

    public static Collection<?> plus(Key a, Key b) { [a, b] }

    public static Collection<?> plus(Key a, String b) { [a, b] }

    static void click(Key key, Component c) { click([key], c) }

    static void rightClick(Key key, Component c) { rightClick([key], c) }

    static void click(Collection<Key> keys, Component c) {
        c.execute(new MouseClick([MouseModifiers.LEFT, MouseModifiers.SINGLE], keys))
    }

    static void rightClick(Collection<Key> keys, Component c) {
        c.execute(new MouseClick([MouseModifiers.RIGHT, MouseModifiers.SINGLE], keys))
    }

    static void select(Component c, String... values) {
        if (values) {
            if (values.length > 1) {
                c.is(States.multiSelectable)
            }

            for (value in values) {
                Item item = c.items.find { it.value == value } as Item
                if (item.is(States.selected))
                    throw new ComponentException("${item.class.simpleName} ${item} is already selected")
                item.execute(new Select())
            }
        } else {
            c.execute(new Select())
        }
    }

    static void select(Component c, Component selected) {
        selected.execute(new Select())
    }

    static void unselect(Component c, String... values) {
        if (values) {
            for (value in values) {
                Item item = c.items.find { it.value == value } as Item
                if (item.is(States.unselected))
                    throw new ComponentException("${item.class.simpleName} ${item} is already unselected")
                item.execute(new Unselect())
            }
        } else {
            c.execute(new Unselect())
        }
    }

    static void unselect(Component c, Component selected) {
        selected.execute(new Unselect())
    }

    static void with(Component c, String value) {
        c.execute(new Fill(value))
    }

    static boolean asBoolean(Block block) {
        Blocks.run(block)
        return true
    }

    public static PropertyMatcher getItems(Integer expected) {
        Properties.size.equalsTo(expected)
    }

    public static PropertyMatcher getGroupItems(Integer expected) {
        Properties.groupItemsSize.equalsTo(expected)
    }

    public static PropertyMatcher getColumns(Integer expected) {
        Properties.columnSize.equalsTo(expected)
    }

    public static PropertyMatcher getRows(Integer expected) {
        Properties.size.equalsTo(expected)
    }

    public static PropertyMatcher getCells(Integer expected) {
        Properties.size.equalsTo(expected)
    }

    public static PropertyMatcher getVisibleItems(Integer expected) {
        Properties.visibleItemsSize.equalsTo(expected)
    }

    public static PropertyMatcher getParagraphs(Integer expected) {
        Properties.paragraphSize.equalsTo(expected)
    }

    public static PropertyMatcher getArticles(Integer expected) {
        Properties.articleSize.equalsTo(expected)
    }

    public static Duration getSeconds(Number self) { Duration.ofSeconds(self.longValue()) }
}