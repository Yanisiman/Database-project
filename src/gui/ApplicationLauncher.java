package gui;

import businessLogic.BusinessFacade;

public class ApplicationLauncher {
	public static void main(String[] args) {
		
		/*ConnectDatabaseGUI connectDatabaseGUI = new ConnectDatabaseGUI();
		connectDatabaseGUI.setVisible(true);*/
		
		BusinessFacade bl = new BusinessFacade("DBDI05", "zwV4Y9Mk");
		LoginGUI loginGUI = new LoginGUI(bl);
		loginGUI.setVisible(true);
		
	}
}
