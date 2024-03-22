package org.karn.supersmashmobs.game.kit;

import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.api.HudApi;

public abstract class AbstractSkill {
    public String id;
    public String name;
    public String desc;
    public Integer cooldown;
    public SkillType type;
    public AbstractSkill(String id, String name, String desc, Integer cooldown, SkillType type){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.cooldown = cooldown;
        this.type = type;
    }

    public String getId(){return id;};
    public String getName(){return name;};
    public String getDesc(){return desc;};
    public SkillType getSkillType(){return type;};
    public Integer getCooldown(){return cooldown;};
}
