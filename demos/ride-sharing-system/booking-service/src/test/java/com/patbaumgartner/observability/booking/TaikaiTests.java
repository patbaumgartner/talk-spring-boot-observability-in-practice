package com.patbaumgartner.observability.booking;

import com.enofex.taikai.Taikai;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.List;

import static com.tngtech.archunit.core.domain.JavaModifier.FINAL;
import static com.tngtech.archunit.core.domain.JavaModifier.PRIVATE;

class TaikaiTests {

	@Test
	void shouldFulfillConstraints() {
		Taikai.builder()
			.namespace("com.patbaumgartner.observability.booking")
			.java(java -> java.noUsageOfDeprecatedAPIs()
				.methodsShouldNotDeclareGenericExceptions()
				.utilityClassesShouldBeFinalAndHavePrivateConstructor()
				.imports(imports -> imports.shouldHaveNoCycles()
					.shouldNotImport("..shaded..")
					.shouldNotImport("org.junit.."))
				.naming(naming -> naming.classesShouldNotMatch(".*Impl")
					.methodsShouldNotMatch("^(foo$|bar$).*")
					.fieldsShouldNotMatch(".*(List|Set|Map)$")
					// Example for positive matching
					// .fieldsShouldMatch("com.enofex.taikai.Matcher", "matcher")
					.constantsShouldFollowConventions()
					.interfacesShouldNotHavePrefixI()))
			.logging(logging -> logging.loggersShouldFollowConventions(Logger.class, "LOGGER", List.of(PRIVATE, FINAL)))
			.test(test -> test.junit(
					junit -> junit.classesShouldNotBeAnnotatedWithDisabled().methodsShouldNotBeAnnotatedWithDisabled()))
			.spring(spring -> spring.noAutowiredFields()
				.boot(boot -> boot.springBootApplicationShouldBeIn("com.patbaumgartner.observability.booking"))
				.configurations(configuration -> configuration.namesShouldEndWithConfiguration())
				.controllers(controllers -> controllers.shouldBeAnnotatedWithRestController()
					.namesShouldEndWithController()
					.shouldNotDependOnOtherControllers()
					.shouldBePackagePrivate())
				.services(services -> services.shouldBeAnnotatedWithService()
					.shouldNotDependOnControllers()
					.namesShouldEndWithService())
				.repositories(repositories -> repositories
					// Not needed for Spring Data JPA
					// .shouldBeAnnotatedWithRepository()
					.shouldNotDependOnServices()
					.namesShouldEndWithRepository()))
			// Add custom ArchUnit rule here
			// .addRule(TaikaiRule.of(...))
			.build()
			.check();
	}

}
