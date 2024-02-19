package org.karn.supersmashmobs.registry;

import eu.pb4.polymer.core.api.other.PolymerSoundEvent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class SSMSounds {
    public static final SoundEvent COUNTDOWN_1 = register("game.1", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent COUNTDOWN_2 = register("game.2", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent COUNTDOWN_3 = register("game.3", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent COUNTDOWN_4 = register("game.4", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent COUNTDOWN_5 = register("game.5", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent COUNTDOWN_GO = register("game.go", SoundEvents.ENTITY_ENDER_DRAGON_GROWL);
    public static final SoundEvent COUNTDOWN_READY = register("game.ready", SoundEvents.BLOCK_NOTE_BLOCK_BELL.value());
    public static final SoundEvent PREPARE = register("game.prepare", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent GAMESET = register("game.gameset", SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH);
    public static final SoundEvent DEATH1 = register("death1", SoundEvents.ENTITY_WITHER_DEATH);
    public static final SoundEvent DEATH2 = register("death2", SoundEvents.ENTITY_WITHER_DEATH);
    public static final SoundEvent FINAL_SMASH = register("finalsmash", SoundEvents.BLOCK_END_PORTAL_SPAWN);
    // BGM
    public static final SoundEvent BGM_FALCON_1 = register("bgm.falcon1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_STARFOX_1 = register("bgm.starfox1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_METAKNIGHT_1 = register("bgm.metaknight1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_TERRY_1 = register("bgm.terry1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BMG_KINGKLUL_1 = register("bgm.kingklul1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_ZELDA_1 = register("bgm.zelda1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_ZELDA_2 = register("bgm.zelda2", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_STEVE_1 = register("bgm.steve1", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_STEVE_2 = register("bgm.steve2", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    public static final SoundEvent BGM_STEVE_3 = register("bgm.steve3", SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
    private static SoundEvent register(String name, SoundEvent soundEvent) {
        Identifier identifier = new Identifier(MODID, name);
        return Registry.register(Registries.SOUND_EVENT, identifier, PolymerSoundEvent.of(identifier, soundEvent));
    }

    public static void init() {}
}
