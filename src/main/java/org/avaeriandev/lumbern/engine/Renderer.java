package org.avaeriandev.lumbern.engine;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL33.*;

public class Renderer {

    // Hardcoded for the sake of testing
    private final static float[] vertices = {
             0.0f,  0.5f,
             0.5f, -0.5f,
            -0.5f, -0.5f
    };

    public void init() {

        int vbo = glGenBuffers();

        // Select vbo as active object
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        // Upload data
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

    }

    private String loadShaderSource(File file) throws IOException {
        return loadShaderSource(new FileInputStream(file));
    }
    private String loadShaderSource(InputStream input) {

        String source;
        try {
            source = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return source;
    }

    public void loadShaders() {

        // Load
        int vertexShader = loadVertexShader();
        int fragmentShader = loadFragmentShader();

        // Combine shaders
        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);

        // Not necessary, but here for good practice
        // Specify which color is written to which framebuffer
        // TODO: learn more about this
        glBindFragDataLocation(shaderProgram, 0, "outColor");

        // Link and use program
        glLinkProgram(shaderProgram);
        glUseProgram(shaderProgram);

    }

    private int loadVertexShader() {

        // Create shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);

        // Load shader
        String vertexSource = loadShaderSource(this.getClass().getResourceAsStream("shaders/vertex_shader.glsl"));
        glShaderSource(vertexShader, vertexSource);

        // Compile shader
        glCompileShader(vertexShader);

        // Verify that shader loaded properly
        IntBuffer status = BufferUtils.createIntBuffer(1);
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, status);

        if(status.get() == GL_TRUE) {
            System.out.println("Vertex shader loaded successfully!");
        } else {
            String log = glGetShaderInfoLog(vertexShader);
            System.err.println(log);
            throw new RuntimeException();
        }

        return vertexShader;
    }

    private int loadFragmentShader() {

        // Create shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        // Load shader
        String fragmentSource = loadShaderSource(this.getClass().getResourceAsStream("shaders/fragment_shader.glsl"));
        glShaderSource(fragmentShader, fragmentSource);

        // Compile shader
        glCompileShader(fragmentShader);

        // Verify that shader loaded properly
        IntBuffer status = BufferUtils.createIntBuffer(1);
        glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, status);

        if(status.get() == GL_TRUE) {
            System.out.println("Vertex shader loaded successfully!");
        } else {
            String log = glGetShaderInfoLog(fragmentShader);
            System.err.println(log);
            throw new RuntimeException();
        }

        return fragmentShader;
    }

    public void render() {

    }
}