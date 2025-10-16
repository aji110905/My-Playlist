package aji.myplaylist.mixin.language;

import aji.myplaylist.MyPlaylistClient;
import net.minecraft.client.resource.language.LanguageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {
    @Inject(method = "setLanguage", at = @At("HEAD"))
    public void setLanguage(String languageCode, CallbackInfo ci) {
        MyPlaylistClient.LANGUAGE.setLanguage(languageCode);
    }
}
