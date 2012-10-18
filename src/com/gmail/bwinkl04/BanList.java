package com.gmail.bwinkl04;

import java.io.File;
import java.io.FileReader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import au.com.bytecode.opencsv.CSVReader;



public class BanList extends JavaPlugin
{

	/*
	 * Test code for test branch.
	 * Testing GIT Branch.
	 */
	@Override
	public void onEnable()
	{
		getLogger().info("BanList is enabled");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("BanList is disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("BanList"))
		{
			try 
			{
				textOutput(sender); // if player types in /banlist, goto method textOutput
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public void textOutput(CommandSender sender) throws Exception
	{
		File file = new File("plugins/CommandBook/bans.csv"); // locates the file
		CSVReader bansList = new CSVReader(new FileReader(file)); // reads in the file
		
		String[] rowAsTokens; //string array for the individual rows
		
		/*
		 * Read in the rows, tokenize them, add them to a string array, and output only the fields we want.
		 */
		while ((rowAsTokens = bansList.readNext()) != null) 
		{
			sender.sendMessage(rowAsTokens[0] + " - " + rowAsTokens[2]);
		}
		bansList.close();
		
	}
}
