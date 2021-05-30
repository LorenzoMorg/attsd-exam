package com.examples.spring.project.e2e;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/e2e/resources/editing.feature")
public class DressShopEditWebPageE2E {
	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
}
