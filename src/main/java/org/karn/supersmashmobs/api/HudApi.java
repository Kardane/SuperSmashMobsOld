package org.karn.supersmashmobs.api;

import org.karn.supersmashmobs.game.kit.AbstractKit;

public interface HudApi {
    boolean canFinalSmash();

    void setFinalSmash(boolean value);

    AbstractKit getKit();

    void setKit(AbstractKit kit);

    int getHurtValue();

    int getSkillCoolA();

    int getSkillCoolB();

    int getSkillCoolC();

    void setHurtValue(int value);

    void setSkillCoolA(int value);

    void setSkillCoolB(int value);

    void setSkillCoolC(int value);
}
