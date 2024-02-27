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

/**
 * This interface is used to specify iiif image api for Cora. Please read further in link below.
 * 
 * @see <a href="https://iiif.io/api/image/3.0/">IIIF Image API</a>
 */
public interface IiifImageAdapter {

	/**
	 * 
	 * @param method
	 * @param headers
	 * @param iiifImageParameters
	 * @return
	 */
	IiifImageResponse requestImage(IiifImageParameters iiifImageParameters);

	/**
	 * 
	 * @param iiifImageParameters
	 * @return
	 */

	// IiifImageResponse requestInformation(Enumeration<String> headers, String pathToFile);
	//
	// IiifImageResponse requestOptions(Enumeration<String> headers, String pathToFile);

}