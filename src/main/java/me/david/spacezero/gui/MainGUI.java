package me.david.spacezero.gui;

import org.joml.Vector2i;
import org.liquidengine.legui.DefaultInitializer;
import org.liquidengine.legui.animation.Animator;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.system.context.Context;
import org.liquidengine.legui.system.layout.LayoutManager;
import org.liquidengine.legui.system.renderer.Renderer;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class MainGUI {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private volatile boolean running = false;
    private Context context;
    private Frame frame = new Frame(WIDTH, HEIGHT);

    private FileExplorer fileExplorer = new FileExplorer(frame);

    public MainGUI() {
        System.setProperty("joml.nounsafe", Boolean.TRUE.toString());
        System.setProperty("java.awt.headless", Boolean.TRUE.toString());
        if (!glfwInit()) {
            throw new RuntimeException("Can't initialize GLFW");
        }
        long window = glfwCreateWindow(WIDTH, HEIGHT, "Example", NULL, NULL);
        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSwapInterval(0);

        DefaultInitializer initializer = new DefaultInitializer(window, frame);

        GLFWKeyCallbackI glfwKeyCallbackI = (w1, key, code, action, mods) -> running = !(key == GLFW_KEY_ESCAPE && action != GLFW_RELEASE);
        GLFWWindowCloseCallbackI glfwWindowCloseCallbackI = w -> running = false;

        initializer.getCallbackKeeper().getChainKeyCallback().add(glfwKeyCallbackI);
        initializer.getCallbackKeeper().getChainWindowCloseCallback().add(glfwWindowCloseCallbackI);

        running = true;

        Renderer renderer = initializer.getRenderer();
        Animator animator = Animator.getInstance();
        renderer.initialize();

        context = initializer.getContext();

        while (running) {
            context.updateGlfwWindow();
            Vector2i windowSize = context.getFramebufferSize();

            glClearColor(1, 1, 1, 1);
            glViewport(0, 0, windowSize.x, windowSize.y);
            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            fileExplorer.render(windowSize.x, windowSize.y);
            renderer.render(frame, context);

            glfwPollEvents();
            glfwSwapBuffers(window);

            animator.runAnimations();

            initializer.getSystemEventProcessor().processEvents(frame, context);
            initializer.getGuiEventProcessor().processEvents();
            LayoutManager.getInstance().layout(frame);
        }

        renderer.destroy();
        glfwDestroyWindow(window);
        glfwTerminate();
        System.exit(0);
    }

}
