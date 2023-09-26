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
package se.uu.ub.cora.contentanalyzer;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.initialize.ModuleInitializer;
import se.uu.ub.cora.initialize.ModuleInitializerImp;
import se.uu.ub.cora.initialize.spies.ModuleInitializerSpy;
import se.uu.ub.cora.logger.LoggerFactory;
import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.logger.spies.LoggerFactorySpy;

public class ContentAnalyzerProviderTest {
	private LoggerFactory loggerFactory = new LoggerFactorySpy();
	private ModuleInitializerSpy moduleInitializerSpy;
	private ContentAnalyzerInstanceProviderSpy instanceProviderSpy;

	@BeforeMethod
	public void beforeMethod() {
		LoggerProvider.setLoggerFactory(loggerFactory);
		ContentAnalyzerProvider.onlyForTestSetContentAnalyzerInstanceProvider(null);
	}

	private void setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		instanceProviderSpy = new ContentAnalyzerInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder",
				((Supplier<ContentAnalyzerInstanceProvider>) () -> instanceProviderSpy));
		ContentAnalyzerProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<ContentAnalyzerProvider> constructor = ContentAnalyzerProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<ContentAnalyzerProvider> constructor = ContentAnalyzerProvider.class
				.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testDefaultInitializerIsModuleInitalizer() throws Exception {
		ModuleInitializer initializer = ContentAnalyzerProvider.onlyForTestGetModuleInitializer();
		assertNotNull(initializer);
		assertTrue(initializer instanceof ModuleInitializerImp);
	}

	@Test
	public void testGetContentAnalyzerIsSynchronized_toPreventProblemsWithFindingImplementations()
			throws Exception {
		Method getContentAnalyzer = ContentAnalyzerProvider.class.getMethod("getContentAnalyzer");
		assertTrue(Modifier.isSynchronized(getContentAnalyzer.getModifiers()));
	}

	@Test
	public void testGetContentAnalyzerUsesModuleInitializerToGetFactory() throws Exception {
		setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy();

		ContentAnalyzer ContentAnalyzer = ContentAnalyzerProvider.getContentAnalyzer();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				ContentAnalyzerInstanceProvider.class);
		instanceProviderSpy.MCR.assertReturn("getContentAnalyzer", 0, ContentAnalyzer);
	}

	@Test
	public void testOnlyForTestSetContentAnalyzerInstanceProvider() throws Exception {
		ContentAnalyzerInstanceProviderSpy instanceProviderSpy2 = new ContentAnalyzerInstanceProviderSpy();
		ContentAnalyzerProvider.onlyForTestSetContentAnalyzerInstanceProvider(instanceProviderSpy2);

		ContentAnalyzer ContentAnalyzer = ContentAnalyzerProvider.getContentAnalyzer();

		instanceProviderSpy2.MCR.assertReturn("getContentAnalyzer", 0, ContentAnalyzer);
	}

	@Test
	public void testMultipleCallsToGetContentAnalyzerOnlyLoadsImplementationsOnce()
			throws Exception {
		setupModuleInstanceProviderToReturnContentAnalyzerFactorySpy();

		ContentAnalyzerProvider.getContentAnalyzer();
		ContentAnalyzerProvider.getContentAnalyzer();
		ContentAnalyzerProvider.getContentAnalyzer();
		ContentAnalyzerProvider.getContentAnalyzer();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}
}
