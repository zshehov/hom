package Game.UserInterface;


public interface UserInterface {
    void takeInput() throws InvalidInputException;

    Action getCurrentAction();
    ActionType getCurrentActionType();
}
