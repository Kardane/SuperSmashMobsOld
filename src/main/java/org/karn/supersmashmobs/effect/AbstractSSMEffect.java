package org.karn.supersmashmobs.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class AbstractSSMEffect extends InstantStatusEffect implements PolymerStatusEffect {
    public static String name;
    public static String icon;
    public static SSMEffectType type;
    public AbstractSSMEffect(StatusEffectCategory statusEffectCategory, String name, String icon, SSMEffectType type) {
        super(statusEffectCategory, 0);
    }

    public static String getID() {
        return name;
    }

    public static String getIcon() {
        return icon;
    }

    public static SSMEffectType getType() {
        return type;
    }

}
