package org.karn.supersmashmobs.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.kit.AbstractKit;
import org.karn.supersmashmobs.game.kit.drowned.DrownedKit;
import org.karn.supersmashmobs.game.kit.none.NoneKit;

public class KitCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ssb_kit")
                .requires(source -> source.hasPermissionLevel(4))
                        .then(CommandManager.literal("desc")
                                .executes(ctx->{
                                    HudApi a = (HudApi) ctx.getSource().getEntity();
                                    ctx.getSource().sendMessage(Text.of(a.getKit().id));
                                    ctx.getSource().sendMessage(Text.of(a.getKit().name));
                                    ctx.getSource().sendMessage(Text.of(a.getKit().desc));
                                    ctx.getSource().sendMessage(Text.of(String.valueOf(a.getKit().disguiseType)));
                                    return 1;
                                })
                        )
                .then(CommandManager.literal("none")
                        .executes(ctx->{
                            var player = ctx.getSource().getPlayerOrThrow();
                            changeKit(player, new NoneKit());
                            return 1;
                        })
                )
                .then(CommandManager.literal("drowned")
                        .executes(ctx->{
                            var player = ctx.getSource().getPlayerOrThrow();
                            changeKit(player, new DrownedKit());
                            return 1;
                        })
                )
        );
    }

    public static int changeKit(PlayerEntity player, AbstractKit kit) {
        if(MainGame.isPlaying) return 1;
        HudApi a = (HudApi) player;
        a.setKit(kit);
        player.sendMessage(Text.literal("선택: " + kit.name).formatted(Formatting.GOLD).formatted(Formatting.BOLD));
        return 2;
    }
}
