package cz.muni.muniGroup.cookbook.activities;

import cz.muni.muniGroup.cookbook.R;
import cz.muni.muniGroup.cookbook.entities.User;
import cz.muni.muniGroup.cookbook.exceptions.ConnectivityException;
import cz.muni.muniGroup.cookbook.exceptions.CookbookException;
import cz.muni.muniGroup.cookbook.managers.UserManager;
import cz.muni.muniGroup.cookbook.managers.UserManagerImpl;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//testRegistration(); //Only for test purpose
		//testLogin();  //Only for test purpose
		
		
		
	}

	/**
	 * Only for test purpose
	 * */
	@SuppressWarnings("unused")
	private void testLogin() {
		final UserManager userManager = new UserManagerImpl();
		final String email = "neco@test.cz";
		final String password = "tajneHeslo";
				
		Runnable loading = new Runnable(){
            public void run() {
				try {
					System.out.println("Výpoètové vlákno loginu nastartováno.");
					User user = userManager.loginUser(email, password);
					System.out.println("Uživatel byl prihlasen. ID: "+user.getId()+" NAME: "+user.getName());
				} catch (ConnectivityException e) {
					System.out.println(e.getMessage());
				} catch (CookbookException e) {
					System.out.println(e.getMessage());
				}
            }
		};
		Thread threadLoading = new Thread(null, loading, "loading");
        threadLoading.start();
	}
	
	/**
	 * Only for test purpose
	 * */
	@SuppressWarnings("unused")
	private void testRegistration() {
		final UserManager userManager = new UserManagerImpl();
		User user = new User();
		user.setEmail("neco@test.cz");
		user.setName("Honza");
		user.setPassword("tajneHeslo");
		
		final User userFinal = user;
		
		Runnable loading = new Runnable(){
            public void run() {
				try {
					System.out.println("Výpoètové vlákno nastartováno.");
					userManager.create(userFinal);
					System.out.println("Uživatel byl vytvoøen. ID: "+userFinal.getId());
				} catch (ConnectivityException e) {
					System.out.println(e.getMessage());
				} catch (CookbookException e) {
					System.out.println(e.getMessage());
				}
            }
		};
		Thread threadLoading = new Thread(null, loading, "loading");
        threadLoading.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
