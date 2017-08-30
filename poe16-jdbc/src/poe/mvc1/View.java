package poe.mvc1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;

public class View extends Observable {

	{
		JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				View.this.notifyObservers("event1");;
			}
		});
	}

}
