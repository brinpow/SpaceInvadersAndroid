package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Path;

import java.util.ArrayList;
import java.util.List;

public class PathFactoryImpl implements Path.PathFactory {
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    public Path produce(Path.PathType type, Point start) {
        List<PathImpl.Line> lines = new ArrayList<>();
        switch (type){
            case SMALL_RECT:
                lines.add(new PathImpl.Line(new Point( start.x + 200, start.y + 200), new Point(start.x + screenWidth-200, start.y + 200), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-200, start.y + 200), new Point(start.x + screenWidth-200, start.y + 500),5));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-200, start.y + 500),new Point( start.x + 200, start.y + 500),5));
                lines.add(new PathImpl.Line( new Point( start.x + 200, start.y + 500),new Point( start.x + 200, start.y + 200), 5));
                return new PathImpl(lines);
            case BIG_RECT:
                lines.add(new PathImpl.Line(new Point( start.x + 200, start.y + 200), new Point(start.x + screenWidth-200, start.y + 200), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-200, start.y + 200), new Point(start.x + screenWidth-200, start.y + 700),5));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-200, start.y + 700),new Point( start.x + 200, start.y + 700),5));
                lines.add(new PathImpl.Line( new Point( start.x + 200, start.y + 700),new Point( start.x + 200, start.y + 200), 5));
                return new PathImpl(lines);
            case STABLE:
                lines.add(new PathImpl.Line(new Point(start.x, start.y), new Point(start.x+6, start.y+6), 5));
                return new PathImpl(lines);
            case TRIANGLE:
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth/2, start.y + 200), new Point(start.x + screenWidth-200, start.y + 600), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-200, start.y + 600), new Point(start.x + 200, start.y + 600), 5));
                lines.add(new PathImpl.Line(new Point(start.x + 200, start.y + 600), new Point(start.x + screenWidth/2, start.y + 200), 5));
                return new PathImpl(lines);
            case DTRIANGLE:
                lines.add(new PathImpl.Line(new Point(start.x + 200, start.y + 200), new Point(start.x + screenWidth-200, start.y + 200), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-200, start.y + 200), new Point(start.x + screenWidth/2, start.y + 600), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth/2, start.y + 600), new Point(start.x + 200, start.y + 200), 5));
                return new PathImpl(lines);
            case DIAMOND:
                lines.add(new PathImpl.Line(new Point( start.x + screenWidth/2, start.y + 200), new Point(start.x + screenWidth-200, start.y + 500), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-200, start.y + 500), new Point(start.x + screenWidth/2, start.y + 800),5));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth/2, start.y + 800),new Point( start.x + 200, start.y + 500),5));
                lines.add(new PathImpl.Line( new Point( start.x + 200, start.y + 500),new Point( start.x + screenWidth/2, start.y + 200), 5));
                return new PathImpl(lines);
            case HEXAGON:
                lines.add(new PathImpl.Line(new Point( start.x + 400, start.y + 200), new Point(start.x + screenWidth-400, start.y + 200), 5));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-400, start.y + 200), new Point(start.x + screenWidth - 200, start.y + 500),5));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-200, start.y + 500),new Point( start.x + screenWidth - 400, start.y + 800),5));
                lines.add(new PathImpl.Line( new Point( start.x + screenWidth-400, start.y + 800),new Point( start.x + 400, start.y + 800), 5));
                lines.add(new PathImpl.Line(new Point(start.x + 400, start.y + 800), new Point(start.x + 200, start.y + 500),5));
                lines.add(new PathImpl.Line( new Point(start.x + 200, start.y + 500),new Point( start.x + 400, start.y + 200),5));
                return new PathImpl(lines);
        }
        return new PathImpl(lines);
    }
}
