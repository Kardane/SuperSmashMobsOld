package org.karn.supersmashmobs;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.world.GameMode;
import org.karn.supersmashmobs.command.KitCommand;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.kit.KitRegistry;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.command.GameCommand;
import org.karn.supersmashmobs.command.HudCommand;
import org.karn.supersmashmobs.registry.SSMEffects;

public class SuperSmashMobs implements ModInitializer {
    public static final String MODID = "supersmashmob";
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets(MODID);
        PolymerResourcePackUtils.markAsRequired();
        SSMAttributes.init();
        SSMEffects.init();
        KitRegistry.init();
        System.out.println("SSB online!");
        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
            HudCommand.register(dispatcher);
            GameCommand.register(dispatcher);
            KitCommand.register(dispatcher);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if(MainGame.getGameStatus()){
                handler.player.changeGameMode(GameMode.SPECTATOR);
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if(MainGame.getGameStatus()){
                MainGame.joinedPlayer.remove(handler.player);
                MainGame.updateGame(server);
            }
        });
    }
}
