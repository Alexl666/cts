/*
 * Coordinate Transformations Suite (abridged CTS)  is a library developped to
 * perform Coordinate Transformations using well known geodetic algorithms
 * and parameter sets.
 * Its main focus are simplicity, flexibility, interoperability, in this order.
 *
 * This library has been originally developed by Michaël Michaud under the JGeod
 * name. It has been renamed CTS in 2009 and shared to the community from
 * the OrbisGIS code repository.
 *
 * CTS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License.
 *
 * CTS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * CTS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <https://github.com/orbisgis/cts/>
 */
package org.cts.parser.prj;

import java.util.Collections;
import java.util.List;

/**
 * @author Antoine Gourlay, Erwan Bocher
 */
public abstract class AbstractPrjElement implements PrjElement {

    /**
     * The list of the children of this PrjElement.
     */
    private List<PrjElement> children;

    /**
     * Create a PrjElement that uses the list of PrjElement in parameter as
     * children.
     *
     * @param children the children of the PrjElement to create
     */
    AbstractPrjElement(List<PrjElement> children) {
        this.children = children;
    }

    /**
     * Create a PrjElement without children.
     */
    AbstractPrjElement() {
        this(Collections.EMPTY_LIST);
    }

    @Override
    public List<PrjElement> getChildren() {
        return children;
    }
}
