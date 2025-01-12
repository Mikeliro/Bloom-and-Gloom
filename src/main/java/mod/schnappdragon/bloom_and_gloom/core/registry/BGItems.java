package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.item.KabloomFruitItem;
import mod.schnappdragon.bloom_and_gloom.common.item.KabloomSeedsItem;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Item> RAFFLESIA_SEED = ITEMS.register("rafflesia_seed",
            () -> new BlockNamedItem(BGBlocks.RAFFLESIA.get(), new Item.Properties().group(ItemGroup.MATERIALS)));

    public static final RegistryObject<Item> KABLOOM_SEEDS = ITEMS.register("kabloom_seeds",
            () -> new KabloomSeedsItem(BGBlocks.KABLOOM_BUSH.get(), new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> KABLOOM_FRUIT = ITEMS.register("kabloom_fruit",
            () -> new KabloomFruitItem((new Item.Properties()).maxStackSize(16).group(ItemGroup.MISC)));

    public static final RegistryObject<Item> SLIME_FERN = ITEMS.register("slime_fern", () -> new WallOrFloorItem(BGBlocks.SLIME_FERN.get(), BGBlocks.WALL_SLIME_FERN.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
}