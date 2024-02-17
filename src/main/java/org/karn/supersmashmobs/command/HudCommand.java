package org.karn.supersmashmobs.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.karn.supersmashmobs.api.HudApi;

public class HudCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("hudtest")
                .requires(source -> source.hasPermissionLevel(4))
                .then(CommandManager.literal("skillA")
                        .then(CommandManager.argument("num", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    HudApi a = (HudApi) ctx.getSource().getPlayerOrThrow();
                                    a.setSkillCoolA(IntegerArgumentType.getInteger(ctx,"num"));
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("skillB")
                        .then(CommandManager.argument("num", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    HudApi a = (HudApi) ctx.getSource().getPlayerOrThrow();
                                    a.setSkillCoolB(IntegerArgumentType.getInteger(ctx,"num"));
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("skillC")
                        .then(CommandManager.argument("num", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    HudApi a = (HudApi) ctx.getSource().getPlayerOrThrow();
                                    a.setSkillCoolC(IntegerArgumentType.getInteger(ctx,"num"));
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("skillD")
                        .then(CommandManager.argument("num", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    HudApi a = (HudApi) ctx.getSource().getPlayerOrThrow();
                                    a.setSkillCoolD(IntegerArgumentType.getInteger(ctx,"num"));
                                    return 1;
                                })
                        )
                )
                .then(CommandManager.literal("skill")
                        .then(CommandManager.argument("num", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    HudApi a = (HudApi) ctx.getSource().getPlayerOrThrow();
                                    a.setSkillCoolA(IntegerArgumentType.getInteger(ctx,"num"));
                                    a.setSkillCoolB(IntegerArgumentType.getInteger(ctx,"num"));
                                    a.setSkillCoolC(IntegerArgumentType.getInteger(ctx,"num"));
                                    a.setSkillCoolD(IntegerArgumentType.getInteger(ctx,"num"));
                                    return 1;
                                })
                        )
                )
        );
    }
}
