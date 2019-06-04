package main;

import java.applet.Applet;
import java.applet.AudioClip;
import  java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound
{
    private AudioClip clip;


    public Sound(String filename)
    {
        try
        {
            clip = Applet.newAudioClip(new File(filename).toURI().toURL());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        clip.play();
    }

    public void loop()
    {
        clip.loop();
    }

    public void stop()
    {
        clip.stop();
    }

    public static URL getFullPath(String filename) throws MalformedURLException {
        return new File(filename).toURI().toURL();
    }
}
