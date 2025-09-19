/*
 * Copyright 2024, 2025 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.binary.iiif;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.annotations.Test;

public class IiifParametersTest {

	@Test
	public void testParameters() {
		Map<String, String> headers = Map.of("headerKey", "headerValue");

		IiifParameters parameters = new IiifParameters("someDataDivider", "someType", "someId",
				"someRepresentation", "someUri", "someMethod", headers);

		assertEquals(parameters.dataDivider(), "someDataDivider");
		assertEquals(parameters.type(), "someType");
		assertEquals(parameters.id(), "someId");
		assertEquals(parameters.representation(), "someRepresentation");
		assertEquals(parameters.uri(), "someUri");
		assertEquals(parameters.method(), "someMethod");
		assertEquals(parameters.headersMap(), headers);
	}
}
