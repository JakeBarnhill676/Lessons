package lessons;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * @author Scalding (Jake)
 *
 * This lesson is on saving locations into seperate configs (and using seperate configs). And also how to retrieve those locations for later use
 */

public class Lesson1 extends JavaPlugin {

    private File dataFile;
    /*
    What this line of code does, is it creates a new 'File' object called 'dataFile' we will assign this to something in the onEnable.
    It has the private modifier because it is not needed in other classes, and it's better/safer to just keep it private.
     */

    private FileConfiguration data;
    /*
    What this line of code does, is it creates a new 'FileConfiguration' object called 'data'. Similarly this has not been assigned either.
    This has the same methods as 'getConfig()' except it is just your custom config. Same reason for private as the above code.
     */

    @Override
    public void onEnable() {
        dataFile = new File(this.getDataFolder(), "data.yml");
        /*
        What this line of code does, is assigns' dataFile' to a new File object. The file has not yet been created, so the below code creates it.
        getDataFolder() is referring to the DataFolder created by this plugin, inside of the plugins folder. For example, if the plugin was called "TestPlugin",
        this folder would be called TestPlugin, inside of the plugins folder. Now please note, if the folder does not exist (using saveDefaultConfig() or saveConfig() creates this folder) it will cause errors,
        I will show you how to create it after (create the folder) "data.ymL" refers to the file's name, and then with the extension.
         */
        if (!dataFile.exists()) {
            /*
            This line checks if the file DOESN'T exist. The if statement will continue if it doesn't exist
             */
            try {
                /*
                This line 'initiates' a try block. What this does, is it tries to do the code inside, and if it can't it will throw an error, and you can
                do many things with this error. I decided just to print the stacktrace of the error (Most likely this error will never happen, but some places where
                it might happen are if the folder has a password, invalid folder, or some other errors like that)
                 */
                dataFile.createNewFile();
                /*
                This line creates the file in the specified folder. This is the code that can throw the error, but it is being processed by the try block.
                 */
            } catch (IOException e) {
                /*
                This line 'catches' the error from the try block. IE if the try block fails, this will happen. 'e' represents the error that is being thrown,
                I can do many things with this error (The reason it is an IOException, is because that is the only error that can possible be thrown) (Files are
                handled by java.io, so that's why it is called IOException)
                 */
                e.printStackTrace();
                /*
                This line just simply prints out the stacktrace and gives a detailed explanation of where the error is (semi detailed xD)
                 */
            }
            data = YamlConfiguration.loadConfiguration(dataFile);
            /*
            This line sets the 'data' FileConfiguration to the FileConfiguration of dataFile. YamlConfiguration.loadConfiguration(FILE) returns the FileConfiguration of the
            specified file.
             */
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /*
        What this line does, is it creates a new boolean method called onCommand. With 4 different arguments to be
        passed into it. Guessing you already know this so I'll just skip
         */
        if (cmd.getName().equalsIgnoreCase("setspawn")) {
            /*
            Checks if the command's name (or what they type in after the /) equals (ignoring the capitalization) 'setspawn'
             */
            if (sender instanceof Player) {
            /*
            This checks if the sender of the command is a player. Obviously console can't use this command so if it is console sending it
            we will send them a message later
             */
                Player p = (Player) sender;
            /*
            This creates a new player object from the sender. With the previous knowledge (from the above if statement) we know with 100% certainty, that the sender
            IS a player. So it is  safe to cast it to a player. Casting is basically saying that sender is a player, and forcing it to be a player. If it is not a player
            and you try to cast it, you will get 'ClassCastExceptions' or something like that xD
             */
                if (p.hasPermission("PERMISSION.TO.SET.SPAWN.HERE")) {
                /*
                This line checks if the specified player has a certain permission. If they don't, we will send them a message later
                 */
                    data.set("world", p.getWorld().toString());
                /*
                This line sets 'world' in the config to the player's current world
                 */

                    data.set("x", p.getLocation().getX());
                /*
                This line sets 'x' in the config to the player's current x coordinate
                 */

                    data.set("y", p.getLocation().getY());
                /*
                This line sets 'world' in the config to the player's current y coordinate
                 */

                    data.set("z", p.getLocation().getZ());
                /*
                This line sets 'world' in the config to the player's current z coordinate
                 */

                    try {
                    /*
                    Same try block as I explained before so you should under stand it xD
                     */
                        data.save(dataFile);
                    /*
                     This line saves the file with the config inside of it
                    */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    p.sendMessage("MESSAGE TO THE PLAYER THAT THEY SET A WARP");
                /*
                This line sends the player the specified message when they set a warp
                 */

                    return true;
                /*
                This line says that the method is done.
                 */
                } else {
                /*
                The code below this is fired when the player doesn't have the permission we specified above.
                 */
                    p.sendMessage("MESSAGE TO THE PLAYER THAT THEY DON'T HAVE PERMISSION");
                /*
                This line sends the player the specified message when they try to use /setspawn when they don't have permission
                 */

                    return true;
                /*
                This line says that the method is done.
                 */
                }
            } else {
                /*
                The code below is fired when a 'thing' that is not a player tries to run this command
                */
                sender.sendMessage("MESSAGE TO THE SENDER THAT ONLY PLAYERS CAN USE THIS COMMAND");
                /*
                This sends a message to the sender who is not a player. The reason we send it to 'sender' is because
                'p' is a player, but in this case sender is not a player, so we can't use 'p'
                */
                return true;
                /*
                This line says that the method is done.
                 */
            }
        } else if (cmd.getName().equalsIgnoreCase("spawn")) {
            /*
            Checks if the command's name (or what they type in after the /) equals (ignoring the capitalization) 'spawn'
             */
            if (sender instanceof Player) {
            /*
            This checks if the sender of the command is a player. Obviously console can't use this command so if it is console sending it
            we will send them a message later
             */
                Player p = (Player) sender;
            /*
            This creates a new player object from the sender. With the previous knowledge (from the above if statement) we know with 100% certainty, that the sender
            IS a player. So it is  safe to cast it to a player. Casting is basically saying that sender is a player, and forcing it to be a player. If it is not a player
            and you try to cast it, you will get 'ClassCastExceptions' or something like that xD
             */
                if (p.hasPermission("PERMISSION.TO.GO.TO.SPAWN")) {
                /*
                This line checks if the specified player has a certain permission. If they don't, we will send them a message later
                 */

                    if (!data.contains("x")) {
                    /*
                    This line checks if the config is empty. And if it is empty that means the config hasn't been sent, so we are gonna send them a message
                     */
                        p.sendMessage("MESSAGE THAT THE SPAWN WASN'T SET");
                    /*
                    This line sends the player the specified message when they try to go to spawn, but spawn wasn't set
                    */

                        return true;
                     /*
                     This line says that the method is done.
                      */
                    } else {
                    /*
                    The code below is fired when the spawn IS set
                     */
                        p.teleport(new Location(Bukkit.getWorld(data.getString("world")), data.getDouble("y"), data.getDouble("z"), data.getDouble("x")));
                    /*
                    This code teleports them to the location set in the config, IE the spawn location
                     */
                        p.sendMessage("MESSAGE THAT THEY TELEPORTED TO SPAWN");
                    /*
                    This line sends the player the specified message when they teleport to spawn
                    */

                        return true;
                     /*
                     This line says that the method is done.
                      */
                    }

                } else {
                /*
                The code below this is fired when the player doesn't have the permission we specified above.
                 */
                    p.sendMessage("MESSAGE TO THE PLAYER THAT THEY DON'T HAVE PERMISSION");
                /*
                This line sends the player the specified message when they try to use /spawn when they don't have permission
                 */

                    return true;
                /*
                This line says that the method is done.
                 */
                }
            } else {
                /*
                The code below is fired when a 'thing' that is not a player tries to run this command
                */
                sender.sendMessage("MESSAGE TO THE SENDER THAT ONLY PLAYERS CAN USE THIS COMMAND");
                /*
                This sends a message to the sender who is not a player. The reason we send it to 'sender' is because
                'p' is a player, but in this case sender is not a player, so we can't use 'p'
                */
                return true;
                /*
                This line says that the method is done.
                 */
            }
        }
        return true;
     /*
     This line says that the method is done.
     */
    }

}
