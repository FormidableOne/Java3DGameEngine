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
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import entities.Entity;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		// OpenGL expects vertices to be defined counter-clockwise by default
		float[] vertices = {
				-0.5f, 0.5f, 0,		// V0
				-0.5f, -0.5f, 0,	// V1
				0.5f, -0.5f, 0,		// V2
				0.5f, 0.5f, 0		// V3
		};
		
		int[] indices = {
				0, 1, 3,	// Top-left triangle (V0,V1,V3)
				3, 1, 2		// Bottom-right triangle (V3,V1,V2)
		};
		
		float[] textureCoords = {
				0, 0,	// V0
				0, 1,	// V1
				1, 1,	// V2
				1, 0	// V3
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,0),0,0,0,1);
		
		while(!Display.isCloseRequested()) {
			entity.increasePosition(0, 0, -0.002f);
			// Game Logic
			renderer.prepare();
			// Rendering
			shader.start();
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
