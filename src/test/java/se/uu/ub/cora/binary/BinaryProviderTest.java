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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzer;
import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzerInstanceProvider;
import se.uu.ub.cora.binary.contentanalyzer.ContentAnalyzerInstanceProviderSpy;
import se.uu.ub.cora.binary.iiif.IiifImageAdapter;
import se.uu.ub.cora.binary.iiif.IiifImageInstanceProvider;
import se.uu.ub.cora.binary.iiif.spy.IiifImageInstanceProviderSpy;
import se.uu.ub.cora.initialize.ModuleInitializer;
import se.uu.ub.cora.initialize.ModuleInitializerImp;
import se.uu.ub.cora.initialize.spies.ModuleInitializerSpy;
import se.uu.ub.cora.logger.LoggerFactory;
import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.logger.spies.LoggerFactorySpy;

public class BinaryProviderTest {
	private LoggerFactory loggerFactory = new LoggerFactorySpy();
	private ModuleInitializerSpy moduleInitializerSpy;
	private ContentAnalyzerInstanceProviderSpy contentAnalyzerInstanceProvider;
	private IiifImageInstanceProviderSpy iiifImageInstanceProvider;

	@BeforeMethod
	public void beforeMethod() {
		LoggerProvider.setLoggerFactory(loggerFactory);
		BinaryProvider.onlyForTestSetContentAnalyzerInstanceProvider(null);
		BinaryProvider.onlyForTestSetIiifImageAdapterInstanceProvider(null);
	}

	private void setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		contentAnalyzerInstanceProvider = new ContentAnalyzerInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder", () -> contentAnalyzerInstanceProvider);
		BinaryProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	private void setupModuleInstanceProviderToReturnIiifImageInstanceProvider() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		iiifImageInstanceProvider = new IiifImageInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder", () -> iiifImageInstanceProvider);
		BinaryProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<BinaryProvider> constructor = BinaryProvider.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<BinaryProvider> constructor = BinaryProvider.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testDefaultInitializerIsModuleInitalizer() throws Exception {
		ModuleInitializer initializer = BinaryProvider.onlyForTestGetModuleInitializer();
		assertNotNull(initializer);
		assertTrue(initializer instanceof ModuleInitializerImp);
	}

	@Test
	public void testGetContentAnalyzerIsSynchronized_toPreventProblemsWithFindingImplementations()
			throws Exception {
		Method getContentAnalyzer = BinaryProvider.class.getMethod("getContentAnalyzer");
		assertTrue(Modifier.isSynchronized(getContentAnalyzer.getModifiers()));
	}

	@Test
	public void testGetContentAnalyzerUsesModuleInitializerToGetFactory() throws Exception {
		setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy();

		ContentAnalyzer contentAnalyzer = BinaryProvider.getContentAnalyzer();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				ContentAnalyzerInstanceProvider.class);
		contentAnalyzerInstanceProvider.MCR.assertReturn("getContentAnalyzer", 0, contentAnalyzer);
	}

	@Test
	public void testOnlyForTestSetContentAnalyzerInstanceProvider() throws Exception {
		ContentAnalyzerInstanceProviderSpy instanceProviderSpy2 = new ContentAnalyzerInstanceProviderSpy();
		BinaryProvider.onlyForTestSetContentAnalyzerInstanceProvider(instanceProviderSpy2);

		ContentAnalyzer contentAnalyzer = BinaryProvider.getContentAnalyzer();

		instanceProviderSpy2.MCR.assertReturn("getContentAnalyzer", 0, contentAnalyzer);
	}

	@Test
	public void testMultipleCallsToGetContentAnalyzerOnlyLoadsImplementationsOnce()
			throws Exception {
		setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy();

		BinaryProvider.getContentAnalyzer();
		BinaryProvider.getContentAnalyzer();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}

	@Test
	public void testGetIiifImageAdapterUsesModuleInitializerToGetFactory() throws Exception {
		setupModuleInstanceProviderToReturnIiifImageInstanceProvider();

		IiifImageAdapter iiifImageAdapter = BinaryProvider.getIiifImageAdapter();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				IiifImageInstanceProvider.class);
		iiifImageInstanceProvider.MCR.assertReturn("getIiifImageAdapter", 0, iiifImageAdapter);
	}

	@Test
	public void testOnlyForTestSetIiifImageAdapterInstanceProvider() throws Exception {
		IiifImageInstanceProviderSpy iifImageInstanceProvider = new IiifImageInstanceProviderSpy();
		BinaryProvider.onlyForTestSetIiifImageAdapterInstanceProvider(iifImageInstanceProvider);

		IiifImageAdapter iiifImageAdapter = BinaryProvider.getIiifImageAdapter();

		iifImageInstanceProvider.MCR.assertReturn("getIiifImageAdapter", 0, iiifImageAdapter);
	}

	@Test
	public void testMultipleCallsToGetIiifImageAdapterOnlyLoadsImplementationsOnce()
			throws Exception {
		setupModuleInstanceProviderToReturnIiifImageInstanceProvider();

		BinaryProvider.getIiifImageAdapter();
		BinaryProvider.getIiifImageAdapter();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}
}
