package com.xyp.draw.rectangle;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xyp
 * @date 2022/5/27 18:59
 */
public class learn extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //鼠标左键键开始坐标
        AtomicReference<Double> lStartX = new AtomicReference<>((double) 0);
        AtomicReference<Double> lStartY = new AtomicReference<>((double) 0);
        //鼠标右键开始坐标
        AtomicReference<Double> rStartX = new AtomicReference<>((double) 0);
        AtomicReference<Double> rStartY = new AtomicReference<>((double) 0);
        //鼠标右键结束坐标
        AtomicReference<Double> rEndX = new AtomicReference<>((double) 0);
        AtomicReference<Double> rEndY = new AtomicReference<>((double) 0);

        Group root = new Group();
        Scene scene = new Scene(root);

        //新建矩形
        Rectangle r = new Rectangle();
        root.getChildren().add(r);
        //鼠标按下事件
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                r.setHeight(0);
                r.setWidth(0);
                lStartX.set(event.getX());
                lStartY.set(event.getY());
                r.setX(lStartX.get());
                r.setY(lStartY.get());
                System.out.println("矩形开始坐标:" + lStartX.get() + "," + lStartY.get());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("鼠标右键键按下");
                rStartX.set(event.getX());
                rStartY.set(event.getY());
                System.out.println(rStartX.get() + "," + rStartY.get());
            }
        });
//        鼠标拖动事件
        scene.setOnMouseDragged(event -> {
            //判断是不是鼠标左键
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("鼠标左键拖动");
                if (event.getX() < lStartX.get()) {
                    r.setX(event.getX());
                } else {

                    if (event.getX() != lStartX.get()) {
                        r.setX(lStartX.get());
                    }
                }
                if (event.getY() < lStartY.get()) {
                    r.setY(event.getY());
                } else {
                    if (event.getY() != lStartY.get()) {
                        r.setY(lStartY.get());
                    }
                }
                r.setWidth(Math.abs(event.getX() - lStartX.get()));
                r.setHeight(Math.abs(event.getY() - lStartY.get()));
                System.out.println(event.getX() + "," + event.getY());
            }
        });
        //鼠标抬起事件
        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("鼠标左键抬起");
                if (event.getX() < r.getX()) {
                    r.setX(event.getX());
                }
                if (event.getY() < lStartY.get()) {
                    r.setY(event.getY());
                }
                r.setWidth(Math.abs(lStartX.get() - event.getX()));
                r.setHeight(Math.abs(lStartY.get() - event.getY()));
                System.out.println(event.getX() + "," + event.getY());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("鼠标右键抬起");
                rEndX.set(event.getX());
                rEndY.set(event.getY());
                if (Math.abs(rEndX.get() - rStartX.get()) < 10 && Math.abs(rEndY.get() - rStartY.get()) < 10) {
                    if (r.getWidth() != 0 || r.getHeight() != 0) {
                        r.setHeight(0);
                        r.setWidth(0);
                    } else {
                        primaryStage.close();
                    }
                }
                System.out.println(rEndX.get() + "," + rEndY.get());
            }
        }); //创建一个robot对象
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });
        root.setOpacity(0.5);
        primaryStage.setScene(scene);
        //置顶 最大化 30%透明度 无边框 取消关闭 隐藏任务栏图标
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setMaximized(true);
        primaryStage.setOpacity(0.5);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}
