import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzerInstanceProvider;
import se.uu.ub.cora.binary.iiif.IiifImageInstanceProvider;

/**
 * The fedora module provides interfaces and classes to use a Fedora Commons System in a Cora based
 * system.
 */
module se.uu.ub.cora.binary {
	requires se.uu.ub.cora.initialize;

	uses ContentAnalyzerInstanceProvider;
	uses IiifImageInstanceProvider;

	exports se.uu.ub.cora.binary.contentanalyzer;
	exports se.uu.ub.cora.binary.iiif;
	exports se.uu.ub.cora.binary;

}