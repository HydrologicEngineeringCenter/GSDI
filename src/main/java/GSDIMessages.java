import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GSDIMessages {
    public static final String Bundle_Name = GSDI_PluginI18n.BUNDLE_NAME;
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(Bundle_Name);
    public static final String Plugin_Name = "GSDIPlugin.Name";
    public static final String Plugin_Description = "GSDIPlugin.Description";
    public static final String Plugin_Short_Name = "GSDIPlugin.ShortName";
	public static final String PLUGIN_EDITOR_TITLE = "GSDIPlugin.Editor.Title";

    private GSDIMessages() {super();}
    public static String getString(String key) {
        try{
            return RESOURCE_BUNDLE.getString(key);
        }
        catch(MissingResourceException e) {
            return "!" + key + "!";
        }
    }
}
