/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.rma.client.Browser;
import com.rma.factories.NewObjectFactory;
import hec.model.OutputVariable;
import hec2.map.GraphicElement;
import hec2.model.DataLocation;
import hec2.model.ProgramOrderItem;
import hec2.plugin.action.EditAction;
import hec2.plugin.action.OutputElement;
import hec2.plugin.action.PluginAction;
import hec2.plugin.lang.ModelLinkingException;
import hec2.plugin.lang.OutputException;
import hec2.plugin.model.ModelAlternative;
import hec2.wat.model.tracking.OutputPlugin;
import hec2.wat.plugin.AbstractSelfContainedWatPlugin;
import hec2.wat.plugin.CreatableWatPlugin;
import hec2.wat.plugin.WatPluginManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WatPowerUser
 */
public class GSDI_Plugin extends AbstractSelfContainedWatPlugin<GSDI_Alternative> implements CreatableWatPlugin, OutputPlugin  {
    public static final String PluginName = "GSDI";
    private static final String _pluginVersion = "0.0.1";
    private static final String _pluginSubDirectory = "GSDI";
    private static final String _pluginExtension = ".GSDI";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GSDI_Plugin p = new GSDI_Plugin();
    }
    public GSDI_Plugin(){
        super();
        setName(PluginName);
        setProgramOrderItem(new ProgramOrderItem(PluginName,
                "A plugin to provide gridded stochastic data for WAT",
                false,1,"GSDI","Images/fda/wsp.png"));
        WatPluginManager.register(this);
    }
    @Override
    protected String getAltFileExtension() {
        return _pluginExtension;
    }
    @Override
    public String getPluginDirectory() {
        return _pluginSubDirectory;
    }
    @Override
    public String getVersion() {
        return _pluginVersion;
    }
    @Override
    public boolean saveProject() {
        boolean success = true;
        for(FIRO_WFP_Alternative alt: _altList){
            if(!alt.saveData()){
                success = false;
                System.out.println("Alternative " + alt.getName() + " could not save");
            }
        }
        return success;
    }

    @Override
    protected GSDI_Alternative newAlternative(String string) {
        return new GSDI_Alternative(string);
    }
    @Override
    protected NewObjectFactory getAltObjectFactory() {
        return new GSDI_AlternativeFactory(this);
    }
    @Override
    public List<DataLocation> getDataLocations(ModelAlternative ma, int i) {
        FIRO_WFP_Alternative alt = getAlt(ma);
        if(alt==null)return null;
        if(DataLocation.INPUT_LOCATIONS == i){
            //input
            return alt.getInputDataLocations();
        }else{
            //ouput
            return alt.getOutputDataLocations();
        }
    }
    @Override
    public boolean setDataLocations(ModelAlternative ma, List<DataLocation> list) throws ModelLinkingException {
        return true;
    }
    @Override
    public boolean compute(ModelAlternative ma) {
        FIRO_WFP_Alternative alt = getAlt(ma);
        if (alt != null) {
            hec2.wat.model.ComputeOptions watComputeOptions = (hec2.wat.model.ComputeOptions)ma.getComputeOptions();
            alt.setComputeOptions(watComputeOptions);
            return alt.compute();
        }
        return false;
    }
    @Override
    public void editAlternative(GSDI_Alternative gsdi_alternative) {
        if (gsdi_alternative == null )
        {
            return;
        }
    }
    @Override
    public boolean displayApplication() {
        return false;
    }
    @Override
    public List<GraphicElement> getGraphicElements(ModelAlternative ma) {
        return new ArrayList<>();
    }
    @Override
    public List<OutputElement> getOutputReports(ModelAlternative ma) {
        return new ArrayList<>();
    }
    @Override
    public boolean displayEditor(GraphicElement ge) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public boolean displayOutput(OutputElement oe, List<ModelAlternative> list) throws OutputException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public List<EditAction> getEditActions(ModelAlternative ma) { throw new UnsupportedOperationException("Not using this.");}
    @Override
    public void editAction(String string, ModelAlternative ma) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<OutputVariable> getAvailOutputVariables(ModelAlternative ma) {
        List<OutputVariable> ret = new ArrayList<>();
        GSDI_Alternative alt = getAlt(ma);
        return alt.getOutputVariables();
    }
    @Override
    public boolean computeOutputVariables(List<OutputVariable> list, ModelAlternative ma) {
        GSDI_Alternative alt = getAlt(ma);
        return alt.computeOutputVariables(list);
    }

    @Override
    public boolean hasOutputVariables(ModelAlternative ma) {
        GSDI_Alternative alt = getAlt(ma);
                Boolean hazvars = alt.hasOutputVariables();
                return hazvars;
    }

}
