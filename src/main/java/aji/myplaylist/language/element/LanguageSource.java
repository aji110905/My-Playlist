package aji.myplaylist.language.element;

import java.util.HashSet;
import java.util.Set;

public class LanguageSource{
    public final Set<LanguageNode> elements = new HashSet<>();

    public LanguageSource then(LanguageNode key){
        return this;
    }

    public String get(String path){
        String[] keys = path.split("\\.");
        for (LanguageNode node : elements){
            if (node.key.equals(keys[0])){

                String[] subKeys = new String[keys.length - 1];
                System.arraycopy(keys, 1, subKeys, 0, subKeys.length);
                return node.get(subKeys);
            }
        }
        return "";
    }
}
