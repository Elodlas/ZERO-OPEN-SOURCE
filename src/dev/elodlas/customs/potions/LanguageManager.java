package dev.elodlas.customs.potions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LanguageManager {
    private String language; //The language pack name we're using
    private int defaultValue = 0; //The way to displaying Strings that don't exist
    private File location; //The location of where the language packs are stored
    private HashMap<String, String> languages; //The HashMap storing the language information

    //Final variables to use for the defaultValue argument
    public static final int DISPLAY_LANGUAGE_ROUTE = 0; //If you can can't find the key 'test' for example it will return the String 'test'
    public static final int DISPLAY_TEXT_NOT_FOUND_ERROR = 1; //If you can't find the key 'test' for example it will return the String 'TEXT NOT FOUND WITH KEY: test'
    public static final int THROW_EXCEPTION = 2; //If you can't find the key 'test' for example it will throw a LanuageKeyNotFoundException.

    public LanguageManager(String language, int defaultValue, File location) {
        this.language = language;
        this.defaultValue = defaultValue;
        this.location = location;
        if(!location.exists()){
            location.mkdir();
        }
        languages = new HashMap<String, String>();
        read();
    }

    public LanguageManager(String language, int defaultValue) {
        this(language, defaultValue, new File("language packs"));
    }

    public LanguageManager(String language) {
        this(language, 0, new File("language packs"));
    }

    public LanguageManager(String language, File location) {
        this(language, 0, location);
    }

    public LanguageManager(File location) {
        this("EN_UK", 0, location);
    }

    private void read() {
        if(location.isDirectory()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(location, language + ".lang")));
                String line;
                while ((line = br.readLine()) != null) {
                    if(line.contains("=")){
                        String[] array = line.split("=");
                        if(array.length == 2){
                            languages.put(array[0].trim(), array[1].trim());
                        }
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public String getLanguage() {
        return language;
    }

    public File getLocation() {
        return location;
    }

    public void setDefaultValue(int defaultValue) {
        if(defaultValue < 0 || defaultValue > 2){
            throw new IllegalArgumentException();
        }
        this.defaultValue = defaultValue;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLocation(File location) {
        this.location = location;
    }

    public void addLanguageValue(String key, String value){
        languages.put(key, value);
    }

    public void removeLanguageValue(String key){
        languages.remove(key);
    }

    public String get(String key){
        switch(defaultValue){
            case DISPLAY_LANGUAGE_ROUTE: return get(key, key);
            case DISPLAY_TEXT_NOT_FOUND_ERROR: return get(key, "TEXT NOT FOUND WITH KEY: " + key);
            case THROW_EXCEPTION: throw new LanuageKeyNotFoundException(key);
            default: return get(key, ""); //This should never happen
        }
    }

    public String get(String key, String defaultValue){
        if(languages.containsKey(key)){
            return languages.get(key);
        }else{
            return defaultValue;
        }
    }

}