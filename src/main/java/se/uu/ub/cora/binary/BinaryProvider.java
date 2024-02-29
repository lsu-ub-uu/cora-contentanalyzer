/*
 * Copyright 2023, 2024 Uppsala University Library
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
package se.uu.ub.cora.binary;

import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzer;
import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzerInstanceProvider;
import se.uu.ub.cora.binary.iiif.IiifAdapter;
import se.uu.ub.cora.binary.iiif.IiifInstanceProvider;
import se.uu.ub.cora.initialize.AbstractProvider;
import se.uu.ub.cora.initialize.SelectOrder;

/**
 * ContentAnalyzerProvider is used to provide access to content analyzer for binaries.
 * </p>
 * Implementing {@link ContentAnalyzerInstanceProvider}s are found using javas module system, and
 * the one with the higest {@link SelectOrder} is used to provide access to content analyzing.
 */
public class BinaryProvider extends AbstractProvider {
	private static ContentAnalyzerInstanceProvider analyzerInstanceProvider;
	private static IiifInstanceProvider imageInstanceProvider;

	private BinaryProvider() {
		// prevent call to constructor
		throw new UnsupportedOperationException();
	}

	/**
	 * getContentAnalyzer returns a ContentAnalyzer that can be used by anything that needs access
	 * to Content Analyzer.
	 * </p>
	 * Code using the returned {@link ContentAnalyzer} instance MUST consider the returned instance
	 * as NOT thread safe.
	 * 
	 * @return A ContentAnalyzer that gives access to check the mime type of a file
	 */
	public static synchronized ContentAnalyzer getContentAnalyzer() {
		locateAndChooseInstanceProvider();
		return analyzerInstanceProvider.getContentAnalyzer();
	}

	private static void locateAndChooseInstanceProvider() {
		if (analyzerInstanceProvider == null) {
			analyzerInstanceProvider = moduleInitializer
					.loadOneImplementationBySelectOrder(ContentAnalyzerInstanceProvider.class);
		}
	}

	/**
	 * onlyForTestSetContentAnalyzerInstanceProvider sets a ContentAnalyzerInstanceProvider that
	 * will be used to return instances for the {@link #getContentAnalyzer()} method. This
	 * possibility to set a ContentAnalyzerInstanceProvider is provided to enable testing of getting
	 * a Content Analyzer in other classes and is not intented to be used in production.
	 * <p>
	 * The ContentAnalyzerInstanceProvider to use in production should be provided through an
	 * implementation of {@link ContentAnalyzerInstanceProvider} in a seperate java module.
	 * 
	 * @param instanceProvider
	 *            A ContentAnalyzerInstanceProvider to use to return ContentAnalyzer instances for
	 *            testing
	 */
	public static void onlyForTestSetContentAnalyzerInstanceProvider(
			ContentAnalyzerInstanceProvider instanceProvider) {
		BinaryProvider.analyzerInstanceProvider = instanceProvider;
	}

	/**
	 * getIiifImageAdapter returns a IiifAdapter that can be used by anything that needs access to
	 * Iiif Image API.
	 * </p>
	 * Code using the returned {@link IiifAdapter} instance MUST consider the returned instance as
	 * NOT thread safe.
	 * 
	 * @return A IiifImageAdapter that gives access to IIIF Image Api
	 */
	public static IiifAdapter getIiifAdapter() {
		if (imageInstanceProvider == null) {
			imageInstanceProvider = moduleInitializer
					.loadOneImplementationBySelectOrder(IiifInstanceProvider.class);
		}
		return imageInstanceProvider.getIiifAdapter();
	}

	/**
	 * onlyForTestSetIiifImageAdapterInstanceProvider sets a IiifImageInstanceProvider that will be
	 * used to return instances for the {@link #getIiifAdapter()} method. This possibility to set a
	 * IiifImageInstanceProvider is provided to enable testing of getting a IiifImageAdapter in
	 * other classes and is not intented to be used in production.
	 * <p>
	 * The IiifImageInstanceProvider to use in production should be provided through an
	 * implementation of {@link IiifInstanceProvider} in a seperate java module.
	 * 
	 * @param instanceProvider
	 *            A IiifImageInstanceProvider to use to return IiifImageAdapter instances for
	 *            testing
	 */
	public static void onlyForTestSetIiifImageAdapterInstanceProvider(
			IiifInstanceProvider imageInstanceProvider) {
		BinaryProvider.imageInstanceProvider = imageInstanceProvider;
	}

}
