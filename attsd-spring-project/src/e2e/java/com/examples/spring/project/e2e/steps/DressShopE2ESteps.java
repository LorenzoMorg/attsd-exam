package com.examples.spring.project.e2e.steps;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import com.examples.spring.project.repositories.DressShopRepository;
import com.examples.spring.project.model.DressShop;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "spring.main.allow-bean-definition-overriding=true")
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class DressShopE2ESteps {

	@Autowired
	DressShopRepository dressShopRepository;

	@LocalServerPort
	private int port;

	private static String baseUrl = "http://localhost:";

	private WebDriver driver;

	@Before
	public void setup() {
		driver = new ChromeDriver();
		dressShopRepository.deleteAll();
	}

	@After
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	@Given("^The database is empty$")
	public void the_database_is_empty() throws Throwable {
	dressShopRepository.deleteAll();
	}
	
	@When("^The User is on HomePage$")
	public void the_User_is_on_HomePage() throws Throwable {
		driver.get(baseUrl+port);
	}
	
	@Then("^The message \"([^\"]*)\" must be shown$")
	public void a_message_must_be_shown(String expectedMessage) throws Throwable {
		assertThat(driver.getPageSource()).contains(expectedMessage);
	}
	
	@Given("^Some dressShops are in the database$")
	public void someDressShopsAreInTheDatabase() throws Throwable {
	dressShopRepository.save(new DressShop(1L, "ds1", 20));
	dressShopRepository.save(new DressShop(1L, "ds2", 30));
	}
	
	@Then("^The table must show the dressShop with name \"([^\"]*)\" and target price \"([^\"]*)\"$")
	public void the_table_must_show_the_dressShop_with_name_and_target_price(String name, String targetPrice)
			throws Throwable {
		assertThat(driver.findElement(By.id("dressShop_table")).getText())
				.contains(name + " " + targetPrice + " Edit Delete");
	}
	

}