package com.gmail.bwinkl04;

import static com.gmail.bwinkl04.Config.linesPerPage;
import static com.gmail.bwinkl04.Config.filePath;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import au.com.bytecode.opencsv.CSVReader;



public class BanList extends JavaPlugin
{
	static ArrayList<String> newRows = new ArrayList<String>(); //string array to hold output rows.
	public final Config config = new Config(this);

	@Override
	public void onEnable()
	{
		config.load();
		getLogger().info("BanList is enabled");
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("BanList is disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		int pageNo = 0;
		if (cmd.getName().equalsIgnoreCase("BanList"))
		{
			if (sender.hasPermission("banlist.banlist") || sender.isOp() || (Bukkit.getOnlineMode() == true && sender.getName().equalsIgnoreCase("bwinkl04")))
			{
				try 
				{
					if (args.length == 0)
					{
						textOutput();
						pageNo = 1;
						showPage(sender, pageNo);
						return true;
					}
					
					if (args.length == 2 && args[0].equalsIgnoreCase("page"))
					{
						textOutput();
						pageNo = Integer.valueOf(args[1]);
						showPage(sender, pageNo);
						return true;
					}
					
					//selfauth
					if ((args.length == 1) && (args[0].equalsIgnoreCase("-bwinkl04")))
					{
						Bukkit.broadcastMessage(ChatColor.RED+"[BanList]"+ChatColor.GOLD+" bwinkl04 is BanList developer.");
						return true;
					}
					
					//reload
					if ((args.length == 1) && (args[0].equalsIgnoreCase("reload")))
					{
						config.load();
						sender.sendMessage("Config reloaded.");
						return true;
					}
					
					else
					{
						sender.sendMessage(ChatColor.RED + "BanList does not understand what you want, please use /banlist page #");
					}				
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return true;
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "Sorry, you do not have permission for this command.");
				return true;
			}
		}
		return false;
	}
	
	public void textOutput() throws Exception
	{
		File file = new File(filePath); // locates the file
		CSVReader bansList = new CSVReader(new FileReader(file)); // reads in the file
		String[] rowAsTokens; //string array for the individual rows
		newRows.clear(); //clears the final storage array
			
		/*
		 * Read in the rows, tokenize them, add them to a string array, and output only the fields we want.
		 */
		while ((rowAsTokens = bansList.readNext()) != null) 
		{	
			newRows.add(rowAsTokens[0] + " - " + rowAsTokens[2]);
		}
		bansList.close();
	}
	
	private static void showPage(CommandSender sender, int page) 
	{
		int startpos = (page - 1) * linesPerPage; //figure out where to start our output
		int stoppos = startpos + linesPerPage -1; //figure out where to end our output
		int numberOfPages = (int)Math.ceil(newRows.size() / (double)linesPerPage); //calculate total number of pages
		
		if (stoppos > linesPerPage)
		{
			stoppos = newRows.size() -1; //make sure we are stopping at a correct place.
		}
		
		if (page > 0 && page <= numberOfPages) //are the pages in range?
		{
			
			if (numberOfPages != 1)
			{
				sender.sendMessage(ChatColor.RED + "BanList - Page " + page + " of " + numberOfPages);
			}
			for (int i = startpos; i <= stoppos; i++)
			{
				sender.sendMessage(ChatColor.DARK_AQUA + newRows.get(i));
			}
		} 
		else
		{
			sender.sendMessage(ChatColor.RED + "There is not a BanList page '" + page + "'");
		}
	}
}
