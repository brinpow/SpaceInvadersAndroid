package com.example.spaceinvaders.logic;

import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Path;

import java.util.List;

public class PathImpl implements Path {
    public static class Line{
        private final Point start;
        private final Point end;
        private final int dist;

        public Line(Point start, Point end, int dist){
            this.start = start;
            this.end = end;
            this.dist = dist;
        }

        public int getLineLength(){
            double distance = Math.sqrt((start.x-end.x)*(start.x-end.x) + (start.y-end.y)*(start.y-end.y));
            return (int) distance/dist;
        }

        public Point getNextPoint(int i){
            double distance = Math.sqrt((start.x-end.x)*(start.x-end.x) + (start.y-end.y)*(start.y-end.y));
            double diff = Math.abs(end.y - start.y);
            double angle = diff/distance;

            double currentDistance = dist*i;
            double diffY = angle*currentDistance;
            double diffX = Math.sqrt(currentDistance*currentDistance-diffY*diffY);

            return new Point((int) (Math.signum(end.x-start.x)*diffX+start.x), (int)(Math.signum(end.y-start.y)*diffY + start.y));
        }

    }

    private final List<Line> pathList;
    private final int pathSize;

    public PathImpl(List<Line> pathList){
        this.pathList = pathList;
        int i = 0;
        for(Line line: pathList){
            i += line.getLineLength();
        }
        pathSize = i;
    }

    @Override
    public Point get(int i) {
        int currentIndex = i%pathSize;
        int index = 0;
        for(Line line: pathList){
            int size = line.getLineLength();
            if(currentIndex<index+size){
                return line.getNextPoint(currentIndex-index);
            }
            index += size;
        }
        return new Point(0, 0);
    }

    @Override
    public int getSize() {
        return pathSize;
    }

}
