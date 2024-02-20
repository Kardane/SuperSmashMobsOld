package org.karn.supersmashmobs.game;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerPropertyUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.karn.supersmashmobs.registry.SSMSounds;
import org.karn.supersmashmobs.util.misc;

import java.util.*;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class BGMManager {
    public static class BGMInfo {
        public int duration;
        public String name;
        public BGMInfo(int duration, String name){
            this.duration = duration;
            this.name = name;
        }
    }
    public static Map<SoundEvent,BGMInfo> bgmList = new HashMap<>();

    public static Map<SoundEvent,BGMInfo> shuffleMap() {
        bgmList.put(SSMSounds.BGM_FALCON_1, new BGMInfo(60*4+9, "F-Zero - Big Blue"));
        bgmList.put(SSMSounds.BGM_STARFOX_1, new BGMInfo(60*2+52, "StarFox - Corneria"));
        bgmList.put(SSMSounds.BGM_METAKNIGHT_1, new BGMInfo(60*4+9, "Kirby - Meta Knight's Revenge"));
        bgmList.put(SSMSounds.BGM_TERRY_1, new BGMInfo(60*5+7, "FatalFury - Kurikinton"));
        bgmList.put(SSMSounds.BMG_KINGKLUL_1, new BGMInfo(60*4+49, "DonkeyKong - Gang-Plank Galleon"));
        bgmList.put(SSMSounds.BGM_ZELDA_1, new BGMInfo(60*3+48, "Zelda - Gerudo Valley"));
        bgmList.put(SSMSounds.BGM_ZELDA_2, new BGMInfo(60*3+30, "Zelda - Kass's Theme"));
        bgmList.put(SSMSounds.BGM_STEVE_1, new BGMInfo(60*5+10, "Minecraft - Toys on a Tear"));
        bgmList.put(SSMSounds.BGM_STEVE_2, new BGMInfo(60*5+20, "Minecraft - Glide"));
        bgmList.put(SSMSounds.BGM_STEVE_3, new BGMInfo(60*4+52, "Minecraft - The Arch-Illager"));
        List<Map.Entry<SoundEvent,BGMInfo>> entryList = new ArrayList<>(bgmList.entrySet());
        Collections.shuffle(entryList);

        Map<SoundEvent, BGMInfo> shuffledMap = new LinkedHashMap<>();
        for (Map.Entry<SoundEvent,BGMInfo> entry : entryList) {
            shuffledMap.put(entry.getKey(), entry.getValue());
        }
        bgmList = shuffledMap;
        return shuffledMap;
    }

    public static void playBGM(MinecraftServer server, Map<SoundEvent,BGMInfo> bgmList, UUID uuid) {
        if (!MainGame.isPlaying || uuid != MainGame.gameUUID) return;
        System.out.println(bgmList.keySet().iterator().next().getId());
        misc.playSoundToAll(server, bgmList.keySet().iterator().next(), SoundCategory.VOICE, 1,1);
        testToast(server, Text.of("\uD83C\uDF9C "+bgmList.values().iterator().next().name), Text.of("desc"));
        int duration = bgmList.values().iterator().next().duration;
        if(bgmList.size() > 1){
            bgmList.remove(bgmList.keySet().iterator().next());
        } else {
            bgmList = shuffleMap();
        }
        Map<SoundEvent, BGMInfo> finalBgmList = bgmList;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                playBGM(server, finalBgmList, uuid);
            }
        };
        timer.schedule(task, duration*1000+50);
    }

    public static void testToast(MinecraftServer server, Text title, Text description){
        Identifier test = new Identifier(MODID,"testtoast");
        var advancement = Advancement.Builder.create()
                .display(Items.MUSIC_DISC_CAT, title, description, null, AdvancementFrame.TASK,true,true,true)
                .criterion("custom_criterion", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions()))
                .build(test);
        var progress = new AdvancementProgress();
        progress.init(AdvancementRequirements.allOf(Set.of("custom_criterion")));
        progress.obtain("custom_criterion");
        server.getPlayerManager().getPlayerList().forEach(player -> {
            player.networkHandler.sendPacket(new AdvancementUpdateS2CPacket(false, Set.of(advancement), Collections.emptySet(), Map.of(test, progress)));
            player.networkHandler.sendPacket(new AdvancementUpdateS2CPacket(false, Collections.emptySet(), Set.of(test), Collections.emptyMap()));
        });
    }
}
