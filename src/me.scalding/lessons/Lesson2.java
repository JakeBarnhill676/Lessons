package lessons;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Scalding (Jake)
 *
 * This lesson is all about particles. Player trail particles and Arrow particles
 */

public class Lesson2 extends JavaPlugin {

    private Map<UUID, Particle> playerTrail;
    /*
    This code creates a new Map object that contains a UUID and a Particle. The Player's UUID is linked to a particle.
    We will assign this in the onEnable.
    */

    @Override
    public void onEnable() {
        playerTrail = new HashMap<>();
        /*
        This assigns playerTrail to a new HashMap object
         */
    }

    /**
     * This method will be used to spawn a particle at a player's feet
     * @param player this is the player to spawn a particle at their feet
     * @param particle this is the particle to be spawned
     */
    private void spawnParticle(Player player, Particle particle) {
        /*
        This creates a new method used to spawn particles at a players feet. Takes a player and an effect argument. This method doesn't need to
        return anything because it is void.
         */
        Location loc = player.getLocation().clone();
        /*
        Creates a copy of the players location and makes it into a new location object
         */
        loc.getWorld().spawnParticle(particle, loc, 1);
        /*
        Plays the particle
         */
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        /*
        What this line does, is it creates a new boolean method called onCommand. With 4 different arguments to be
        passed into it. Guessing you already know this so I'll just skip
         */
        if (cmd.getName().equalsIgnoreCase("trail")) {
            /*
            Checks if the command's name (or what they type in after the /) equals (ignoring the capitalization) 'trail'
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
                if (p.hasPermission("PERMISSION.TO.TOGGLE.TRAIL")) {
                /*
                This line checks if the specified player has a certain permission. If they don't, we will send them a message later
                 */
                if(playerTrail.containsKey(p.getUniqueId())) {
                    /*
                    This checks if playerTrail map contains them, if it does the if statement will continue
                     */
                    playerTrail.remove(p.getUniqueId());
                    /*
                    This toggles the trail off and removes them from the map
                     */
                    p.sendMessage("TRAIL TOGGLED OFF MESSAGE");
                    /*
                    Message sent to the player when the trail is toggled off
                     */
                    return true;
                    /*
                    This line says that the method is done.
                    */

                } else {
                    /*
                    This code is run if they have the trail toggled off
                     */
                    playerTrail.put(p.getUniqueId(), Particle.BARRIER);
                    /*
                    This toggles the trail on and adds them to the map
                    For the Particle.blah part, you can replace with any time of particle.
                    Just type particle. and find one you like
                     */
                    p.sendMessage("TRAIL TOGGLED ON MESSAGE");
                    /*
                    Message sent to the player when the trail is toggled on
                     */

                    new BukkitRunnable() {
                        /*
                        This line of code is used to start a new timer, kinda hard to explain sorry
                         */
                        @Override
                        public void run() {
                            /*
                            This is the code run every time the timer is ready or whatever you wanna call it
                             */
                            if (!playerTrail.containsKey(p.getUniqueId())) {
                                /*
                                This code is run if they the map doesn't contain them (if they toggled it off)
                                We will be disabling the timer now
                                 */
                                cancel();
                                /*
                                This cancels the timer so no more particles will be spawned
                                 */

                            } else {
                                /*
                                This code is run when the they still have trails on
                                 */
                                spawnParticle(p, playerTrail.get(p.getUniqueId()));
                                /*
                                This is the method we create above this that is used to spawn a particle
                                 */
                            }
                        }
                    }.runTaskTimer(this, 0, 3);
                    /*
                    This says how long the timer will run/how often
                     */
                    return true;
                    /*
                    This line says that the method is done.
                    */

                }
                }  else {
                /*
                The code below this is fired when the player doesn't have the permission we specified above.
                 */
                    p.sendMessage("MESSAGE TO THE PLAYER THAT THEY DON'T HAVE PERMISSION");
                /*
                This line sends the player the specified message when they try to use /trail when they don't have permission
                 */

                    return true;
                /*
                This line says that the method is done.
                 */
                }
            }else {
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
