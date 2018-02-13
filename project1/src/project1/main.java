package project1;

import com.sun.scenario.effect.Blend.Mode;

import project1.Model;
import project1.View;

public class main {

	public static void main(String[] args) {
		Model.init();
		View.render(Model.getGrid());
		System.out.println();
		Model.moveDown();
		View.render(Model.getGrid());
	}

}
