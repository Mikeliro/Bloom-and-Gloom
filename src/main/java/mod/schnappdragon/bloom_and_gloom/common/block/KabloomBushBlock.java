package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.entity.projectile.KabloomFruitEntity;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class KabloomBushBlock extends BushBlock implements IGrowable {
    protected static final VoxelShape AGE_0_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D);
    protected static final VoxelShape AGE_1_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 6.0D, 15.0D);
    protected static final VoxelShape AGE_2_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape AGE_3_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D);
    protected static final VoxelShape AGE_4_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D);
    protected static final VoxelShape AGE_5_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 12.0D, 15.0D);
    protected static final VoxelShape AGE_6_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
    protected static final VoxelShape AGE_7_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

    public KabloomBushBlock() {
        super(AbstractBlock.Properties
                .create(Material.PLANTS)
                .zeroHardnessAndResistance()
                .sound(SoundType.PLANT)
                .doesNotBlockMovement()
                .notSolid()
                .setAllowsSpawn((a,b,c,d) -> false)
        );

        this.setDefaultState(
                this.stateContainer.getBaseState()
                        .with(AGE, 0)
        );
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(AGE)) {
            case 1:
                return AGE_1_SHAPE;
            case 2:
                return AGE_2_SHAPE;
            case 3:
                return AGE_3_SHAPE;
            case 4:
                return AGE_4_SHAPE;
            case 5:
                return AGE_5_SHAPE;
            case 6:
                return AGE_6_SHAPE;
            case 7:
                return AGE_7_SHAPE;
            default:
                return AGE_0_SHAPE;
        }
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return true;
    }

    /*
     * Kabloom Bush Function Methods
     */

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity || entityIn instanceof ProjectileEntity || entityIn instanceof FallingBlockEntity || entityIn instanceof BoatEntity || entityIn instanceof TNTEntity || entityIn instanceof AbstractMinecartEntity) {
            if (entityIn instanceof LivingEntity && state.get(AGE) > 1)
                entityIn.setMotionMultiplier(state, new Vector3d(0.95F, 0.9D, 0.95F));
            if (state.get(AGE) == 7) {
                dropFruit(state, worldIn, pos, true);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!(state.get(AGE) == 7) && player.getHeldItem(handIn).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        } else if (state.get(AGE) == 7) {
            if (player.getHeldItem(handIn).getItem() == Items.SHEARS) {
                if (!worldIn.isRemote) {
                    spawnAsEntity(worldIn, pos, new ItemStack(BGItems.KABLOOM_FRUIT.get(), 1));
                    player.getHeldItem(handIn).damageItem(1, player, (playerIn) -> {
                        playerIn.sendBreakAnimation(handIn);
                    });
                    worldIn.setBlockState(pos, state.with(AGE, 3), 2);
                    worldIn.playSound(null, pos, BGSoundEvents.BLOCK_KABLOOM_BUSH_SHEAR.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
                }
            }
            else
                dropFruit(state, worldIn, pos, true);
            return ActionResultType.SUCCESS;
        }
        else
            return ActionResultType.FAIL;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        ItemStack held = player.getHeldItemMainhand();

        if (state.get(AGE) == 7 && !player.abilities.isCreativeMode) {
            if (held.getItem() != Items.SHEARS) {
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, held) == 0 || !(held.getItem() instanceof ToolItem))
                    dropFruit(state, worldIn, pos, false);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    private void dropFruit(BlockState state, World worldIn, BlockPos pos, boolean replaceBush) {
        if (replaceBush && !worldIn.isRemote) {
            worldIn.setBlockState(pos, state.with(AGE, 3), 2);
            worldIn.playSound(null, pos, BGSoundEvents.BLOCK_KABLOOM_BUSH_RUSTLE.get(), SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
        }

        worldIn.addEntity(new KabloomFruitEntity(worldIn, pos.getX() + 0.5F, pos.getY() + 0.6F, pos.getZ() + 0.5F));
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) <= 7;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (state.get(AGE) < 7 && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(5) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE, state.get(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
        else if (state.get(AGE) == 7 && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(10) == 0)) {
            dropFruit(state, worldIn, pos, true);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < 7;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(AGE, Math.min(7, state.get(AGE) + 1)), 2);
    }

    /*
     * Plant Method
     */

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    /*
     * Block Flammability Methods
     */

    @Override
    public int getFlammability(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 60;
    }

    /*
     * Pathfinding Method
     */

    @Nullable
    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return state.get(AGE) > 1 ? PathNodeType.DAMAGE_OTHER : null;
    }
}