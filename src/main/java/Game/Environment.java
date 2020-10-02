package Game;

import Units.UnitGroup;

import java.util.ArrayList;

/**
 * This class is a singleton since some units might want to access an instance of it to be able to
 * perform different abilities. Otherwise - such Units should be passed a reference to it which is
 * verbose and special handling is needed when those Units are created
 */
public class Environment {
    private Environment() {}

    private static class SingletonHelper {
        private static final Environment instance = new Environment();
    }

    public static Environment getEnvironment() {
        return SingletonHelper.instance;
    }

    public UnitGroup getEnemiesOf(int teamIndex) {
        // this assumes only 2 teams
        return players.get(1 - teamIndex).getUnits();
    }

    public UnitGroup getAlliesOf(int teamIndex) {
        return players.get(teamIndex).getUnits();
    }

    /**
     * @return all units present on the map
     */
    public UnitGroup getAllUnits() {
        UnitGroup allUnits = new UnitGroup();

        for (Player player : players) {
            allUnits.getUnits().addAll((player.getUnits().getUnits()));
        }

        return allUnits;
    }

    synchronized Player createPlayer() {
        players.add(new Player(players.size(), 500));
        return players.get(players.size() - 1);
    }

    private ArrayList<Player> players = new ArrayList<Player>();
}
