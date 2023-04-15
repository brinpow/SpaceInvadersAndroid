package com.example.spaceinvaders.logic;

import android.content.res.Resources;
import android.graphics.Point;

import com.example.spaceinvaders.logic.interfaces.Path;

import java.util.ArrayList;
import java.util.List;

public class PathFactoryImpl implements Path.PathFactory {
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    public Path produce(Path.PathType type, Point start) {
        int dist = screenWidth/216;
        int sX = screenWidth/11;
        int sY = screenHeight/21;
        List<PathImpl.Line> lines = new ArrayList<>();
        switch (type){
            case SMALL_RECT:
                lines.add(new PathImpl.Line(new Point( start.x + 2*sX, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 2*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-2*sX, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 5*sY),dist));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-2*sX, start.y + 5*sY),new Point( start.x + 2*sX, start.y + 5*sY),dist));
                lines.add(new PathImpl.Line( new Point( start.x + 2*sX, start.y + 5*sY),new Point( start.x + 2*sX, start.y + 2*sY), dist));
                return new PathImpl(lines);
            case BIG_RECT:
                lines.add(new PathImpl.Line(new Point( start.x + 2*sX, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 2*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-2*sX, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 7*sY),dist));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-2*sX, start.y + 7*sY),new Point( start.x + 2*sX, start.y + 7*sY),dist));
                lines.add(new PathImpl.Line( new Point( start.x + 2*sX, start.y + 7*sY),new Point( start.x + 2*sX, start.y + 2*sY), dist));
                return new PathImpl(lines);
            case STABLE:
                lines.add(new PathImpl.Line(new Point(start.x, start.y), new Point(start.x+screenWidth/216, start.y+screenHeight/426), dist));
                return new PathImpl(lines);
            case TRIANGLE:
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth/2, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 6*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-2*sX, start.y + 6*sY), new Point(start.x + 2*sX, start.y + 6*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + 2*sX, start.y + 6*sY), new Point(start.x + screenWidth/2, start.y + 2*sY), dist));
                return new PathImpl(lines);
            case DTRIANGLE:
                lines.add(new PathImpl.Line(new Point(start.x + 2*sX, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 2*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-2*sX, start.y + 2*sY), new Point(start.x + screenWidth/2, start.y + 6*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth/2, start.y + 6*sY), new Point(start.x + 2*sX, start.y + 2*sY), dist));
                return new PathImpl(lines);
            case DIAMOND:
                lines.add(new PathImpl.Line(new Point( start.x + screenWidth/2, start.y + 2*sY), new Point(start.x + screenWidth-2*sX, start.y + 5*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-2*sX, start.y + 5*sY), new Point(start.x + screenWidth/2, start.y + 8*sY),dist));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth/2, start.y + 8*sY),new Point( start.x + 2*sX, start.y + 5*sY),dist));
                lines.add(new PathImpl.Line( new Point( start.x + 2*sX, start.y + 5*sY),new Point( start.x + screenWidth/2, start.y + 2*sY), dist));
                return new PathImpl(lines);
            case HEXAGON:
                lines.add(new PathImpl.Line(new Point( start.x + 4*sX, start.y + 2*sY), new Point(start.x + screenWidth-4*sX, start.y + 2*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + screenWidth-4*sX, start.y + 2*sY), new Point(start.x + screenWidth - 2*sX, start.y + 5*sY),dist));
                lines.add(new PathImpl.Line( new Point(start.x + screenWidth-2*sX, start.y + 5*sY),new Point( start.x + screenWidth - 4*sX, start.y + 8*sY),dist));
                lines.add(new PathImpl.Line( new Point( start.x + screenWidth-4*sX, start.y + 8*sY),new Point( start.x + 4*sX, start.y + 8*sY), dist));
                lines.add(new PathImpl.Line(new Point(start.x + 4*sX, start.y + 8*sY), new Point(start.x + 2*sX, start.y + 5*sY),dist));
                lines.add(new PathImpl.Line( new Point(start.x + 2*sX, start.y + 5*sY),new Point( start.x + 4*sX, start.y + 2*sY),dist));
                return new PathImpl(lines);
        }
        return new PathImpl(lines);
    }
}
