// <copyright file="ChocoboPlayerTracker.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-12-16</date>
// <summary>Player tracker for login and logoff events</summary>

package chococraft.common.events;

import chococraft.common.config.Constants;
import chococraft.common.network.PacketRegistry;
import chococraft.common.network.clientSide.ChocoboLocalSetupUpdate;
import chococraft.common.network.clientSide.ChocoboSetupUpdate;
import chococraft.common.utils.UpdateChecker;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.Validate;


public class ChocoboPlayerTracker
{

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		this.sendSetupUpdate((EntityPlayerMP)event.player);

		try {
			UpdateChecker.VersionInfo latestVersionInfo = UpdateChecker.getResponse().versions.get(0);

			if(!UpdateChecker.isVersionHigher(latestVersionInfo.modversion, Constants.TCC_VERSION))
				return;

			ChatStyle styleUnderlined = new ChatStyle().setUnderlined(true).setColor(EnumChatFormatting.GOLD);
			ChatStyle style = new ChatStyle().setColor(EnumChatFormatting.GOLD);

			IChatComponent text = new ChatComponentText("An update is avilable for Chococraft!, Latest version: "+latestVersionInfo.modversion).setChatStyle(styleUnderlined);
			event.player.addChatComponentMessage(text);
			text = new ChatComponentText("Changes:").setChatStyle(style);
			event.player.addChatComponentMessage(text);

			for(String line : latestVersionInfo.changes) {
				text = new ChatComponentText(line).setChatStyle(style);
				event.player.addChatComponentMessage(text);
			}
			text = new ChatComponentText("Get the latest version at http://minecraft.curseforge.com/mc-mods/225280-chococraft").setChatStyle(style);
			event.player.addChatComponentMessage(text);

		}catch (NullPointerException npe) {
			System.out.println("npe choco");
			//prob failed to connect
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
	{
		if (event.player == null || ((EntityPlayerMP)event.player).playerNetServerHandler == null) return;
		//Validate.notNull(event.player, "Player is null"); maybe later?
		this.sendLocalSetupUpdate((EntityPlayerMP)event.player);
	}

	
	protected void sendSetupUpdate(EntityPlayerMP player)
	{
		if (!player.worldObj.isRemote)
		{
			ChocoboSetupUpdate packet = new ChocoboSetupUpdate();
			PacketRegistry.INSTANCE.sendTo(packet, player);
		}		
	}
	
	protected void sendLocalSetupUpdate(EntityPlayerMP player)
	{
		if (!player.worldObj.isRemote)
		{
			ChocoboLocalSetupUpdate packet = new ChocoboLocalSetupUpdate();
			PacketRegistry.INSTANCE.sendTo(packet, player);
		}		
	}
}