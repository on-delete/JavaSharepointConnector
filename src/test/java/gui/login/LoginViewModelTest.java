package gui.login;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javafx.concurrent.Worker;

import org.junit.Before;
import org.junit.Test;

import service.SharepointService;

public class LoginViewModelTest {

	private LoginViewModel loginViewModel;
	private SharepointService sharepointService;
	private LoginService loginService;
	
	@Before
	public void setup(){
		sharepointService = mock(SharepointService.class);
		loginService = mock(LoginService.class);
		
		loginViewModel = new LoginViewModel(sharepointService);
	}
	
	@Test
	public void testInitSharepointService(){
		assertNull(loginViewModel.getUserCredentials());
		
		loginViewModel.addressProperty().set("http://test.de");
		loginViewModel.usernameProperty().set("Andre");
		loginViewModel.passwordProperty().set("passwort");
		
		loginViewModel.initSharepointService();
		
		assertNotNull(loginViewModel.getUserCredentials());
		assertTrue(loginViewModel.getUserCredentials().getUsername().equals("Andre"));
		assertTrue(loginViewModel.getUserCredentials().getPassword().equals("passwort"));
		
		verify(sharepointService).initialize("http://test.de", loginViewModel.getUserCredentials());
	}
	
	@Test
	public void testInitLoginService(){
		assertNull(loginViewModel.loginWorkerProperty().get());
		
		loginViewModel.initLoginService();
		
		assertNotNull(loginViewModel.loginWorkerProperty().get());
		assertTrue(loginViewModel.loginWorkerProperty().get().stateProperty().get()==Worker.State.READY);
	}
	
	@Test
	public void testLogin() {
		loginViewModel.loginWorkerProperty().set(loginService);
		
		loginViewModel.login();
		
		verify(loginService).start();
	}
}
