package org.karn.supersmashmobs.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.List;

public abstract class AbstractSSMEffect extends InstantStatusEffect implements PolymerStatusEffect {
    public String name;
    public String icon;
    public List<SSMEffectType> type;
    public AbstractSSMEffect(StatusEffectCategory statusEffectCategory, String name, String icon, List<SSMEffectType> type) {
        super(statusEffectCategory, 0);
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public String getID() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public List<SSMEffectType> getType() {
        return type;
    }

}
