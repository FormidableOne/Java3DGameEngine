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
import entities.Camera;
import entities.Entity;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
		
		// OpenGL expects vertices to be defined counter-clockwise by default
		float[] vertices = {			
				-0.5f,0.5f,-0.5f,-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,0.5f,-0.5f,0.5f
				
		};
		float[] textureCoords = {
				
				0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,			
				0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,
				0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0

		};
		int[] indices = {
				0,1,3,3,1,2,4,5,7,7,5,6,
				8,9,11,11,9,10,12,13,15,15,13,14,	
				16,17,19,19,17,18,20,21,23,23,21,22

		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("image")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-5),0,0,0,1);
		
		Camera camera = new Camera();
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(1,1,0);
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
