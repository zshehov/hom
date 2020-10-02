package Game;

import java.util.Optional;

public class LimitedPosition extends Position {
    private int maxX;
    private int maxY;

    public LimitedPosition(int x, int y, int maxX, int maxY) {
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
    }
    public LimitedPosition(LimitedPosition rhs) {
        this.x = rhs.x;
        this.y = rhs.y;
        this.maxX = rhs.maxX;
        this.maxY = rhs.maxY;
    }

    @Override
    public Position getCopy() {
        return new LimitedPosition(this);
    }

    public void setX(int x) throws PositionException {
        if (x < 0 || x >= maxX) {
            throw new PositionException("This x " + x + " goes over the limits [0, " + maxX + "] ");
        }

        this.x = x;
    }

    public void setY(int y) throws PositionException {
        if (y < 0 || y >= maxY) {
            throw new PositionException("This y " + y + " goes over the limits [0, " + maxY + "] ");
        }
        this.y = y;
    }

    @Override
    protected Optional<Position> getOneClockwise(Position start) {
        // this expects correct start position in the sense that it is on radius 1

        int deltaX = start.x - this.x;
        int deltaY = start.y - this.y;

        int resX = start.x;
        int resY = start.y;

        if (deltaY == -1 && deltaX < 1) {
            resX++;
        } else if (deltaX == 1 && deltaY < 1) {
            resY++;
        } else if (deltaY == 1 && deltaX > -1) {
            resX--;
        } else if (deltaX == -1 && deltaY > -1) {
            resY--;
        } else {
            System.out.println("wtf");
        }

        LimitedPosition res = new LimitedPosition(this);
        try {
            res.putX(resX).putY(resY);
        } catch (PositionException e) {
            System.out.println("Can't get one clockwise. " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(res);
    }

    // TODO: deal with this duplication
    @Override
    protected Optional<Position> getOneCounterClockwise(Position start) {
        // this expects correct start position in the sense that is around focal

        int deltaX = start.x - this.x;
        int deltaY = start.y - this.y;

        int resX = start.x;
        int resY = start.y;

        if (deltaY == -1 && deltaX > 1) {
            resX--;
        } else if (deltaX == -1 && deltaY < 1) {
            resY++;
        } else if (deltaY == 1 && deltaX < 1) {
            resX++;
        } else if (deltaX == 1 && deltaY > -1) {
            resY--;
        } else {
            System.out.println("wtf");
        }

        LimitedPosition res = new LimitedPosition(this);
        try {
            res.putX(resX).putY(resY);
        } catch (PositionException e) {
            System.out.println("Can't get one counterclockwise. " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(res);
    }

}
