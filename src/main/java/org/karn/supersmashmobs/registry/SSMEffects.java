package org.karn.supersmashmobs.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.karn.supersmashmobs.effect.common.PrepareEffect;
import org.karn.supersmashmobs.effect.common.Slow;
import org.karn.supersmashmobs.game.kit.drowned.DrownedSkillE;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class SSMEffects {
    public static final StatusEffect TEST = register("test", new Slow());
    public static final StatusEffect GAME_PREPARE = register("game_prepare", new PrepareEffect());
    public static final StatusEffect DROWNED_RIPTIDE = register("drowned_riptide", new DrownedSkillE.RiptideEffect());

    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID,id), statusEffect);
    }

    public static void init(){}
}
