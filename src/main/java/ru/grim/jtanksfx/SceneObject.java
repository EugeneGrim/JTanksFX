package ru.grim.jtanksfx;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface SceneObject {
  
  void update(double time);

  void render(GraphicsContext gc);

  Rectangle2D getBoundary();

  boolean intersects(SceneObject s);

  void setPosition(double x, double y);

}
