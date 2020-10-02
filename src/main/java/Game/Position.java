package Game;


import java.util.ArrayList;
import java.util.Optional;

public abstract class Position {
    protected int x;
    protected int y;

    public abstract Position getCopy();

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) throws PositionException {
        this.x = x;
    }
    public Position putX(int x) throws PositionException {
        setX(x);
        return this;
    }
    public void setY(int y) throws PositionException {
        this.y = y;
    }
    public Position putY(int y) throws PositionException {
        setY(y);
        return this;
    }

    public void setPosition(Position position) throws PositionException {
        setX(position.x);
        setY(position.y);
    }


    public final ArrayList<Position> getClockwise(Position start, int count) {
        return getPositionsWithDirection(start, count, this::getOneClockwise);
    }
    public final ArrayList<Position> getCounterClockwise(Position start, int count) {
        return getPositionsWithDirection(start, count, this::getOneCounterClockwise);
    }

    private interface getOneWithDirection {
        Optional<Position> invoke(Position start);
    }
    private ArrayList<Position> getPositionsWithDirection(Position start, int count, getOneWithDirection getOne) {
        ArrayList<Position> result = new ArrayList<Position>();

        Optional<Position> optPos = Optional.of(start);
        for (int i = 0; i < count; ++i) {
            optPos = getOne.invoke(optPos.get());
            if (optPos.isPresent()) {
                result.add(optPos.get());
            } else {
                break;
            }
        }

        return result;
    }

    protected abstract Optional<Position> getOneClockwise(Position start);
    protected abstract Optional<Position> getOneCounterClockwise(Position start);

    public ArrayList<Position> getPositionsAround() {
        ArrayList<Position> around = new ArrayList<Position>(4);

        getOnTheLeft().ifPresent(that -> {
            around.add(that);
            // add diagonals on the left side
            that.getOnTheBottom().ifPresent(bot -> around.add(bot));
            that.getOnTheTop().ifPresent(top -> around.add(top));
        });

        getOnTheRight().ifPresent(that -> {
            around.add(that);
            // add diagonals on the right side
            that.getOnTheBottom().ifPresent(bot -> around.add(bot));
            that.getOnTheTop().ifPresent(top -> around.add(top));
        });

        getOnTheTop().ifPresent(that -> around.add(that));
        getOnTheBottom().ifPresent(that -> around.add(that));

        return around;
    }

    /**
     * @param source Analogy of a source of light
     * @return a list of all(3) the Positions this casts a shadow on if lighted by source
     */
    public ArrayList<Position> getPositionsBehind(Position source) {
        ArrayList<Position> around = new ArrayList<Position>(3);

        getProjection(source, 1).flatMap(this::getOpposite).ifPresent(that -> {
            // TODO: Test this
            around.add(that);
            around.addAll(this.getClockwise(that, 1));
            around.addAll(this.getCounterClockwise(that, 1));
        });

        return around;
    }

    /**
     * @param from Position that we are getting the opposite of with respect to this Position
     * @return the opposite Position of from
     */
    public Optional<Position> getOpposite(Position from) {
        int deltaX = from.x - this.x;
        int deltaY = from.y - this.y;

        Position res = getCopy();
        try {
            res.putX(this.x - deltaX).putY(this.y - deltaY);
        } catch (PositionException e) {
            System.out.println("Can't get opposite of " + from + ". " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(res);
    }

    /**
     * @param from Position we project to this Position
     * @param radius the radius on which the projection is made
     * @return The position of the projection
     */
    public Optional<Position> getProjection(Position from, int radius) {
        int deltaX = from.x - this.x;
        int deltaY = from.y - this.y;

        double hipo =  Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double hipoDivisor = hipo / radius;

        int deltaXprojection = (int) Math.round(deltaX / hipoDivisor);
        int deltaYprojection = (int) Math.round(deltaY / hipoDivisor);

        Position res = getCopy();
        try {
            res.putX(deltaXprojection + this.x).putY(deltaYprojection + this.y);
        } catch (PositionException e) {
            System.out.println("Can't get projection of " + from + ". " + e.getMessage());
            return Optional.empty();
        }

        return Optional.of(res);
    }

    public Optional<Position> getOnTheLeft() {
        Position res = getCopy();
        try {
            res.putX(x - 1);
        } catch (PositionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(res);
    }
    public Optional<Position> getOnTheRight() {
        Position res = getCopy();
        try {
            res.putX(x + 1);
        } catch (PositionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(res);
    }
    public Optional<Position> getOnTheTop() {
        Position res = getCopy();
        try {
            res.putY(y - 1);
        } catch (PositionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(res);
    }
    public Optional<Position> getOnTheBottom() {
        Position res = getCopy();
        try {
            res.putY(y + 1);
        } catch (PositionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(res);
    }

    public boolean isCloserThan(Position target, int distance) {
        int deltaX = x - target.x;
        int deltaY = y - target.y;

        return Math.round(Math.sqrt(deltaX * deltaX + deltaY * deltaY)) <= distance;
    }

    @Override
    public String toString() {
        return "{" + x + ", " + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }
}
