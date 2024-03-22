package org.karn.supersmashmobs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.MainGame;
import org.karn.supersmashmobs.game.SmashCrystal;
import org.karn.supersmashmobs.util.MessageSender;

public class GameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ssb")
                .requires(source -> source.hasPermissionLevel(4))
                .then(CommandManager.literal("start")
                        .executes(ctx->{
                            MainGame.prepareGame(ctx.getSource().getServer());
                            return 1;
                        })
                )
                .then(CommandManager.literal("stop")
                        .executes(ctx->{
                            MainGame.stopGame(ctx.getSource().getServer());
                            return 1;
                        })
                )
                .then(CommandManager.literal("getPlayer")
                        .executes(ctx->{
                            ctx.getSource().sendMessage(Text.literal(MainGame.getJoinedPlayer(ctx.getSource().getServer())));
                            return 1;
                        })
                )
                .then(CommandManager.literal("status")
                        .executes(ctx->{
                            ctx.getSource().sendMessage(Text.literal(String.valueOf(MainGame.getGameStatus())));
                            return 1;
                        })
                )
                .then(CommandManager.literal("currentPlayerStatus")
                        .executes(ctx->{
                            MainGame.getPlayerStatus(ctx.getSource().getServer());
                            return 1;
                        })
                )
                .then(CommandManager.literal("summonCrystal")
                        .executes(ctx->{
                            SmashCrystal.startSpawn(ctx.getSource().getServer());
                            return 1;
                        })
                )
                .then(CommandManager.literal("debug")
                        .executes(ctx->{
                            MainGame.isDebug = !MainGame.isDebug;
                            ctx.getSource().sendMessage(Text.literal("Debug mode: " + MainGame.isDebug));
                            return 1;
                        })
                )
                .then(CommandManager.literal("setCrystalTime")
                        .then(CommandManager.argument("time", IntegerArgumentType.integer(1))
                                .executes(ctx->{
                                    MainGame.FinalSmashTime = IntegerArgumentType.getInteger(ctx, "time");
                                    MessageSender.sendMsgAll(ctx.getSource().getServer(), Text.literal("다음 스매시 크리스탈 생성 대기시간: " + MainGame.FinalSmashTime));
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("bots")
                        .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                .executes(ctx->{
                                    for (int i = 0; i < IntegerArgumentType.getInteger(ctx, "count"); i++) {
                                        ctx.getSource().getServer().getCommandManager().executeWithPrefix(ctx.getSource().withSilent(), "player bot-" +i+" spawn");
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }
}
