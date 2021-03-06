// <copyright file="ChocoCraftEventCommon.java">
// Copyright (c) 2013 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2013-06-08</date>
// <summary>Class for registering event listeners</summary>

package chococraft.common.events;

import chococraft.common.config.Constants;
import chococraft.common.config.ChocoCraftBlocks;
import chococraft.common.config.ChocoCraftItems;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.gui.GuiStarter;
import chococraft.common.helper.ChocoboPlayerHelper;
import chococraft.common.blocks.BlockGysahlStem;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;

public class ChocoCraftEventCommon
{

	@SubscribeEvent
	public void onSmackBlock(BlockEvent event) {
		//FMLLog.bigWarning(event.block.getUnlocalizedName()+" "+event.blockMetadata);
	}

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event)
	{
		if (event.block.equals(ChocoCraftBlocks.gysahlStemBlock))
		{
			if (((BlockGysahlStem) ChocoCraftBlocks.gysahlStemBlock).onBonemealUse(event.world, event.x, event.y, event.z))
			{
				event.setResult(Event.Result.ALLOW);
			}
		}
	}
    
    @SubscribeEvent
    public void onBurnDamage(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		if(event.source.equals(DamageSource.lava) 
    				|| event.source.equals(DamageSource.inFire) 
    				|| event.source.equals(DamageSource.onFire))
    		{
    			if(player.ridingEntity instanceof EntityChocoboPurple)
    			{
    				event.setCanceled(true);
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onPlayerAttackMount(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityChocobo)
    	{
    		EntityChocobo chocobo = (EntityChocobo)event.entity;
    		
    		if(null != chocobo.riddenByEntity && chocobo.riddenByEntity instanceof EntityPlayer)
    		{
    			EntityPlayer player = (EntityPlayer)chocobo.riddenByEntity;

        		if(event.source.equals(DamageSource.causePlayerDamage(player)))
        		{
        			event.setCanceled(true);
        		}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onPlayerAttackInventory(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityChocobo)
    	{
    		EntityChocobo chocobo = (EntityChocobo)event.entity;
    		if(chocobo.isTamed())
    		{
    			if(event.source.getEntity() instanceof EntityPlayer)
    			{
    				EntityPlayer player = (EntityPlayer)event.source.getEntity();

    				if(player.isSneaking())
    				{
    					event.setCanceled(true);
    				}
    			}    		
    		}
    	}
    }
    
    @SubscribeEvent
    public void onChocoboSuffocateInWall(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityChocobo)
    	{
    		if(event.source.equals(DamageSource.inWall))
    		{
    			event.setCanceled(true);        		
    		}
    	}
    }
    
    @SubscribeEvent
    public void chocoboDamageHandling(LivingHurtEvent event)
    {
    	if(event.entity instanceof EntityChocobo)
    	{
    		EntityChocobo chocobo = (EntityChocobo)event.entity;
    		chocobo.damageHandling();
    	}
    }
    
    @SubscribeEvent
    public void onChocopediaRightClick(PlayerInteractEvent event)
    {
    	if(event.action == Action.RIGHT_CLICK_AIR)
    	{
        	if(event.entity instanceof EntityPlayer)
        	{
        		EntityPlayer player = (EntityPlayer)event.entity;
        		ItemStack currentItem = player.getCurrentEquippedItem();
        		if(currentItem != null)
        		{        			
        			if(currentItem.getItem().equals(ChocoCraftItems.chocopediaItem))
        			{
        				GuiStarter.startChocopediaGui(null);
        				if(event.isCancelable())
        				{
        					event.setCanceled(true);
        				}
        			}
        		}
        	}
    	}
    }
    
    @SubscribeEvent
    public void onFeatherRightClick(PlayerInteractEvent event)
    {
    	if(event.action == Action.RIGHT_CLICK_BLOCK)
    	{
        	if(event.entity instanceof EntityPlayer)
        	{
        		EntityPlayer player = (EntityPlayer)event.entity;
        		ItemStack currentItem = player.getCurrentEquippedItem();
        		if(currentItem != null)
        		{        			
        			if(currentItem.getItem().equals(ChocoCraftItems.chocoboFeatherItem))
        			{
						if(!event.world.isRemote)
        				if(player.worldObj.getBlock(event.x, event.y, event.z) == Blocks.bookshelf)
        				{
           	        		player.worldObj.setBlockToAir(event.x, event.y, event.z);
           	        		ChocoboPlayerHelper.useCurrentItem(player);
           	        		ItemStack itemstack = new ItemStack(ChocoCraftItems.chocopediaItem, 1, 0);
							EntityItem entityItem = new EntityItem(player.worldObj, event.x, event.y, event.z, itemstack);
							player.worldObj.spawnEntityInWorld(entityItem);
        				}
        			}
    			}
    		}
    	}
    }
	
	@SubscribeEvent
	public void onSkeletonAndZombiDropEvent(LivingDropsEvent event)
	{
		if (this.isChocopediaDropEntity(event.entityLiving))
		{
			double d100 = Math.random() * 100;
			if (d100 < Constants.CHOCOPEDIA_MOB_DROP_RATE)
			{
				//TODO reenable after sorting the config file out..
//				event.entityLiving.dropItem(ChocoCraftItems.chocopediaItem, 1);
			}
		}
	}
	
	protected boolean isChocopediaDropEntity(EntityLivingBase entity)
	{
		return entity instanceof EntityZombie
				|| entity instanceof EntitySkeleton
				|| entity instanceof EntityWitch
				|| entity instanceof EntityVillager;
	}
}
