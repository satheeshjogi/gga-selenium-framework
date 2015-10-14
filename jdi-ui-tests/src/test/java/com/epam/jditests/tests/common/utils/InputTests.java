package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getJSExecutor;

public class InputTests {

	private Preconditions _onPage;
	private IInputable _i;
	private ITextField _textFileld = null;
	private String _inputText;

	public InputTests(String inputText, Preconditions onPage, IInputable i) {
		this._inputText = inputText;
		this._onPage = onPage;
		this._i = i;
	}

	private ITextField input() {
		if (_textFileld == null) {
			_textFileld = _i.getInputElement();
		}
		return _textFileld;
	}

	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}

	@AfterMethod
	public void after() {
		_onPage.open();
	}

	@Test
	public void inputTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"\"", input().getWebElement());
		input().input(_inputText);
		input().getWebElement().sendKeys(Keys.ESCAPE);
		final String resText = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement())
				.toString();
		Assert.assertEquals(resText, _inputText);
	}

	@Test
	public void sendKeyTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"\"", input().getWebElement());
		input().input(_inputText);
		final String resText = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement())
				.toString();
		Assert.assertEquals(resText, _inputText);
	}

	@Test
	public void newInputTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"garbageString\"", input().getWebElement());
		input().newInput(_inputText);
		final String resText = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement())
				.toString();
		Assert.assertEquals(_inputText, resText);
	}

	@Test
	public void clearTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"garbageString\"", input().getWebElement());
		input().clear();
		final String resText = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement())
				.toString();
		Assert.assertTrue(resText.length() == 0);
	}

	@Test
	public void multiKeyTest() throws Exception {
		getJSExecutor().executeScript("arguments[0].value=\"\"", input().getWebElement());
		for (int i = 0; i < _inputText.length(); i++) {
			input().sendKeys(Character.toString(_inputText.charAt(i)));
		}
		final String resText = getJSExecutor().executeScript("return arguments[0].value", input().getWebElement())
				.toString();
		Assert.assertEquals(_inputText, resText);
	}

	@Test
	public void focusTest() throws Exception {
		/*
		 * 1. Set new attribute to tested element 2. Set focus on element by JDI
		 * method 3. Get attribute value on focused element by WebDriver method
		 */
		String id = "testValue";
		getJSExecutor().executeScript("arguments[0].setAttribute(\"test\",\"" + id + "\");", input().getWebElement());
		input().focus();
		final String res = getDriver().switchTo().activeElement().getAttribute("test");
		Assert.assertEquals(id, res);
	}
}