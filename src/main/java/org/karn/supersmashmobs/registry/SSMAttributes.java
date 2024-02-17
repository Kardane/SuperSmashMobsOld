package org.karn.supersmashmobs.registry;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class SSMAttributes {
    public static final EntityAttribute HEALTH_REGEN = registerAttribute(new Identifier(MODID,"ssm_health_regen"), "attribute.ssm.health_regen",0,0,10000);
    public static final EntityAttribute KNOCKBACK_TAKEN = registerAttribute(new Identifier(MODID,"ssm_knockback_taken"), "attribute.ssm.knockback_taken",100,0,10000);
    public static final EntityAttribute PROTECTION = registerAttribute(new Identifier(MODID,"ssm_protection"), "attribute.ssm.protection",100,0,10000);
    public static final EntityAttribute ATTACK_SPEED = registerAttribute(new Identifier(MODID,"ssm_attack_speed"), "attribute.ssm.attack_speed",20,1,10000);
    public static final EntityAttribute ATTACK_DMG = registerAttribute(new Identifier(MODID,"ssm_attack_damage"), "attribute.ssm.attack_damage",1,0,10000);
    public static final ObjectSet<EntityAttribute> ATTRIBUTES = new ObjectOpenHashSet<>();
    private static EntityAttribute registerAttribute(Identifier id, String translationKey) {
        return registerAttribute(id, translationKey, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static EntityAttribute registerAttribute(Identifier id, String translationKey, int defaultValue, int minValue, int maxValue) {
        return Registry.register(Registries.ATTRIBUTE, id, new ClampedEntityAttribute(translationKey, defaultValue, minValue, maxValue));
    }

    private static void put(EntityAttribute a) {
        ATTRIBUTES.add(a);
    }

    static {
        put(HEALTH_REGEN);
        put(KNOCKBACK_TAKEN);
        put(PROTECTION);
        put(ATTACK_SPEED);
        put(ATTACK_DMG);
    }

    public static void init(){}
}
