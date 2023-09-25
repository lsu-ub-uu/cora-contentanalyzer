package se.uu.ub.cora.contentanalyzer;
/*
 * Copyright 2022, 2023 Uppsala University Library
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

import se.uu.ub.cora.initialize.AbstractProvider;
import se.uu.ub.cora.initialize.SelectOrder;

/**
 * ContentAnalyzerProvider is used to provide access to archive for resources.
 * </p>
 * Implementing {@link ContentAnalyzerInstanceProvider}s are found using javas module system, and
 * the one with the higest {@link SelectOrder} is used to provide access to resource archive.
 */
public class ContentAnalyzerProvider extends AbstractProvider {
	private static ContentAnalyzerInstanceProvider instanceProvider;

	/**
	 * getContentAnalyzer returns a ContentAnalyzer that can be used by anything that needs access
	 * to resources.
	 * </p>
	 * Code using the returned {@link ContentAnalyzer} instance MUST consider the returned instance
	 * as NOT thread safe.
	 * 
	 * @return A ContentAnalyzer that gives access to storage for resource
	 */
	public static synchronized ContentAnalyzer getContentAnalyzer() {
		locateAndChooseInstanceProvider();
		return instanceProvider.getContentAnalyzer();
	}

	private static void locateAndChooseInstanceProvider() {
		if (instanceProvider == null) {
			instanceProvider = moduleInitializer
					.loadOneImplementationBySelectOrder(ContentAnalyzerInstanceProvider.class);
		}
	}

	/**
	 * onlyForTestSetInstanceProvider sets a ContentAnalyzerInstanceProvider that will be used to
	 * return instances for the {@link #getContentAnalyzer()} method. This possibility to set a
	 * DataRecordFactory is provided to enable testing of getting a record storage in other classes
	 * and is not intented to be used in production.
	 * <p>
	 * The ContentAnalyzerInstanceProvider to use in production should be provided through an
	 * implementation of {@link ContentAnalyzerInstanceProvider} in a seperate java module.
	 * 
	 * @param instanceProvider
	 *            A ContentAnalyzerInstanceProvider to use to return ContentAnalyzer instances for
	 *            testing
	 */
	public static void onlyForTestSetInstanceProvider(
			ContentAnalyzerInstanceProvider instanceProvider) {
		ContentAnalyzerProvider.instanceProvider = instanceProvider;
	}
}
