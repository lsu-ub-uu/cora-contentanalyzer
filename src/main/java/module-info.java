import se.uu.ub.cora.contentanalyzer.ContentAnalyzerInstanceProvider;

/**
 * The fedora module provides interfaces and classes to use a Fedora Commons System in a Cora based
 * system.
 */
module se.uu.ub.cora.contentanalyzer {
	requires se.uu.ub.cora.initialize;

	uses ContentAnalyzerInstanceProvider;

	exports se.uu.ub.cora.contentanalyzer;

}