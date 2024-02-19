package org.karn.supersmashmobs.game;

import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.FireworkStarItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtByteArray;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.game.kit.AbstractKit;
import org.karn.supersmashmobs.game.kit.none.NoneSkillA;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.MessageSender;
import org.karn.supersmashmobs.util.misc;

import java.util.*;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

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
        Vec2f pos = new Vec2f((float) misc.randomInt(-100,100), (float) misc.randomInt(-100,100));
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
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "summon minecraft:firework_rocket "+pos.x+" "+y+" "+pos.y+" {Life:0,LifeTime:0,FireworksItem:{id:\"firework_rocket\",Count:1,tag:{Fireworks:{Explosions:[{Type:1,Trail:1b,Colors:[I;16711935]}]}}}}");
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "summon item "+pos.x+" "+y+" "+pos.y+" {NoGravity:1b,Glowing:1b,CustomNameVisible:1b,Health:10000,PickupDelay:0,Invulnerable:1b,CustomName:'{\"text\":\"스매시 크리스탈\",\"color\":\"yellow\",\"bold\":true,\"italic\":false}',Item:{id:\"minecraft:nether_star\",Count:1b,tag:{display:{Name:'{\"text\":\"스매시 크리스탈\",\"color\":\"yellow\",\"bold\":true,\"italic\":false}'}}}}");
                }
            };
            timer.schedule(task, 8*50);
        }
    }
}
