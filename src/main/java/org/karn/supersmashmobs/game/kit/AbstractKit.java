package org.karn.supersmashmobs.game.kit;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;

public abstract class AbstractKit {
    public String id;
    public String name;
    public String desc;
    public String icon;
    public EntityType disguiseType;

    public Integer Health;
    public Integer HealthRegen;
    public Integer KnockbackMultiplier;
    public Float AttackSpeed;
    public Float AttackDamage;
    public Float Speed;

    public AbstractKit(String id, String name, String desc, String icon, EntityType disguiseType, Integer Health, Integer HealthRegen, Integer KnockbackMultiplier, Float AttackSpeed, Float AttackDamage, Float Speed){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        this.disguiseType = disguiseType;
        this.Health = Health;
        this.HealthRegen = HealthRegen;
        this.KnockbackMultiplier = KnockbackMultiplier;
        this.AttackSpeed = AttackSpeed;
        this.AttackDamage = AttackDamage;
        this.Speed = Speed;
    }

    public abstract void onRevive(ServerPlayerEntity player);
    public abstract void onDeath(ServerPlayerEntity player);
    public abstract void onHurt(ServerPlayerEntity player);
    public abstract void onAttack(ServerPlayerEntity player);

    public abstract String getId();
    public abstract String getName();
    public abstract String getDesc();
    public abstract EntityType getDisguiseMob();
}
