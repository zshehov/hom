package Game.UserInterface;

public enum ActionType {
    BUY {
        @Override
        public String toString() {
            return "buy";
        }
    },
    MOVE {
        @Override
        public final String toString() {
            return "move";
        }
    },
    ATTACK {
        @Override
        public final String toString() {
            return "attack";
        }
    },
    UPGRADE {
        @Override
        public final String toString() {
            return "upgrade";
        }
    },
    CAST {
        @Override
        public final String toString() {
            return "cast";
        }

    },
    END {
        @Override
        public final String toString() {
            return "end";
        }
    };
}
