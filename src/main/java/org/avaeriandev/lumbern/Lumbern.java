package org.avaeriandev.lumbern;

import org.avaeriandev.lumbern.engine.Window;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Lumbern {

    private static boolean isRunning;

    static {
        isRunning = false;
    }

    public static void run() {

        if(isRunning) throw new RuntimeException("Game is already running!");
        isRunning = true;

        Window window = new Window();
        while(window.isRunning()) {
            window.handle();
        }

        System.out.println("Closing window");
        // window.terminate();

        // TODO: move this to Window class
        // This will require some rewriting and code clean up
        glfwTerminate();

    }

}
