package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.SlimeMossBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.WallSlimeMossBlock;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Block> RAFFLESIA = BLOCKS.register("rafflesia", RafflesiaBlock::new);
    public static final RegistryObject<Block> KABLOOM_BUSH = BLOCKS.register("kabloom_bush", KabloomBushBlock::new);

    public static final RegistryObject<Block> SLIME_MOSS = BLOCKS.register("slime_moss", () -> new SlimeMossBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
    public static final RegistryObject<Block> WALL_SLIME_MOSS = BLOCKS.register("wall_slime_moss", () -> new WallSlimeMossBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().sound(SoundType.PLANT).doesNotBlockMovement().notSolid()));
}