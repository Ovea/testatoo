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
package org.testatoo.component

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.firefox.FirefoxDriver
import org.testatoo.core.Testatoo
import org.testatoo.core.component.datagrid.Cell
import org.testatoo.core.component.datagrid.Column
import org.testatoo.core.component.datagrid.DataGrid
import org.testatoo.core.component.datagrid.Row
import org.testatoo.core.evaluator.webdriver.WebDriverEvaluator

import static org.testatoo.core.Testatoo.*
import static org.testatoo.core.property.Properties.*
import static org.testatoo.core.state.States.*

/**
 * Created by david on 29/01/15.
 */
@RunWith(JUnit4)
class DataGridTest {

    @BeforeClass
    public static void setup() {
        Testatoo.evaluator = new WebDriverEvaluator(new FirefoxDriver())
        open 'http://localhost:8080/components.html'
    }

    @AfterClass
    public static void tearDown() { evaluator.close() }

    @Test
    public void should_have_expected_behaviours() {
        DataGrid data_grid = $('#data_grid') as DataGrid

        data_grid.should { be enabled }
        data_grid.should { be visible }

        data_grid.should { have 3.columns }
        assert data_grid.columns.size == 3

        data_grid.should { have 4.rows }
        assert data_grid.rows.size == 4

        data_grid.columns[0].should { have title('Column 1 title') }
        data_grid.columns[1].should { have title('Column 2 title') }
        data_grid.columns[2].should { have title('Column 3 title') }

        List<Column> columns = data_grid.columns

        columns[0].should { have 4.cells }
        assert columns[0].cells.size == 4

        List<Cell> cells = columns[1].cells

        cells[0].should { have value('cell 12') }
        cells[1].should { have value('cell 22') }
        cells[2].should { have value('cell 32') }
        cells[3].should { have value('cell 42') }

        columns[2].cells[3].should { have value('cell 43') }

        List<Row> rows = data_grid.rows
        rows[0].should { have 3.cells }
        assert rows[0].cells.size == 3

        cells = rows[1].cells

        cells[0].should { have value('cell 21') }
        cells[1].should { have value('cell 22') }
        cells[2].should { have value('cell 23') }

        rows[2].cells[1].should { have value('cell 32') }
    }
}
