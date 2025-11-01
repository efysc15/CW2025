package com.comp2042.logic.bricks;

import java.util.List;

public abstract class Brick {
    protected int id;
    protected List <int[][]> shapeMatrix;
    
    public Brick (int id, List <int[][]> shapeMatrix) {
        this.id = id;
        this.shapeMatrix = shapeMatrix;
    }

    public int getId() {
        return id;
    }

    public List<int[][]> getShapeMatrix() {
        return shapeMatrix;
    }
}
