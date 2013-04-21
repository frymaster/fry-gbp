fry-gbp
=======

Permissions plugin for bukkit focussed around defining group membership as the core activity.

The basic idea is that to add a user *foo* to group *bar*, instead of going to users.yml and adding

```yaml
foo:
  - groups:
    - bar
```

...you'd simply go to the bar.txt file and add "foo" to the end of it, just like you'd do with ops.txt or white-list.txt etc.

Why might this be useful? Because it's a lot easier to integrate with external scripts if they have to simply output a list of names rather than generate a YAML structure.  This important if you want to automatically assign group memberships based on your forum permissions, for example.

Features
--------

* Flatfile group membership lists that work like the built-in ops.txt file
* Assign permissions and negative permissions to groups
* A group can inherit permissions from other groups (which can have their own parent etc.) - these will be overridden by the explicitly set permissions
* Meta-groups called *all* and *ops* which represent everyone and ops, funnily enough
* Can define "default" groups which people will be added to if and only if they have no explicit group memberships
* Group priorities to determine what permissions take priority if someone is a member of many groups
* Still features a conventional users.yml file where you can override group memberships (both add and negate) and permission nodes

What the plugin doesn't do
--------------------------

This plugin is in early stages of development.  There are several standard features it doesn't yet support

* In game commands - the only way to alter the configuration is to edit the files directly.  The group membership testfiles are read every time a player joins, but you will have to /reload if you make changes to the .yml files
* Per world permissions - global only right now
* Vault API integration
* Time-limited permissions

Future Features
---------------

Ultimately the aim of this plugin is to read, and hopefully write, from arbitrary mySQL databases.  This would mean it could, for example, query your forum **directly** to determine group membership.  Optionally you would be able to write memberships as well, and in the far future perhaps store most config items in a database as well, for distributed setups.

Configuration
-------------

The groups.yml and users.yml files should be fairly self-explanatory.  If not, you should let me know what's confusing and I'll add more documentation

MCStats
-------

The plugin now includes stats tracking courtesy of [MCStats](http://mcstats.org/).  You can opt out of statistics on the plugin being sent to this service by editing the plugins/PluginMetrics/config.yml file.

IRC
-----

I idle in #bukkit and in my personal channel #frymaster on Espernet.  I am in the UK and are unlikely to respond immediately to queries except in the evening, UK time, but I log all messages and I **will** get back to you eventually.

BukkitDev
---------
http://dev.bukkit.org/server-mods/fry-gbp/
