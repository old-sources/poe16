package poe.mvc1;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

	{
		new View().addObserver(this);
	}
	public void update(Observable o, Object arg) {
		switch ((String) arg) {
		case "event1":
			break;
		case "event2":
			break;
		default:
			break;
		}
	}

}
