package com.gmail.bwinkl04;

import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
	private BanList plugin;
	private FileConfiguration config;
	public static int linesPerPage;
	public static String filePath;
	public Config(BanList plugin)
	{
		this.plugin = plugin;
	}
	
	public void load()
	{
		plugin.reloadConfig();
		config = plugin.getConfig();
		
		config.addDefault("lines_per_page", 15);
		config.addDefault("ban_csv_file_path", "plugins/CommandBook/bans.csv");
		
		config.options().copyDefaults(true);
		plugin.saveConfig();
		
		linesPerPage = config.getInt("lines_per_page");
		filePath = config.getString("ban_csv_file_path");
	}
}
