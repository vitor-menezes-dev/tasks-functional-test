package dev.menezes.vitor.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	private WebDriver acessarAplicacao() throws MalformedURLException {
		
		ChromeOptions cap = new ChromeOptions(); 
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
		                  UnexpectedAlertBehaviour.IGNORE);
		
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.109:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.1.109:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	private void preencherTelaDeCadastroESalvar(WebDriver driver, String task, String dueDate) {
		driver.findElement(By.id("addTodo")).click();

		driver.findElement(By.id("task")).sendKeys(task);

		driver.findElement(By.id("dueDate")).sendKeys(dueDate);

		driver.findElement(By.id("saveButton")).click();

	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			preencherTelaDeCadastroESalvar(driver, "Teste via Selenium", "01/01/2030");

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Success!", mensagem);
		} finally {
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			preencherTelaDeCadastroESalvar(driver, "", "01/01/2000");

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the task description", mensagem);

		} finally {
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			preencherTelaDeCadastroESalvar(driver, "Teste sem Data", "");

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Fill the due date", mensagem);

		} finally {
			driver.quit();
		}

	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			preencherTelaDeCadastroESalvar(driver, "Teste com Data Passada", "01/01/2000");

			String mensagem = driver.findElement(By.id("message")).getText();

			Assert.assertEquals("Due date must not be in past", mensagem);

		} finally {
			driver.quit();
		}

	}

}
