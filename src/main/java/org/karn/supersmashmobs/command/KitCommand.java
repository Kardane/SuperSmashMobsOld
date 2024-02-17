package org.karn.supersmashmobs.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.MainGame;
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
                            HudApi a = (HudApi) ctx.getSource().getEntity();
                            a.setKit(new NoneKit());
                            ctx.getSource().sendMessage(Text.of("Kit changed to None"));
                            return 1;
                        })
                )
        );
    }
}
