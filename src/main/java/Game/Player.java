package Game;

import Game.UserInterface.*;
import Units.*;
import Units.Skills.OutOfManaException;

import java.util.NoSuchElementException;
import java.util.Optional;

public class Player {
    private UnitGroup army;

    private int gold;
    private int teamIndex;

    Player(int teamIndex, int gold) {
        this.gold = gold;
        this.teamIndex = teamIndex;
        this.army = new UnitGroup();

        this.userInterface = new CommandLineInterface();
    }

    private UserInterface userInterface;

    public UnitGroup getUnits() {
        return army;
    }

    void buyUnits(Shop shop) {
        System.out.println("Player " + teamIndex + "'s buy phase");

        boolean endOfTurn = false;
        while(!endOfTurn) {
            try {
                userInterface.takeInput();
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                System.out.println("Please retry");
                continue;
            } catch (NoSuchElementException e) {
                System.out.println("Game ended");
                System.exit(0);
            }

        switch (userInterface.getCurrentActionType()) {
                case BUY:
                    buyUnit(shop, (BuyAction) userInterface.getCurrentAction());
                    break;
                case UPGRADE:
                    upgradeUnit(shop, (UpgradeAction) userInterface.getCurrentAction());
                    break;
                case END:
                    System.out.println("Turn ended");
                    endOfTurn = true;
                    break;
                default:
                    System.out.println("Wtf are you trying to do");
            }
        }
    }

    /**
     * A turn is moving or attacking with a unit
     */
    void playTurn() {
        army.liveTurn();

        boolean endOfTurn = false;
        while (!endOfTurn) {
            try {
                userInterface.takeInput();
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
                System.out.println("Please retry");
                continue;
            } catch (NoSuchElementException e) {
                System.out.println("Game ended");
                System.exit(0);
            }

            switch (userInterface.getCurrentActionType()) {
                case MOVE:
                    moveUnit((MoveAction) userInterface.getCurrentAction());
                    break;
                case ATTACK:
                    attackUnit((AttackAction) userInterface.getCurrentAction());
                    break;
                case CAST:
                    castWithUnit((CastAction) userInterface.getCurrentAction());
                    break;
                case END:
                    System.out.println("Turn ended");
                    endOfTurn = true;
                    break;
                default:
                    System.out.println("Wtf are you trying to do");
            }
        }
    }

    private void buyUnit(Shop shop, BuyAction action) {
        try {
            int price = shop.getPrice(action.getUnitName());
            if (gold - price < 0) {
                System.out.println("No money. This costs " + price + " you have " + gold + ".");
                return;
            }

            if (army.unitIsPresentAt(action.getSrcPosition())) {
                throw new PositionException("Unit already present at " + action.getSrcPosition() + ".");
            }

            int x = action.getSrcPosition().x;
            int y = action.getSrcPosition().y;

            if (x < 0 || x >= GAME_CONFIG.FIELD_WIDTH || y < 0 || y >= GAME_CONFIG.FIELD_HEIGHT) {
                throw new PositionException("Unit will be out of the field at " + action.getSrcPosition() + ".");
            }

            if (teamIndex == 0) {
                if (x >= GAME_CONFIG.TEAM_LINE) {
                    throw new PositionException("Unit can't go over the start line " + action.getSrcPosition() + ".");
                }
            } else {
                if (x < GAME_CONFIG.TEAM_LINE) {
                    throw new PositionException("Unit can't go over the start line " + action.getSrcPosition() + ".");
                }
            }

            Unit unit = shop.getBasicUnit(action.getUnitName(), x, y);

            unit.assignToTeam(teamIndex);
            army.addUnit(unit);

            gold -= price;
        } catch (java.lang.IllegalArgumentException ex) {
            System.out.println("Unit purchase failed for unit: " + action.getUnitName() + ". " + ex.getMessage());
        } catch (PositionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void moveUnit(MoveAction action) {
        try {
            Unit srcUnit = selectUnitAt(action.getSrcPosition());
            srcUnit.moveTo(action.getDstPosition());
        } catch (PositionException | ExhaustionException | DisabledException e) {
            System.out.println(e.getMessage() + " Try again");
        }
    }

    private void attackUnit(AttackAction action) {
        try {
            Unit srcUnit = selectUnitAt(action.getSrcPosition());
            srcUnit.attackPosition(action.getDstPosition());
        } catch (PositionException e) {
            System.out.println("Couldn't attack enemy unit (" + e.getMessage() + "). Try again");
        } catch (ExhaustionException | DisabledException e) {
            System.out.println(e.getMessage() + " Try again");
        }
    }

    private void castWithUnit(CastAction action) {
        try {
            Unit srcUnit = selectUnitAt(action.getSrcPosition());
            srcUnit.castAtPosition(action.getSpellName(), action.getDstPosition());
        } catch (PositionException e) {
            System.out.println("Couldn't attack enemy unit (" + e.getMessage() + "). Try again");
        } catch (IllegalArgumentException |  DisabledException | OutOfManaException e) {
            System.out.println(e.getMessage() + " Try again");
        }
    }

    private void upgradeUnit(Shop shop, UpgradeAction action) {
        try {
            Unit unit = selectUnitAt(action.getSrcPosition());

            int price = shop.getUpgradePrice(unit.getName());
            if (gold - price < 0) {
                System.out.println("No money. This upgrade costs " + price + " you have " + gold + ".");
                return;
            }

            unit.upgrade();
        } catch (java.lang.IllegalArgumentException ex) {
            System.out.println("Unit upgrade failed for unit at: " + action.getSrcPosition() + ". " + ex.getMessage());
        } catch (PositionException e) {
            System.out.println("Couldn't upgrade unit (" + e.getMessage() + "). Try again");
        }
    }

    private Unit selectUnitAt(Position position) throws PositionException {
        Optional<Unit> unit = army.getUnitAt(position);
        if (!unit.isPresent()) {
            throw new PositionException("There is no ally unit on " + position);
        }
        return unit.get();
    }

    @Override
    public String toString() {
        return "Player " + teamIndex + "'s army is: " + army;
    }
}
