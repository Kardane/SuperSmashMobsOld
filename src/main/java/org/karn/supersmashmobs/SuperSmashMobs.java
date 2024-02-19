package org.karn.supersmashmobs;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.GameMode;
import org.karn.supersmashmobs.command.KitCommand;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.SkillRouter;
import org.karn.supersmashmobs.game.kit.KitRegistry;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.command.GameCommand;
import org.karn.supersmashmobs.command.HudCommand;
import org.karn.supersmashmobs.registry.SSMEffects;
import org.karn.supersmashmobs.registry.SSMSounds;

public class SuperSmashMobs implements ModInitializer {
    public static final String MODID = "supersmashmob";
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets(MODID);
        PolymerResourcePackUtils.markAsRequired();
        SSMAttributes.init();
        SSMEffects.init();
        SSMSounds.init();
        KitRegistry.init();
        System.out.println("SSB online!");
        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
            HudCommand.register(dispatcher);
            GameCommand.register(dispatcher);
            KitCommand.register(dispatcher);
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if(MainGame.isPlaying){
                handler.player.changeGameMode(GameMode.SPECTATOR);
            } else {
                handler.player.changeGameMode(GameMode.ADVENTURE);
                //handler.player.kill();
            }
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if(MainGame.isPlaying){
                if(MainGame.joinedPlayer.containsKey(handler.player)){
                    MainGame.joinedPlayer.remove(handler.player);
                }
                MainGame.updateGame(server);
            }
        });
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if(!player.isSpectator() && player.getMainHandStack().isOf(Items.NETHER_STAR)){
                SkillRouter.routeSmash(player);
                player.getMainHandStack().setCount(0);
            }
            return TypedActionResult.pass(player.getMainHandStack());
        });
    }
}
