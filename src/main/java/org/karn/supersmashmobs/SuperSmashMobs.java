package org.karn.supersmashmobs;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.karn.supersmashmobs.command.KitCommand;
import org.karn.supersmashmobs.game.kit.KitRegistry;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.command.GameCommand;
import org.karn.supersmashmobs.command.HudCommand;

public class SuperSmashMobs implements ModInitializer {
    public static final String MODID = "supersmashmob";
    @Override
    public void onInitialize() {
        PolymerResourcePackUtils.addModAssets(MODID);
        PolymerResourcePackUtils.markAsRequired();
        SSMAttributes.init();
        KitRegistry.init();
        System.out.println("SSB online!");
        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, ignored1) -> {
            HudCommand.register(dispatcher);
            GameCommand.register(dispatcher);
            KitCommand.register(dispatcher);
        });
    }
}
