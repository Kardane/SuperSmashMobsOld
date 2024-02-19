package org.karn.supersmashmobs.game;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.border.WorldBorder;
import org.karn.supersmashmobs.api.HudApi;
import org.karn.supersmashmobs.registry.SSMAttributes;
import org.karn.supersmashmobs.registry.SSMSounds;
import org.karn.supersmashmobs.util.GameMessages;
import org.karn.supersmashmobs.util.MessageSender;
import org.karn.supersmashmobs.util.misc;

import javax.management.Attribute;
import java.time.LocalDate;
import java.util.*;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class MainGame {
    public static boolean isPlaying = false;
    public static Map<PlayerEntity,Integer> joinedPlayer = new HashMap<>();
    public static int maxLife = 3;
    public static Vec2f ringPos = new Vec2f(0,0);
    public static boolean getGameStatus(){
        return isPlaying;
    }
    public static UUID gameUUID = null;

    public static void prepareGame(MinecraftServer server){
        Timer count5 = new Timer();
        TimerTask task5 = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_5, SoundCategory.MASTER,2,1F);
                MessageSender.sendTitleAll(server, Text.literal("5").formatted(Formatting.DARK_GREEN), Text.literal("가만히 계세요"));
            }
        };
        count5.schedule(task5, 20*50);

        TimerTask task4 = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_4, SoundCategory.MASTER,2,1F);
                MessageSender.sendTitleAll(server, Text.literal("4").formatted(Formatting.GREEN),Text.literal("가만히 계세요"));
            }
        };
        count5.schedule(task4, 40*50);

        TimerTask task3 = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_3, SoundCategory.MASTER,2,1F);
                MessageSender.sendTitleAll(server, Text.literal("3").formatted(Formatting.YELLOW), Text.literal("가만히 계세요"));
            }
        };
        count5.schedule(task3, 60*50);

        TimerTask task2 = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_2, SoundCategory.MASTER,2,1F);
                MessageSender.sendTitleAll(server, Text.literal("2").formatted(Formatting.GOLD), Text.literal("가만히 계세요"));
            }
        };
        count5.schedule(task2, 80*50);

        TimerTask task1 = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_1, SoundCategory.MASTER,2,1F);
                MessageSender.sendTitleAll(server, Text.literal("1").formatted(Formatting.RED), Text.literal("가만히 계세요"));
            }
        };
        count5.schedule(task1, 100*50);

        TimerTask prepareTask = new TimerTask() {
            public void run() {
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_READY, SoundCategory.MASTER,2,1F);
            }
        };
        count5.schedule(prepareTask, 115*50);

        TimerTask task = new TimerTask() {
            public void run() {
                startGame(server);
                BGMManager.playBGM(server,BGMManager.shuffleMap(BGMManager.makeBGMList()), gameUUID);
                misc.playSoundToAll(server, SSMSounds.COUNTDOWN_GO, SoundCategory.MASTER,2,1F);
            }
        };
        count5.schedule(task, 130*50);
    }

    public static void startGame(MinecraftServer server){
        gameUUID = UUID.randomUUID();
        WorldBorder border = server.getOverworld().getWorldBorder();
        border.setCenter(ringPos.x,ringPos.y);
        border.setDamagePerBlock(0);
        border.setSafeZone(0);
        border.setWarningBlocks(5);
        border.setMaxRadius(1000);
        CommandBossBar bar = server.getBossBarManager().add(new Identifier(MODID,"crystal"),Text.literal("스매시 크리스탈 재생성"));
        bar.setColor(BossBar.Color.PINK);
        bar.setValue(0);
        bar.setMaxValue(300);
        bar.addPlayers(server.getPlayerManager().getPlayerList());
        setGamerule(server);
        setPlayerLife(server);
        System.out.println(System.currentTimeMillis());
        misc.spreadPlayers(server,joinedPlayer,ringPos,16,80,60);
        System.out.println(System.currentTimeMillis());
        server.getPlayerManager().getPlayerList().forEach(p->{
            p.changeGameMode(GameMode.ADVENTURE);
            HudApi a = (HudApi) p;
            a.setSkillCoolA(100);
            a.setSkillCoolB(100);
            a.setSkillCoolC(100);
            a.setSkillCoolD(100);
            p.getAttributeInstance(SSMAttributes.ATTACK_DMG).setBaseValue(a.getKit().AttackDamage);
            p.getAttributeInstance(SSMAttributes.ATTACK_SPEED).setBaseValue(a.getKit().AttackSpeed);
            p.getAttributeInstance(SSMAttributes.HEALTH_REGEN).setBaseValue(a.getKit().HealthRegen);
            p.getAttributeInstance(SSMAttributes.KNOCKBACK_TAKEN).setBaseValue(a.getKit().KnockbackMultiplier);
            //p.getAttributeInstance(SSMAttributes.PROTECTION).setBaseValue(0);
            p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(a.getKit().Health);
            p.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(a.getKit().Speed);
            p.heal(10000);
            p.clearStatusEffects();
            p.networkHandler.sendPacket(new StopSoundS2CPacket(null, null));
            p.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.MASTER,1,1F);
        });
        MessageSender.sendMsgAll(server, GameMessages.getGameStartMsg());
        isPlaying = true;
        updateGame(server);
        SmashCrystal.tick(server);
        //tickGame(server);
    }

    public static void stopGame(MinecraftServer server){
        isPlaying = false;
        WorldBorder border = server.getOverworld().getWorldBorder();
        border.setCenter(ringPos.x,ringPos.y);
        border.setMaxRadius(1000);
        if(server.getBossBarManager().get(new Identifier(MODID,"crystal")) != null){
            server.getBossBarManager().get(new Identifier(MODID,"crystal")).clearPlayers();
            server.getBossBarManager().remove(server.getBossBarManager().get(new Identifier(MODID,"crystal")));
        }

        MessageSender.sendMsgAll(server,GameMessages.getGameStopMsg());
        server.getPlayerManager().getPlayerList().forEach(p->{
            p.networkHandler.sendPacket(new StopSoundS2CPacket(null, null));
            p.heal(10000);
            p.clearStatusEffects();
            p.changeGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.kill();
        });
        joinedPlayer = new HashMap<>();
        misc.playSoundToAll(server, SSMSounds.GAMESET, SoundCategory.MASTER,2,1F);
    }

    public static void updateGame(MinecraftServer server){
        var playing = 0;
        PlayerEntity winner = null;
        for (var a:joinedPlayer.entrySet()) {
            if(a.getValue() > 0) {
                playing++;
                winner = a.getKey();
            }
        }
        if(playing == 1){
            winner.playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1.5F);
            MessageSender.sendTitleAll(server,GameMessages.getWinnerMsg(winner),Text.literal("ㅊㅊ"));
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    stopGame(server);
                }
            };
            timer.schedule(task, 100*50);
        }
    }

    //=========================

    public static void tickGame(MinecraftServer server){
        var playing = 0;
        PlayerEntity winner = null;
        for (var a:joinedPlayer.entrySet()) {
            if(a.getValue() > 0) {
                playing++;
                winner = a.getKey();
            }
        }
        if(playing == 1){
            MessageSender.sendTitleAll(server,GameMessages.getWinnerMsg(winner),Text.literal("ㅊㅊ"));
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    stopGame(server);
                }
            };
            timer.schedule(task, 100*50);
            return;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                tickGame(server);
            }
        };
        timer.schedule(task, 50);
    }

    public static String getJoinedPlayer(MinecraftServer server){
        if(joinedPlayer == null){
            return "none";
        } else return joinedPlayer.toString();
    }

    public static void getPlayerStatus(MinecraftServer server){
        System.out.println("==========");
        for (var a:joinedPlayer.entrySet()) {
            System.out.println(a.getKey().getEntityName() + ":"+a.getValue());
        }
        System.out.println("==========");
    }

    public static void setPlayerLife(MinecraftServer server){
        PlayerManager manager = server.getPlayerManager();
        manager.getPlayerList().forEach(p->{
            joinedPlayer.put(p,maxLife);
        });
    }

    public static void setGamerule(MinecraftServer server){
        GameRules rules = server.getGameRules();
        rules.get(GameRules.NATURAL_REGENERATION).set(false,server);
        rules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false,server);
        rules.get(GameRules.DO_WEATHER_CYCLE).set(false,server);
        rules.get(GameRules.DO_IMMEDIATE_RESPAWN).set(true,server);
        rules.get(GameRules.DO_TILE_DROPS).set(false,server);
        rules.get(GameRules.DO_ENTITY_DROPS).set(false,server);
        rules.get(GameRules.DO_MOB_SPAWNING).set(false,server);
        rules.get(GameRules.DO_MOB_GRIEFING).set(false,server);
        rules.get(GameRules.ANNOUNCE_ADVANCEMENTS).set(false,server);
        rules.get(GameRules.COMMAND_BLOCK_OUTPUT).set(false,server);
        rules.get(GameRules.DO_FIRE_TICK).set(false,server);
        rules.get(GameRules.RANDOM_TICK_SPEED).set(0,server);
        rules.get(GameRules.KEEP_INVENTORY).set(true,server);
        rules.get(GameRules.SPAWN_RADIUS).set(0,server);
        rules.get(GameRules.FALL_DAMAGE).set(false,server);
        rules.get(GameRules.SPECTATORS_GENERATE_CHUNKS).set(false,server);
        rules.get(GameRules.LOG_ADMIN_COMMANDS).set(false,server);
        rules.get(GameRules.SHOW_DEATH_MESSAGES).set(false,server);
    }
}
