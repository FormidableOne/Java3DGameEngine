package engineTester;
/**
 * 	Name: Ken Mascarenas
 * 	Web Site: http://cryosoftdesigns.com
 * 	Email: admin@cryosoftdesigns.com
 * 	Date: 23 December 2014
 * 	Project: Java 3D Game Engine
 * 	Version: 0.0.1a
 * 	Credit: Huge thanks to the following people who have helped
 * 			contribute to this project in one way or another:
 * 			Zack - RealTutsGML @ YouTube
 * 			ThinMatrix 
 * 				- ThinMatrix @ Twitter
 * 				- ThinMatrix @ YouTube
 */
import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		// Setting up Entity to render in renderer
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("dragonTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(150);
		texture.setReflectivity(1);
		Entity entity = new Entity(staticModel, new Vector3f(0,-5,-30),0,0,0,1);
		
		// Setting up lights(sun) with positioning and camera
		Light light = new Light(new Vector3f(0,0,-25), new Vector3f(1,1,1));
		Camera camera = new Camera();
		
		// Creating renderer then rendering
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			
			// Game Logic
			camera.move();
			entity.increaseRotation(0, 0.65f, 0);
			
			// Rendering
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();	
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
