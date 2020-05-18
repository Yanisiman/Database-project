package gui;

import businessLogic.BusinessFacade;

public class ApplicationLauncher {
	public static void main(String[] args) {
		
		/*ConnectDatabaseGUI connectDatabaseGUI = new ConnectDatabaseGUI();
		connectDatabaseGUI.setVisible(true);*/
		
		BusinessFacade bl = new BusinessFacade("DBDI05", "DBDI05");
		LoginGUI loginGUI = new LoginGUI(bl);
		loginGUI.setVisible(true);
		
	}
}
