package com.plr.sactweaker.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SwordSpecsSetting {
    private final ForgeConfigSpec.IntValue maxDamage;
    private final ForgeConfigSpec.DoubleValue attackDamage;
    private final ForgeConfigSpec.DoubleValue attackSpeed;
    private final ForgeConfigSpec.IntValue enchantmentValue;
    private final ForgeConfigSpec.ConfigValue<List<String>> repairIngredients;

    SwordSpecsSetting(
            ForgeConfigSpec.Builder cfg, String name, SwordSpecs.DefaultSpecs specs
    ) {
        final String nameWithoutUnderscore = name.replace('_', ' ');
        final String name_lower = name.toLowerCase(Locale.ROOT);
        cfg.push(nameWithoutUnderscore + " Specs");
        this.maxDamage = cfg.comment(nameWithoutUnderscore + "'s max damage")
                .defineInRange(name_lower + "_max_damage", specs.maxDamage(), 1, Integer.MAX_VALUE);
        this.attackDamage = cfg.comment(nameWithoutUnderscore + "'s attack damage bonus").
                defineInRange(name_lower + "_attack_damage", specs.attackDamage(), .0, Double.MAX_VALUE);
        this.attackSpeed = cfg.comment(nameWithoutUnderscore + "'s attack speed")
                .defineInRange(name_lower + "_attack_speed", specs.attackSpeed(), .0, Double.MAX_VALUE);
        this.enchantmentValue = cfg.comment(nameWithoutUnderscore + "'s enchantment value")
                .defineInRange(name_lower + "_enchantment_value", specs.enchantmentValue(), 0, Integer.MAX_VALUE);
        this.repairIngredients = cfg.comment(nameWithoutUnderscore + "'s repair ingredients")
                .define(name_lower + "_repair_ingredients", Arrays.stream(specs.repairItems())
                        .map(i -> Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).toString()).toList());
        cfg.pop();
    }

    public ForgeConfigSpec.IntValue getMaxDamage() {
        return maxDamage;
    }

    public ForgeConfigSpec.DoubleValue getAttackDamage() {
        return attackDamage;
    }

    public ForgeConfigSpec.DoubleValue getAttackSpeed() {
        return attackSpeed;
    }

    public ForgeConfigSpec.IntValue getEnchantmentValue() {
        return enchantmentValue;
    }

    public ForgeConfigSpec.ConfigValue<List<String>> getRepairIngredients() {
        return repairIngredients;
    }
}
