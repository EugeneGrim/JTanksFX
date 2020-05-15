package ru.grim.jtanksfx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class JTanksFX extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage theStage) {
    theStage.setTitle("JTanksFX");

    Queue<String> input = new LinkedList<>();
    
    GraphicsContext gc = addCanvas(theStage, input).getGraphicsContext2D();

    Tank tank = new Tank(50);
    tank.setPosition(200, 200);

    ArrayList<Sprite> moneybagList = addMoneyBags();

    LongValue lastNanoTime = new LongValue(System.nanoTime());

    new AnimationTimer() {
      public void handle(long currentNanoTime) {
        
        // calculate time since last update.
        double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
        lastNanoTime.value = currentNanoTime;

        // game logic
        handleUserInputs(tank, input, elapsedTime);

        // collision detection
        detectCollisions(tank, moneybagList);

        // render
        gc.clearRect(0, 0, 512, 512);
        tank.render(gc);

        for (Sprite moneybag : moneybagList)
          moneybag.render(gc);
      }
    }.start();

    theStage.show();
  }
  
  private void handleUserInputs(Tank tank, Queue<String> input, double elapsedTime) {
    tank.stop();
    
    Iterator<String> inputIterator = input.iterator();
    while (inputIterator.hasNext()) {
      String userInput = inputIterator.next();
      if (userInput.equals("LEFT") && !tank.isVerticalMoving()) {
        System.out.println("LEFT");
        tank.moveLeft();
      }
      if (userInput.equals("RIGHT") && !tank.isVerticalMoving()) {
        System.out.println("RIGHT");
        tank.moveRight();
      }
      if (userInput.equals("UP") && !tank.isHorizontalMoving()) {
        System.out.println("UP");
        tank.moveUp();
      }
      if (userInput.equals("DOWN") && !tank.isHorizontalMoving()) {
        System.out.println("DOWN");
        tank.moveDown();
      }
    }

    tank.update(elapsedTime);
  }
  
  private void detectCollisions(Tank tank, ArrayList<Sprite> sceneObjects) {
    Iterator<Sprite> sceneObjectsIter = sceneObjects.iterator();
    while (sceneObjectsIter.hasNext()) {
      Sprite sceneObject = sceneObjectsIter.next();
      if (tank.intersects(sceneObject)) {
        sceneObjectsIter.remove();
      }
    }
  }

  private Canvas addCanvas(Stage stage, Queue<String> input) {
    Group root = new Group();
    
    Scene theScene = new Scene(root);
    stage.setScene(theScene);
    
    theScene.setOnKeyPressed(getEventHandlerForKeyPressed(input));
    theScene.setOnKeyReleased(getEventHandlerForKeyReleased(input));

    Canvas canvas = new Canvas(512, 512);
    root.getChildren().add(canvas);
    return canvas;
  }

  private ArrayList<Sprite> addMoneyBags() {
    ArrayList<Sprite> moneybagList = new ArrayList<>();

    for (int i = 0; i < 15; i++) {
      Sprite moneybag = new Sprite();
      moneybag.setImage("moneybag.png");
      double px = 350 * Math.random() + 50;
      double py = 350 * Math.random() + 50;
      moneybag.setPosition(px, py);
      moneybagList.add(moneybag);
    }
    return moneybagList;
  }

  private EventHandler<? super KeyEvent> getEventHandlerForKeyReleased(Queue<String> input) {
    return event -> {
      String code = event.getCode().toString();
      input.remove(code);
    };
  }

  private EventHandler<? super KeyEvent> getEventHandlerForKeyPressed(Queue<String> input) {
    return event -> {
      String code = event.getCode().toString();
      if (!input.contains(code))
        input.add(code);
    };
  }
}
