package org.karn.supersmashmobs.game;

import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerPropertyUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.karn.supersmashmobs.registry.SSMSounds;
import org.karn.supersmashmobs.util.misc;

import java.util.*;

public class BGMManager {
    public static Map<SoundEvent,Integer> bgmList = new HashMap<>();
    public static Map<SoundEvent,Integer> makeBGMList() {
        bgmList.clear();
        bgmList.put(SSMSounds.BGM_FALCON_1, 60*4+9);
        bgmList.put(SSMSounds.BGM_STARFOX_1, 60*2+52);
        bgmList.put(SSMSounds.BGM_METAKNIGHT_1, 60*4+9);
        bgmList.put(SSMSounds.BGM_TERRY_1, 60*5+7);
        bgmList.put(SSMSounds.BMG_KINGKLUL_1, 60*4+49);
        bgmList.put(SSMSounds.BGM_ZELDA_1, 60*3+51);
        bgmList.put(SSMSounds.BGM_ZELDA_2, 60*3+38);
        bgmList.put(SSMSounds.BGM_STEVE_1, 60*5+10);
        bgmList.put(SSMSounds.BGM_STEVE_2, 60*5+20);
        bgmList.put(SSMSounds.BGM_STEVE_3, 60*4+52);
        return bgmList;
    }

    public static Map<SoundEvent,Integer> shuffleMap(Map<SoundEvent,Integer> map) {
        List<Map.Entry<SoundEvent,Integer>> entryList = new ArrayList<>(map.entrySet());
        Collections.shuffle(entryList);

        Map<SoundEvent, Integer> shuffledMap = new LinkedHashMap<>();
        for (Map.Entry<SoundEvent,Integer> entry : entryList) {
            shuffledMap.put(entry.getKey(), entry.getValue());
        }
        bgmList = shuffledMap;
        return shuffledMap;
    }

    public static void playBGM(MinecraftServer server, Map<SoundEvent,Integer> bgmList, UUID uuid) {
        if (!MainGame.isPlaying || uuid != MainGame.gameUUID) return;
        System.out.println(bgmList.keySet().iterator().next().getId());
        misc.playSoundToAll(server, bgmList.keySet().iterator().next(), SoundCategory.VOICE, 1,1);
        server.getPlayerManager().getPlayerList().forEach(player -> {
        });
        if(bgmList.size() > 1){
            bgmList.remove(bgmList.keySet().iterator().next());
        } else {
            bgmList = shuffleMap(bgmList);
        }
        Map<SoundEvent, Integer> finalBgmList = bgmList;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                playBGM(server, finalBgmList, uuid);
            }
        };
        timer.schedule(task, bgmList.values().iterator().next()*1000);
    }
}
