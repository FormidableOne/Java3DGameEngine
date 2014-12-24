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
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		// OpenGL expects vertices to be defined counter-clockwise by default
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1);
		
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0,1,0);
			camera.move();
			// Game Logic
			renderer.prepare();
			// Rendering
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
