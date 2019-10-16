/*
 *	Author:      Kong Kong Marc Christian
 *	Date:        15 mars 2018
 */

package com.games.flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.system.libffi.ClosureError.*;
import org.omg.CosNaming.IstringHelper;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import com.games.flappy.graphics.Shader;
import com.games.flappy.input.Input;
import com.games.flappy.level.Level;
import com.games.flappy.math.Matrix4f;

public class Main implements Runnable {
		
	private int width = 1270;
	private int height = 720;
	private long window;
	private GLFWKeyCallback keyCallBack;

	private Level level;

	private Thread thread;
	private boolean running = false;

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			// TODO: handle it
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
		if (window == NULL) {
			// TODO: handle it
			return;
		}

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetKeyCallback(window, keyCallBack = new Input());

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GLContext.createFromCurrent();

		// glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGl:" + glGetString(GL_VERSION));
		Shader.loadAll();

		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f,
				1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniformli("tex", 1);

		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniformli("tex", 1);

		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniformli("tex", 1);

		level = new Level();
	}

	public void run() {
		init();

		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + "ups, " + frames + "fps");
				updates = 0;
				frames = 0;
			}

			if (glfwWindowShouldClose(window) == GL_TRUE) {
				running = false;
			}
		}
		glfwDestroyWindow(window);
		glfwTerminate();
		keyCallBack.release();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		int error = glGetError();
		if (error != GL_NO_ERROR) {
			System.out.println(error);
		}
		glfwSwapBuffers(window);

	}

	private void update() {
		glfwPollEvents();
		level.update();
		if (level.isGameOver()) {
			level = new Level();
		}

	}

	public static void jeu() {
		Main play = new Main();
		play.start();

	}
	
}