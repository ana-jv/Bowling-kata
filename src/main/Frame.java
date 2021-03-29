package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Frame {
    List<Bonus> spare;
    List<Bonus> bonuses;

    public Frame() {
        bonuses = new ArrayList<>();
        spare = new ArrayList<>();
    }

    public int sumScore(int pinsBlockedDown, List<Bonus> spare, List<Bonus> bonuses) {
        int spareBonus = this.spareBonus(pinsBlockedDown, spare);
        int strikeBonus = this.strikeBonus(bonuses, pinsBlockedDown);
        return spareBonus + strikeBonus + pinsBlockedDown;
    }


    private int spareBonus(int pinsBlockedDown, List<Bonus> spare) {
        if (spare.isEmpty()) return 0;
        for (Bonus s : spare) {
            if (!s.isStrikeScoreCompleted()) {
                s.addRollScore(pinsBlockedDown);
                return pinsBlockedDown;
            }
        }
        return 0;
    }

    private int strikeBonus(List<Bonus> previousBonuses, int pinsBlockedDown) {
        if (previousBonuses.isEmpty()) return 0;
        int strikesScore = 0;
        List<Bonus> newBonuses = new ArrayList<>();
        this.addMyOwnStrike(newBonuses);
        for (Bonus bonus : previousBonuses) {
            if (!bonus.isStrikeScoreCompleted()) {
                bonus.addRollScore(pinsBlockedDown);
                newBonuses.add(bonus);
                strikesScore += pinsBlockedDown;
            }
        }
        this.bonuses = newBonuses;
        return strikesScore;
    }

    private void addMyOwnStrike(List<Bonus> newBonuses) {
        this.bonuses.forEach(bonus -> {
            if (bonus.isNewStrike()) {
                newBonuses.add(bonus);
            }
        });
    }

    public List<Bonus>  getSpare() {
        return new ArrayList<>(this.spare);
    }


    public List<Bonus> getStrikes() {
        return new ArrayList<>(this.bonuses);
    }


    abstract public void roll(int pinsBlockedDown);

    abstract public boolean isFrameCompleted();


}
