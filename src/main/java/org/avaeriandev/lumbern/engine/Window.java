package org.avaeriandev.lumbern.engine;

import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static boolean isCreated;
    private long window;

    static {
        isCreated = false;
    }

    public Window() {
        if(isCreated) throw new UnsupportedOperationException("Multiple game windows are not supported.");
        isCreated = true;
        init();
    }

    private void init() {

        // Initialize library
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) throw new RuntimeException("GLFW unable to initialize");

        // Configure window
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUSED, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        // Create window
        int width = 1920;
        int height = 1080;
        long window = glfwCreateWindow(width, height, "Lumbern", NULL, NULL);
        this.window = window;

        // Prepare with OpenGL
        glfwMakeContextCurrent(window);
    }

    public boolean isRunning() {
        return !glfwWindowShouldClose(window);
    }

    // While window is running
    // Run for every frame
    public void handle() {
        glfwSwapBuffers(window);
        glfwPollEvents();
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            close();
        }
    }

    // TODO: consider glfwSetWindowCloseCallback() to close the window nicely
    // This would provide abstraction of all GLFW code away from other engine code
    public void close() {
        glfwSetWindowShouldClose(window, true);
        glfwDestroyWindow(window);

        /*
            pseudo-code

            mainProcess.onClose(() -> {
                glfwTerminate();
            });
         */
    }

}
