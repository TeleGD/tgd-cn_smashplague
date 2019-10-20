import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
import java.awt.*;

public final class Main {

	public static final void main (String [] arguments) throws SlickException {
		String title = "Sans titre";
		int width = 1280;
		int height = 720;
		boolean fullscreen = false;
		String request = "Voulez-vous jouer en plein écran ?";
		String [] options = {
			"Oui",
			"Non"
		};
		int returnValue = JOptionPane.showOptionDialog (
			null,
			request,
			title,
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options [0]
		);
		if (returnValue == -1) {
			return;
		}
		if (returnValue == 0) {
			DisplayMode display = GraphicsEnvironment.getLocalGraphicsEnvironment ().getDefaultScreenDevice ().getDisplayMode ();
			width = display.getWidth ();
			height = display.getHeight ();
			fullscreen = true;
		}
		StateBasedGame game = new StateBasedGame (title) {

			@Override
			public void initStatesList (GameContainer container) {
				this.addState (new pages.Welcome (0));
				this.addState (new pages.Choice (1));
				this.addState (new pages.Pause (2));
				this.addState (new plague.World (3));
				this.addState (new pages.NameChoice (4));
				this.addState (new plague.SkillPage (5));
			}

		};
		AppGameContainer container = new AppGameContainer (game, width, height, fullscreen);
		container.setTargetFrameRate (60);
		container.setVSync (true);
		container.setShowFPS (false);
		container.start ();
	}

}
