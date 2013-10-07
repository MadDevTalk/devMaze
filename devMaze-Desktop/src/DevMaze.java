import dev.talk.maze.GameFrame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DevMaze {
   public static void main(String[] args) {
      LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
      cfg.width = 1243;
      cfg.height = 768;
      
      new LwjglApplication(new GameFrame(), cfg);
   }
}