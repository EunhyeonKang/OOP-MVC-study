package main;

import db.DBConnection;
import singleton.Singleton;
import view.FirstView;

public class main {
	public static void main(String[] args) {
		DBConnection.initConnection();
		Singleton s = Singleton.getInstance();
		FirstView f = new FirstView();
	//	s.memCtrl.loginView();
	
	}

}
