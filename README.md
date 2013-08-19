imgpack
=======

**Dumb image packing tool**  
Copyright (C) 2013 Maciej Jesionowski

This program is a stand-alone, command line tool, written in Java. It allows you to pack several images into a single big image file with an associated JSON file that describes its contents. In other words, you can make a texture atlas with it, e.g. for a 2D, sprite-based game.

This software comes without any warranty and is licensed under the terms of GNU GPL. See LICENSE file for details.

### How to run

Run Eclipse IDE and import the project. Eclipse will build the project. Now run build_jar.xml ANT script from the IDE and it will create the JAR file.

To get started run:

    java -jar imgpack.jar
    
Which will give you a list of command line parameters. Example usage might be like this:

    java -jar imgpack.jar -size 512 256 -add hero.png -add baddie.png -add tile1.png -add tile2.png
    
If nothing crashes, you should get an atlas.png and atlas.json. JSON file is pretty much self-explanatory. Try it with different settings, especially -crop to get the feeling of how it affects the output. Crashes will typically happen if a file doesn't exist or if there's no room to pack all the images.
