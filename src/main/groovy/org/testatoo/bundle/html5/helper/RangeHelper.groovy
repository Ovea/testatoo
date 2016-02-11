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
package org.testatoo.bundle.html5.helper

import org.testatoo.core.component.Component
import static org.testatoo.core.Testatoo.*

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class RangeHelper {

    static Object getMinimun(Component c) {
        config.evaluator.eval(c.id, "it.prop('min')")
    }

    static Object getMaximum(Component c) {
        config.evaluator.eval(c.id, "it.prop('max')")
    }

    static Number getStep(Component c) {
        Object value = config.evaluator.eval(c.id, "it.prop('step')")
        if (value)
            value as BigDecimal
        else
            0
    }

    static boolean isInRange(Component c) {
        !isOutOfRange(c)
    }

    static boolean isOutOfRange(Component c) {
        config.evaluator.check(c.id, "it.is(':out-of-range')")
    }
}
