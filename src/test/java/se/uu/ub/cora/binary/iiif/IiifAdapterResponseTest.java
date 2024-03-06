/*
 * Copyright 2024 Uppsala University Library
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

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.testng.annotations.Test;

public class IiifAdapterResponseTest {

	@Test
	public void testIiifImageResponse() throws Exception {
		Map<String, String> headers = Map.of("someKey", "someValue");
		ByteArrayInputStream inputStream = new ByteArrayInputStream("someString".getBytes());

		IiifAdapterResponse record = new IiifAdapterResponse(200, headers, inputStream);

		assertEquals(record.status(), 200);
		assertEquals(record.headers(), headers);
		assertEquals(record.body(), inputStream);
	}

}
