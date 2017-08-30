package poe.mvc2;

import java.util.Observable;

import javax.swing.JButton;

public class View extends Observable {

	{
		JButton button = new JButton();
		button.addActionListener(new Controller());
	}

}
