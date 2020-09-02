package dev.menezes.vitor.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	private WebDriver acessarAplicacao() {
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
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
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		try {
		preencherTelaDeCadastroESalvar(driver,"Teste via Selenium","01/01/2030");
		
		String mensagem = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Success!", mensagem);
		} finally {
			driver.quit();	
		}
		
		
	}


	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();
		try {
		preencherTelaDeCadastroESalvar(driver,"","01/01/2000");
		
		String mensagem = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Fill the task description", mensagem);
		
		} finally {
			driver.quit();	
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();
		try {
		preencherTelaDeCadastroESalvar(driver,"Teste sem Data","");
		
		String mensagem = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Fill the due date", mensagem);
		
		} finally {
			driver.quit();	
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();
		try {
		preencherTelaDeCadastroESalvar(driver,"Teste com Data Passada","01/01/2000");
		
		String mensagem = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Due date must not be in past", mensagem);
		
		} finally {
			driver.quit();	
		}
		
	}
	

}
