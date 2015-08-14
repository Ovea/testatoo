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
package org.testatoo.core.action

import org.testatoo.bundle.html5.Button
import org.testatoo.core.Component
import org.testatoo.core.ComponentException
import org.testatoo.core.state.Available

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class Reset implements Action {
    @Override
    void execute(Component c) {
        Button reset_button = c.find('[type=reset]:first')[0] as Button
        if (reset_button && reset_button.is(new Available()))
            reset_button.execute(new MouseClick())
        else
            throw new ComponentException('Cannot reset form without reset button')
    }
}
