package aji.myplaylist.Language.element;

import java.util.HashSet;
import java.util.Set;

public class LanguageNode {
    public final String key;
    public final Set<Object> elements = new HashSet<>();

    public LanguageNode(String key){
        this.key = key;
    }

    public LanguageNode then(LanguageNode key){
        return this;
    }

    public LanguageNode then(LanguageValue key){
        return this;
    }

    public String get(String[] keys){
        for (Object element : elements){
            if(element instanceof LanguageNode){
                if (((LanguageNode) element).key.equals(keys[0])){
                    String[] subKeys = new String[keys.length - 1];
                    System.arraycopy(keys, 1, subKeys, 0, subKeys.length);
                    return ((LanguageNode) element).get(subKeys);
                }
            }
            if(element instanceof LanguageValue){
                return ((LanguageValue) element).value;
            }
        }
        return "";
    }
}
