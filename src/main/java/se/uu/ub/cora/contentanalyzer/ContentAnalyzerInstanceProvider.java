package se.uu.ub.cora.contentanalyzer;
/*
 * Copyright 2023 Uppsala University Library
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

import se.uu.ub.cora.initialize.SelectOrder;

/**
 * ContentAnalyzerInstanceProvider is used to provide Content Analyzers for binaries
 */
public interface ContentAnalyzerInstanceProvider extends SelectOrder {

	/**
	 * getContentAnalyzer should be implemented in such a way that it returns a
	 * {@link ContentAnalyzer} that can be used by anything that needs access to analyzing content.
	 * Multiple calls to getContentAnalyzer should return instances or the same instance, depending
	 * on the implementation. It must be possible to use the currently returned instance without
	 * considering if other calls has been made to this method.
	 * 
	 * @return A {@link ContentAnalyzer} that gives access to content analyzer for binaries
	 */

	ContentAnalyzer getContentAnalyzer();
}
