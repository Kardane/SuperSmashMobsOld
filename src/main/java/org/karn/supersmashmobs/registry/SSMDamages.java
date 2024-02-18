package org.karn.supersmashmobs.registry;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class SSMDamages {
    RegistryKey<DamageType> TEST_SSM = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("test"));
}
