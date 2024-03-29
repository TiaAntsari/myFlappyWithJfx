/*
 *	Author:      Kong Kong Marc Christian
 *	Date:        15 mars 2018
 */

package com.games.flappy.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {
	public static boolean[] keys = new boolean[65536];

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE;

	}

	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}

}
