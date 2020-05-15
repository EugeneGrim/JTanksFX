package ru.grim.jtanksfx;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Tank implements SceneObject {
  private double positionX;
  private double positionY;
  private double velocityX;
  private double velocityY;
  private double width;
  private double height;
  
  private boolean verticalMoving;
  private boolean horizontalMoving;

  public Tank(double size) {
    positionX = 0;
    positionY = 0;
    velocityX = 0;
    velocityY = 0;
    width     = size;
    height    = size;
    
    verticalMoving   = false;
    horizontalMoving = false;
  }
  
  public void moveUp() {
    addVelocity(0, -50);
    verticalMoving = true;
    horizontalMoving = false;
  }
  
  public void moveDown() {
    addVelocity(0, 50);
    verticalMoving = true;
    horizontalMoving = false;
  }
  
  public void moveLeft() {
    addVelocity(-50, 0);
    verticalMoving = false;
    horizontalMoving = true;
  }
  
  public void moveRight() {
    addVelocity(50, 0);
    verticalMoving = false;
    horizontalMoving = true;
  }
  
  public void stop() {
    setVelocity(0, 0);
    verticalMoving   = false;
    horizontalMoving = false;
  }

  public boolean isVerticalMoving() {
    return verticalMoving;
  }

  public boolean isHorizontalMoving() {
    return horizontalMoving;
  }
  
  public void setSize(double size) {
    width  = size;
    height = size;
  }

  public void setPosition(double x, double y) {
    positionX = x;
    positionY = y;
  }

  private void setVelocity(double x, double y) {
    velocityX = x;
    velocityY = y;
  }

  private void addVelocity(double x, double y) {
    velocityX += x;
    velocityY += y;
  }

  public void update(double time) {
    positionX += velocityX * time;
    positionY += velocityY * time;
  }

  public void render(GraphicsContext gc) {
    System.out.println(positionX + ":" + positionY);
    gc.setFill(Color.GREEN);
    gc.setStroke(Color.BLUE);
    gc.setLineWidth(5);
    gc.strokeLine(positionX, positionY, positionX + width, positionY);
    gc.strokeLine(positionX + width, positionY, positionX + width, positionY + height);
    gc.strokeLine(positionX + width, positionY + height, positionX, positionY + height);
    gc.strokeLine(positionX, positionY + height, positionX, positionY);
  }

  public Rectangle2D getBoundary() {
    return new Rectangle2D(positionX, positionY, width, height);
  }

  public boolean intersects(SceneObject s) {
    return s.getBoundary().intersects(this.getBoundary());
  }

  public String toString() {
    return " Position: [" + positionX + "," + positionY + "]" + " Velocity: [" + velocityX + ","
        + velocityY + "]";
  }
}
