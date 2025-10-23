package aji.myplaylist.client.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.util.List;

public class ParsedConfig {
    private final String defaultValue;
    private final String name;
    private final List<String> optionalValues;
    private String value;

    public ParsedConfig(String name, List<String> optionalValues, String defaultValue) {
        this.defaultValue = defaultValue;
        this.name = name;
        this.optionalValues = optionalValues;
        this.value = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void toDefault(){
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (!optionalValues.contains(value)) throw new IllegalArgumentException("Invalid value");
        this.value = value;
    }

    public SimpleOption<String> toSimpleOption(){
        return new SimpleOption<String>(
                "myplaylist.options." + name,
                SimpleOption.emptyTooltip(),
                (text, value) -> Text.literal("myplaylist.options." + name + "." + value),
                new SimpleOption.PotentialValuesBasedCallbacks<>(
                        optionalValues,
                        Codec.STRING
                ),
                getValue(),
                this::setValue
        );
    }
}
