package org.karn.supersmashmobs.game.kit.none;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.ServerPlayerEntity;
import org.karn.supersmashmobs.game.kit.AbstractKit;

public class NoneKit extends AbstractKit {
    public static String id = "none";
    public static String name = "None";
    public static String desc = "Default kit. No special ability.";
    public static String icon = "\u5000";
    public static EntityType disguiseType = EntityType.ZOMBIE;

    public static Integer Health = 100;
    public static Integer HealthRegen = 1;
    public static Integer KnockbackMultiplier = 100;
    public static Float AttackSpeed = 20f;
    public static Float AttackDamage = 10f;
    public static Float Speed = 0.1f;
    public NoneKit(){
        super(id, name, desc, icon,EntityType.ZOMBIE,Health,HealthRegen,KnockbackMultiplier,AttackSpeed,AttackDamage,Speed);
        new NoneSkillA();
        new NoneSkillB();
        new NoneSkillC();
        new NoneSkillD();
        System.out.println("NoneKit skills initialized!");
    }

    @Override public void onRevive(ServerPlayerEntity player) {

    }
    @Override public void onDeath(ServerPlayerEntity player) {

    }
    @Override public void onHurt(ServerPlayerEntity player) {

    }
    @Override public void onAttack(ServerPlayerEntity player) {

    }

    //================================================================================================
    @Override public String getId() {
        return id;
    }
    @Override public String getName() {
        return name;
    }
    @Override public String getDesc() {
        return desc;
    }
    @Override public EntityType getDisguiseMob() {
        return disguiseType;
    }
}
