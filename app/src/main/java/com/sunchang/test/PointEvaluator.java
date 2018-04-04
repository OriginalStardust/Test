package com.sunchang.test;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 2018/4/3.
 */

public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float v, Object o, Object t1) {
        Point startPoint = (Point) o;
        Point endPoint = (Point) t1;

        float x = startPoint.getX() + v * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + v * (endPoint.getY() - startPoint.getY());

        Point point = new Point(x, y);
        return point;
    }
}
