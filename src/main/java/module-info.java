import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzerInstanceProvider;
import se.uu.ub.cora.binary.iiif.IiifInstanceProvider;

/**
 * The binary module provides interfaces and classes to use a binaries in a Cora based system.
 */
module se.uu.ub.cora.binary {
	requires se.uu.ub.cora.initialize;

	uses ContentAnalyzerInstanceProvider;
	uses IiifInstanceProvider;

	exports se.uu.ub.cora.binary.contentanalyzer;
	exports se.uu.ub.cora.binary.iiif;
	exports se.uu.ub.cora.binary;

}