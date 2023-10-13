package com.plr.sactweaker.common.config;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
public enum SwordSpecs {
    COSMIC_DAZZLE(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT)),
    GOLDEN_TUSK(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT)),
    ROUKANKEN(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT)),
    SCULK_CLEAVER(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT)),
    THE_OTHER_HALF(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT)),
    TIME_FLIES(DefaultSpecs.of(1561, 7.0, 1.6, 10, Items.COPPER_INGOT));

    private SwordSpecsSetting specsCache;

    private final DefaultSpecs specs;

    SwordSpecs(DefaultSpecs specs) {
        this.specs = specs;
    }

    public DefaultSpecs getDefaultSpecs() {
        return specs;
    }

    public SwordSpecsSetting getSpecs() {
        if (specsCache == null) specsCache = CommonConfig.specsMap.get(this);
        return specsCache;
    }

    public Supplier<Integer> uses() {
        return getSpecs().getMaxDamage();
    }

    public Supplier<Double> attackDamage() {
        return getSpecs().getAttackDamage();
    }

    public Supplier<Integer> getEnchantmentValue() {
        return getSpecs().getEnchantmentValue();
    }

    public Supplier<Ingredient> getRepairIngredient() {
        return () -> Ingredient.of(getSpecs().getRepairIngredients().get().stream()
                .map(ResourceLocation::tryParse)
                .filter(Objects::nonNull)
                .map(r -> Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(r)).getDefaultInstance())
                .filter(s -> !s.isEmpty()));
    }

    public record DefaultSpecs(int maxDamage, double attackDamage, double attackSpeed, int enchantmentValue,
                               Item... repairItems) {
        public static DefaultSpecs of(int maxDamage, double attackDamage, double attackSpeed, int enchantmentValue, Item... repairItems) {
            return new DefaultSpecs(maxDamage, attackDamage, attackSpeed, enchantmentValue, repairItems);
        }
    }
}
