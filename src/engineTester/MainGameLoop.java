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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
//import objConverter.ModelData;
//import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		// Setting up Entity to render in renderer
		/* This is how to add an object with the new parser
		 * 
		ModelData data = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		*/
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(
				loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
				new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0; i<500; i++) {
			entities.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 800 - 400,
					0, random.nextFloat() * -600), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0,
					random.nextFloat() * -600), 0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0,
					random.nextFloat() * -600), 0, 0, 0, 0.6f));
		}
		
		// Setting up lights(sun) with positioning and camera
		Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		
		// Creating renderer then rendering
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			// Game Logic
			camera.move();
			
			// Rendering
			for(Entity obj:entities) {
				renderer.processEntity(obj);
			}
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();	
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
