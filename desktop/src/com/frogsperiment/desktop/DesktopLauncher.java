package com.frogsperiment.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.frogsperiment.FrogsperimentGame;
import com.frogsperiment.util.Constants;

public class DesktopLauncher {

    private static boolean rebuildAtlas = false;
    private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {

        if (rebuildAtlas) {
            Settings settings = new Settings();
            settings.maxWidth = 1024;
            settings.maxHeight = 1024;
            settings.duplicatePadding = true;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "android/assets-raw/images",
                    "android/assets/images", "worldtextures");
        }

		LwjglApplicationConfiguration config =
                new LwjglApplicationConfiguration();

        config.title = "Frogsperiment Development Build v0.1d";
        config.width = Constants.DESKTOP_WIDTH;
        config.height = Constants.DESKTOP_HEIGHT;

		new LwjglApplication(new FrogsperimentGame(), config);
	}
}
