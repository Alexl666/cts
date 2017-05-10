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

package org.cts;

import org.cts.crs.CoordinateReferenceSystem;
import org.cts.datum.Ellipsoid;
import org.cts.datum.GeodeticDatum;
import org.cts.op.CoordinateOperation;
import org.cts.op.CoordinateOperationFactory;
import org.cts.op.projection.Projection;
import org.cts.op.projection.TransverseMercator;
import org.cts.op.transformation.GeocentricTransformation;
import org.cts.op.transformation.GeocentricTranslation;
import org.cts.op.transformation.NTv2GridShiftTransformation;
import org.cts.registry.EPSGRegistry;
import org.cts.registry.IGNFRegistry;
import org.cts.registry.RegistryManager;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Tests for CRSHelper class
 */
public class CRSHelperTest {

    @Test
    public void testEpsgParser() throws Exception {
        CRSFactory factory = new CRSFactory();
        factory.getRegistryManager().addRegistry(new EPSGRegistry());
        CoordinateReferenceSystem crs = factory.getCRS("EPSG:27572");
        assertTrue(crs.getAuthorityName().equals("EPSG"));
        assertTrue(crs.getAuthorityKey().equals("27572"));
        GeodeticDatum datum = (GeodeticDatum)crs.getDatum();
        assertTrue(datum.equals(GeodeticDatum.NTF_PARIS));
        Set<GeocentricTransformation> ops = datum.getGeocentricTransformations(GeodeticDatum.WGS84);
        assertTrue(CoordinateOperationFactory.getMostPrecise(
                CoordinateOperationFactory.includeFilter(ops, GeocentricTranslation.class)) != null);
    }

    @Test
    public void testEpsgParser2() throws Exception {
        CRSFactory factory = new CRSFactory();
        factory.getRegistryManager().addRegistry(new EPSGRegistry());
        CoordinateReferenceSystem crs = factory.getCRS("EPSG:3874");
        assertTrue(crs.getAuthorityName().equals("EPSG"));
        assertTrue(crs.getAuthorityKey().equals("3874"));
        GeodeticDatum datum = (GeodeticDatum)crs.getDatum();
        assertTrue(datum.getEllipsoid().equals(Ellipsoid.GRS80));
        Projection projection = crs.getProjection();
        assertTrue(projection instanceof TransverseMercator);
    }

    @Test
    public void testIgnfParser() throws Exception {
        CRSFactory factory = new CRSFactory();
        factory.getRegistryManager().addRegistry(new IGNFRegistry());
        CoordinateReferenceSystem crs = factory.getCRS("IGNF:LAMB2");
        assertTrue(crs.getAuthorityName().equals("IGNF"));
        assertTrue(crs.getAuthorityKey().equals("LAMB2"));
        GeodeticDatum datum = (GeodeticDatum)crs.getDatum();
        assertTrue(datum.equals(GeodeticDatum.NTF_PARIS));
        Set<CoordinateOperation> ops = datum.getGeographicTransformations(GeodeticDatum.RGF93);
        assertTrue(CoordinateOperationFactory.getMostPrecise(
                CoordinateOperationFactory.includeFilter(ops, NTv2GridShiftTransformation.class)) != null);
        datum.removeAllTransformations();
        GeodeticDatum.RGF93.removeAllTransformations();
    }
}
