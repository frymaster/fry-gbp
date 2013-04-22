package org._127001.frymaster.gbp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FryGroup implements Comparable<FryGroup> {

    private int priority = 0;
    private String name = null;
    private Map<String, Boolean> permissions;
    private boolean defaultGroup = false;
    private List<String> inheritList = null;
    private String fileName = null;
    private File file = null;
    private Plugin plugin;

    /**
     * @return the list of groups this group inherits permissions from, if any
     */
    public List<String> inheritList() {
        return inheritList;
    }

    /**
     * @return the file used to look up group membership
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param file the file used to look up group membership
     */
    public void setFileName(String fileName) {
        this.file = null;
        if (fileName != null && isMetaGroup()) {
            plugin.getLogger().log(Level.WARNING, "Filename specified for group {0}- ignoring", getName());
            this.fileName = null;
        } else if (fileName == null && !isMetaGroup()) {
            plugin.getLogger().log(Level.INFO, "No file specified for group {0}", getName());
            if (!isDefaultGroup()) {
                plugin.getLogger().log(Level.WARNING, "Group {0} can never have any members", getName());
            }
        } else {
            this.fileName = fileName;
        }
    }

    public File getFile() {
        if (file != null) {
            return file;
        }
        if (getFileName() == null) {
            return null;
        }
        File newFile = new File(getFileName());
        // Unless the filename is fully-qualified, use one relative to the plugin's directory
        if (!newFile.isAbsolute()) {
            newFile = new File(plugin.getDataFolder(), getFileName());
        }
        // if it's an existing file we don't have to create it
        if (newFile.isFile()) {
            //but warn if it can't be written to
            if (!newFile.canWrite()) {
                plugin.getLogger().log(Level.WARNING, "Group {0} file {1} can not be written to", new Object[]{getName(), newFile.getAbsolutePath()});
            }
        } else if (newFile.exists()) {
            //exists but isn't a file - presumably a directory or similar
            plugin.getLogger().log(Level.SEVERE, "Group {0} file {1} is not a valid filename", new Object[]{getName(), newFile.getAbsolutePath()});
            return null;
        } else {
            //needs created
            plugin.getLogger().log(Level.INFO, "Group {0} members file does not exist, creating", getName());
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File {0} could not be created", newFile.getAbsolutePath());
                plugin.getLogger().severe(e.toString());
                return null;
            }
        }
        file = newFile;
        return file;
    }

    /**
     * @return the defaultGroup
     */
    public boolean isDefaultGroup() {
        return defaultGroup;
    }

    /**
     * @param defaultGroup the defaultGroup to set
     */
    public void setGroupIsDefault(boolean defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the permissions
     */
    public Map<String, Boolean> permissions() {
        return permissions;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(FryGroup arg0) {
        return Integer.compare(this.getPriority(), arg0.getPriority());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FryGroup other = (FryGroup) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public FryGroup(Plugin plugin,String name, int priority, List<String> inherit, String file, boolean isDefault) {
        super();
        this.plugin = plugin;
        this.setPriority(priority);
        this.setName(name);
        this.inheritList = inherit;
        this.setGroupIsDefault(isDefault);
        this.setFileName(file);

        permissions = new HashMap<String, Boolean>();
        this.getFile();	// To show filename errors now rather than on first use;
    }

    @Override
    public String toString() {
        return getName() + ":" + permissions().toString();
    }

    public boolean isMetaGroup() {
        return (getName().equals("ops") || getName().equals("all"));
    }

    public boolean isMember(Player player) {
        if (getName().equals("all")) {
            return true;
        }
        if (getName().equals("ops")) {
            return player.isOp();
        }
        File f = this.getFile();
        if (f != null) {
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(f));
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    if (player.getName().equalsIgnoreCase(currentLine)) {
                        br.close();
                        return true;
                    }
                }
                br.close();
            } catch (FileNotFoundException e) {
                plugin.getLogger().log(Level.WARNING, "Group {0} File not found trying to read group membership in file {1}", new Object[]{this.getName(), f.getAbsolutePath()});
                return false;
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Group {0} IOException to read group membership in file {1}", new Object[]{this.getName(), f.getAbsolutePath()});
                plugin.getLogger().severe(e.toString());
            }
        }
        return false;
    }
}