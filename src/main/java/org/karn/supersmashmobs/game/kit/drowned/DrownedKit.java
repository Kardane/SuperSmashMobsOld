package org.karn.supersmashmobs.game.kit.drowned;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.karn.supersmashmobs.game.kit.AbstractKit;
import org.karn.supersmashmobs.game.kit.AbstractSkill;
import org.karn.supersmashmobs.game.kit.none.*;
import org.karn.supersmashmobs.registry.SSMItems;

import java.util.ArrayList;
import java.util.List;

import static org.karn.supersmashmobs.SuperSmashMobs.MODID;

public class DrownedKit extends AbstractKit {
    public static String id = "drowned";
    public static String name = "드라운드";
    public static String desc = "드라운드입니다.";
    public static String icon = "\u5002";
    public static String smashicon = "\u5003";
    public static EntityType disguiseType = EntityType.DROWNED;

    public static Item SkillAItem = SSMItems.DROWNED_A;
    public static Item SkillBItem = SSMItems.DROWNED_B;
    public static Item SkillCItem = SSMItems.DROWNED_C;
    public static Item SkillDItem = SSMItems.DROWNED_D;
    public static Item SkillEItem = SSMItems.DROWNED_E;

    public static AbstractSkill SkillA = new DrownedSkillA();
    public static AbstractSkill SkillB = new DrownedSkillB();
    public static AbstractSkill SkillC = new DrownedSkillC();
    public static AbstractSkill SkillD = new DrownedSkillD();
    public static AbstractSkill SkillE = new DrownedSkillE();

    public static Integer Health = 300;
    public static Integer HealthRegen = 1;
    public static Integer KnockbackMultiplier = 100;
    public static Float AttackSpeed = 15f;
    public static Float AttackDamage = 10f;
    public static Float Speed = 0.1f;

    public static DustParticleEffect drownedDust = new DustParticleEffect(new Vector3f(0.0F, 0.75F, 1F), 1.0F);
    public DrownedKit(){
        super(id, name, desc, icon,smashicon,disguiseType,
                Health,HealthRegen,KnockbackMultiplier,AttackSpeed,AttackDamage,Speed,
                SkillAItem,SkillBItem,SkillCItem,SkillDItem,SkillEItem,
                SkillA,SkillB,SkillC,SkillD,SkillE);
    }

    //================================================================================================
    @Override public void onRevive(ServerPlayerEntity player) {

    }
    @Override public void onDeath(ServerPlayerEntity player) {

    }
    @Override public void onHurt(ServerPlayerEntity player) {

    }
    @Override public void onAttack(ServerPlayerEntity player) {

    }

    //================================================================================================

    public static class DrownedAItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public DrownedAItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/"+id+"/a"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class DrownedEItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public DrownedEItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/"+id+"/e"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class DrownedBItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public DrownedBItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/"+id+"/b"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class DrownedCItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public DrownedCItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/"+id+"/c"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }

    public static class DrownedDItem extends Item implements PolymerItem {
        private final PolymerModelData polymerModel;
        public DrownedDItem() {
            super(new Item.Settings().maxCount(1));
            this.polymerModel = PolymerResourcePackUtils.requestModel(Items.PAPER, new Identifier(MODID, "item/"+id+"/d"));
        }
        @Override public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.item();}
        @Override public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {return this.polymerModel.value();}
    }
}
