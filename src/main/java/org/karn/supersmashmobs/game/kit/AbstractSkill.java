package org.karn.supersmashmobs.game.kit;

import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.api.HudApi;

public abstract class AbstractSkill {
    public static String id;
    public static String name;
    public static String desc;
    public static Integer cooldown;
    public static SkillType type;
    public AbstractSkill(String id, String name, String desc, Integer cooldown, SkillType type){
        id = id;
        name = name;
        desc = desc;
        cooldown = cooldown;
        type = type;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getDesc();
    public abstract SkillType getSkillType();
    public abstract Integer getCooldown();
}
