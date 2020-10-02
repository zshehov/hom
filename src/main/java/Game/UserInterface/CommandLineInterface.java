package Game.UserInterface;

import Game.GAME_CONFIG;
import Game.LimitedPosition;
import Game.Position;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * INPUTS FOR CLI:
 *
 * buy <int> <int> <String> - posX, posY, unit name
 * upgrade <int> <int> - posX, posY
 *
 * move <int> <int> <int> <int> - srcposX, srcposY, dstposX, dstposY
 * attack <int> <int> <int> <int> - srcposX, srcposY, dstposX, dstposY
 * cast <int> <int> <int> <int> <String> -srcposX, srcposY, dstposX, dstposY, spell name
 *
 * end
 */

public class CommandLineInterface implements UserInterface {
    @Override
    public void takeInput() throws InvalidInputException {
        parseLine();
    }

    @Override
    public Action getCurrentAction() {
        return currentAction;
    }

    @Override
    public ActionType getCurrentActionType() {
        return currentActionType;
    }

    public CommandLineInterface() {
        this.scanner = new Scanner(System.in);
        this.commandFormats = new HashMap<>();

        String srcPosition = "<srcX> <srcY>";
        String dstPosition = "<dstX> <dstY>";
        commandFormats.put(ActionType.ATTACK.toString(), ActionType.ATTACK.toString() + " " + srcPosition + " " + dstPosition);
        commandFormats.put(ActionType.MOVE.toString(), ActionType.MOVE.toString() + " " + srcPosition + " " + dstPosition);
        commandFormats.put(ActionType.CAST.toString(), ActionType.CAST.toString() + " " + srcPosition + " " + dstPosition + " <spell name>");

        commandFormats.put(ActionType.BUY.toString(), ActionType.BUY.toString() + " " + srcPosition + " <unit name>");
        commandFormats.put(ActionType.UPGRADE.toString(), ActionType.UPGRADE.toString() + " " + srcPosition);

        commandFormats.put(ActionType.END.toString(), ActionType.END.toString());
    }

    private void parseLine() throws InvalidInputException {
        /* clear any past parsed actions */
        currentActionType = null;
        currentAction = null;
        System.out.print("Enter action: ");

        String line = scanner.nextLine();
        String[] words = line.split("\\s+");

        String command = words[0];

        if (command.equals(ActionType.ATTACK.toString()) ||
                command.equals((ActionType.MOVE.toString())) ||
                command.equals((ActionType.CAST.toString()))) {
            /* actions that have src and dst positions */

            if (words.length < 5) {
                throw new InvalidInputException("Wrong argument count. Expected in format: "
                        + commandFormats.get(command));
            }
            Position srcPos = getPositionFromArgs(words[1], words[2]);
            Position dstPos = getPositionFromArgs(words[3], words[4]);

            SrcAndDstPositionAction action;
            if (command.equals(ActionType.ATTACK.toString())) {
                action = new AttackAction();
            } else if (command.equals(ActionType.MOVE.toString())) {
                action = new MoveAction();
            } else if (command.equals(ActionType.CAST.toString())) {
                if (words.length < 6) {
                    throw new InvalidInputException("Wrong argument count. Expected format: "
                            + commandFormats.get(command));
                }
                CastAction castAction = new CastAction();
                castAction.setSpellName(words[5]);
                action = castAction;
            } else {
                /* What !? */
                throw new InvalidInputException("This is not acceptable");
            }

            setCurrentAction(action);
            action.setSrcPosition(srcPos);
            action.setDstPosition(dstPos);
        } else if (command.equals(ActionType.UPGRADE.toString()) ||
                command.equals(ActionType.BUY.toString())) {
            /* actions that have src position only */
            if (words.length < 3) {
                throw new InvalidInputException("Wrong argument count. Expected format: "
                        + commandFormats.get(command));
            }

            Position srcPos = getPositionFromArgs(words[1], words[2]);

            SrcPositionAction action;
            if (command.equals(ActionType.UPGRADE.toString())) {
                action = new UpgradeAction();
            } else if (command.equals(ActionType.BUY.toString())) {
                if (words.length < 4) {
                    throw new InvalidInputException("Wrong argument count. Expected format: "
                            + commandFormats.get(command));
                }
                BuyAction buyAction = new BuyAction();
                buyAction.setUnitName(words[3]);
                action = buyAction;
            } else {
                /* What !? */
                throw new InvalidInputException("This is not acceptable");
            }

            setCurrentAction(action);
            action.setSrcPosition(srcPos);
        }  else if (command.equals(ActionType.END.toString())) {
            /* actions with  no arguments */
            setCurrentAction(new EndAction());
        } else {
            throw new InvalidInputException("Unknown command " + command);
        }
    }

    private Position getPositionFromArgs(String op1, String op2) throws InvalidInputException {
        Position pos;
        try {
            pos = new LimitedPosition(
                    Integer.parseInt(op1), Integer.parseInt(op2),
                    GAME_CONFIG.FIELD_WIDTH, GAME_CONFIG.FIELD_HEIGHT);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid format of position: " + e.getMessage());
        }
        return pos;
    }

    private void setCurrentAction(Action action) {
        currentAction = action;
        currentActionType = action.getType();
    }
    private Scanner scanner;
    private Action currentAction;
    private ActionType currentActionType;
    private HashMap<String, String> commandFormats;
}
