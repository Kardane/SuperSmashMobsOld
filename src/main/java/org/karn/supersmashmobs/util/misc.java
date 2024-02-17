package org.karn.supersmashmobs.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;

public class misc {
    public static boolean isVoidDamage(DamageSource source){
        return source.isOf(DamageTypes.GENERIC_KILL) || source.isOf(DamageTypes.GENERIC) || source.isOf(DamageTypes.OUT_OF_WORLD)|| source.isOf(DamageTypes.FALL);
    }
}
