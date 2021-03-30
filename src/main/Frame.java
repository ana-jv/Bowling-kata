package main;

import java.util.List;

public abstract class Frame {
    Bonus frameBonus;

    public Frame() {
        this.frameBonus = Bonus.noBonus();
    }

    public int sumScore(int pinsBlockedDown, List<Bonus> previousBonuses) {
        return pinsBlockedDown + bonusScore(previousBonuses, pinsBlockedDown);
    }


    private int bonusScore(List<Bonus> previousBonuses, int pinsBlockedDown) {
        if (previousBonuses.isEmpty()) return 0;
        int strikesScore = 0;
        for (Bonus bonus : previousBonuses) {
            if (!bonus.isBonusScoreCompleted()) {
                bonus.addRollScore(pinsBlockedDown);
                strikesScore += pinsBlockedDown;
            }
        }
        return strikesScore;
    }

    public Bonus getFrameBonus() {
        return frameBonus;
    }

    abstract public void roll(int pinsBlockedDown);

    abstract public boolean isFrameCompleted();


}
