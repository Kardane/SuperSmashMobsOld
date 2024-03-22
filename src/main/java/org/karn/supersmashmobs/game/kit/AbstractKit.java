package org.karn.supersmashmobs.game.kit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.registry.SSMItems;

public abstract class AbstractKit {
    public String id;
    public String name;
    public String desc;
    public String icon;
    public String smashicon;
    public EntityType disguiseType;

    public Item SkillAItem;
    public Item SkillBItem;
    public Item SkillCItem;
    public Item SkillDItem;
    public Item SkillEItem;
    public AbstractSkill SkillA;
    public AbstractSkill SkillB;
    public AbstractSkill SkillC;
    public AbstractSkill SkillD;
    public AbstractSkill SkillE;

    public Integer Health;
    public Integer HealthRegen;
    public Integer KnockbackMultiplier;
    public Float AttackSpeed;
    public Float AttackDamage;
    public Float Speed;

    public AbstractKit(String id,
                       String name,
                       String desc,
                       String icon,
                       String smashicon,
                       EntityType disguiseType,
                       Integer Health,
                       Integer HealthRegen,
                       Integer KnockbackMultiplier,
                       Float AttackSpeed,
                       Float AttackDamage,
                       Float Speed,
                       Item SkillAModel,
                       Item SkillBModel,
                       Item SkillCModel,
                       Item SkillDModel,
                       Item SkillEModel,
                       AbstractSkill SkillA,
                       AbstractSkill SkillB,
                       AbstractSkill SkillC,
                       AbstractSkill SkillD,
                       AbstractSkill SkillE) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.smashicon = smashicon;
        this.disguiseType = disguiseType;
        this.Health = Health;
        this.HealthRegen = HealthRegen;
        this.KnockbackMultiplier = KnockbackMultiplier;
        this.AttackSpeed = AttackSpeed;
        this.AttackDamage = AttackDamage;
        this.Speed = Speed;
        this.SkillAItem = SkillAModel;
        this.SkillBItem = SkillBModel;
        this.SkillCItem = SkillCModel;
        this.SkillDItem = SkillDModel;
        this.SkillEItem = SkillEModel;
        this.SkillA = SkillA;
        this.SkillB = SkillB;
        this.SkillC = SkillC;
        this.SkillD = SkillD;
        this.SkillE = SkillE;
    }

    public abstract void onRevive(ServerPlayerEntity player);
    public abstract void onDeath(ServerPlayerEntity player);
    public abstract void onHurt(ServerPlayerEntity player);
    public abstract void onAttack(ServerPlayerEntity player);

    public String getId(){return id;};
    public String getName(){return name;};
    public String getDesc(){return desc;};
    public String getSmashicon(){return smashicon;};
    public EntityType getDisguiseMob(){return disguiseType;};
    public AbstractSkill getSkillA(){return SkillA;};
    public AbstractSkill getSkillB(){return SkillB;};
    public AbstractSkill getSkillC(){return SkillC;};
    public AbstractSkill getSkillD(){return SkillD;};
    public AbstractSkill getSkillE(){return SkillE;};

    public Item getSkillAItem(){return SkillAItem;};
    public Item getSkillBItem(){return SkillBItem;};
    public Item getSkillCItem(){return SkillCItem;};
    public Item getSkillDItem(){return SkillDItem;};
    public Item getSkillEItem(){return SkillEItem;}
}
