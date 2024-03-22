package org.karn.supersmashmobs.game;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.particle.Arc;
import org.karn.supersmashmobs.particle.ParticleDrawer;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.MessageSender;
import org.karn.supersmashmobs.util.Misc;

import java.util.*;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;
import static org.karn.supersmashmobs.hud.Hud.guiStyle;

public class SmashCrystal {
    public static void tick(MinecraftServer server){
        if(!MainGame.isPlaying) return;
        CommandBossBar bar = server.getBossBarManager().get(new Identifier(MODID,"crystal"));
        bar.setValue(bar.getValue()+1);
        bar.setVisible(true);
        if(bar.getValue() < bar.getMaxValue()){
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    tick(server);
                }
            };
            timer.schedule(task, 20*50);
        } else {
            bar.setValue(0);
            startSpawn(server);
        }
    }

    public static void startSpawn(MinecraftServer server){
        Vec2f pos = new Vec2f((float) Misc.randomInt(-100,100), (float) Misc.randomInt(-100,100));
        boolean isSolid = false;
        for (int i = 100; i > 0; i--) {
            BlockPos blockPos = new BlockPos((int) pos.x,i, (int) pos.y);
            if(server.getOverworld().getBlockState(blockPos).isSolidBlock(server.getOverworld(),blockPos)){
                isSolid = true;
            }
        }
        if (!isSolid) startSpawn(server);
        else {
            spawnFirework(server,pos,130);
            MessageSender.sendMsgAll(server, GameMessages.getCrystalMsg(pos));
        }
    }

    public static void spawnFirework(MinecraftServer server, Vec2f pos, int y){
        //if(!MainGame.isPlaying) return;
        BlockPos blockPos = new BlockPos((int) pos.x,y, (int) pos.y);
        server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "summon minecraft:firework_rocket "+pos.x+" "+y+" "+pos.y+" {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:0,Colors:[I;16711935]}]}}}}");
        if(!server.getOverworld().getBlockState(blockPos.add(0,-2,0)).isSolidBlock(server.getOverworld(),blockPos.add(0,-2,0)) && y >0){
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    spawnFirework(server,pos,y-1);
                }
            };
            timer.schedule(task, 6*50);
        } else {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    var pa1 = Arc.CircleDots(new Vec3d(pos.x,y,pos.y), 2, 110, 0.2);
                    var pa2 = Arc.CircleDots(new Vec3d(pos.x,y,pos.y), 2.25, 120, 0.2);
                    var pa3 = Arc.CircleDots(new Vec3d(pos.x,y,pos.y), 2.5, 130, 0.2);
                    var pa4 = Arc.CircleDots(new Vec3d(pos.x,y,pos.y), 2.75, 140, 0.2);
                    var pa5 = Arc.CircleDots(new Vec3d(pos.x,y,pos.y), 3, 150, 0.2);
                    ParticleDrawer.DotArrayDraw(server.getOverworld(),ParticleTypes.DRAGON_BREATH,pa1,true);
                    ParticleDrawer.DotArrayDraw(server.getOverworld(),ParticleTypes.DRAGON_BREATH,pa2,true);
                    ParticleDrawer.DotArrayDraw(server.getOverworld(),ParticleTypes.DRAGON_BREATH,pa3,true);
                    ParticleDrawer.DotArrayDraw(server.getOverworld(),ParticleTypes.DRAGON_BREATH,pa4,true);
                    ParticleDrawer.DotArrayDraw(server.getOverworld(),ParticleTypes.DRAGON_BREATH,pa5,true);
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "summon minecraft:firework_rocket "+pos.x+" "+y+" "+pos.y+" {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Trail:1b,Colors:[I;16711935]}]}}}}");
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "summon item "+pos.x+" "+y+" "+pos.y+" {NoGravity:1b,Glowing:1b,CustomNameVisible:1b,Health:10000,PickupDelay:0,Invulnerable:1b,CustomName:'{\"text\":\"스매시 크리스탈\",\"color\":\"yellow\",\"bold\":true,\"italic\":false}',Item:{id:\"minecraft:nether_star\",Count:1b,tag:{display:{Name:'{\"text\":\"스매시 크리스탈\",\"color\":\"yellow\",\"bold\":true,\"italic\":false}'}}}}");
                }
            };
            timer.schedule(task, 8*50);
            tick(server);
        }
    }

    public static void showCrystalCutScene(MinecraftServer server, PlayerEntity user){
        HudApi a = (HudApi) user;
        Text t1 = Text.empty().append(Text.translatable("space.-8")).append(Text.literal(a.getKit().getSmashicon()).setStyle(guiStyle));
        Text t2 = Text.empty().append(Text.translatable("space.8")).append(Text.literal(a.getKit().getSmashicon()).setStyle(guiStyle));
        CommandBossBar bar = server.getBossBarManager().add(new Identifier(MODID,"cutscene"),Text.empty());
        bar.setColor(BossBar.Color.GREEN);
        bar.addPlayers(server.getPlayerManager().getPlayerList());
        bar.setName(t1);

        Timer timer = new Timer();
        TimerTask s1 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s2 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s3 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s4 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s5 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s6 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s7 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s8 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s9 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s10 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s11 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s12 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s13 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s14 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s15 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s16 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s17 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s18 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask s19 = new TimerTask() {
            public void run() {
                bar.setName(t2);
            }
        };
        TimerTask s20 = new TimerTask() {
            public void run() {
                bar.setName(t1);
            }
        };
        TimerTask slast = new TimerTask() {
            public void run() {
                server.getPlayerManager().getPlayerList().forEach(bar::removePlayer);
                server.getBossBarManager().remove(bar);
            }
        };
        timer.schedule(s1, 50);
        timer.schedule(s2, 50*2);
        timer.schedule(s3, 50*3);
        timer.schedule(s4, 50*4);
        timer.schedule(s5, 50*5);
        timer.schedule(s6, 50*6);
        timer.schedule(s7, 50*7);
        timer.schedule(s8, 50*8);
        timer.schedule(s9, 50*9);
        timer.schedule(s10, 50*10);
        timer.schedule(s11, 50*11);
        timer.schedule(s12, 50*12);
        timer.schedule(s13, 50*13);
        timer.schedule(s14, 50*14);
        timer.schedule(s15, 50*15);
        timer.schedule(s16, 50*16);
        timer.schedule(s17, 50*17);
        timer.schedule(s18, 50*18);
        timer.schedule(s19, 50*19);
        timer.schedule(s20, 50*20);
        timer.schedule(slast, 50*21);
    }
}
