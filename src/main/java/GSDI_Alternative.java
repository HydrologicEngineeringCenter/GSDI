/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.rma.io.RmaFile;
import hec.SqliteDatabase;
import hec.ensemble.EnsembleTimeSeries;
import hec.ensemble.stats.*;
import hec.metrics.MetricCollectionTimeSeries;
import hec.model.OutputVariable;
import hec.ensemble.stats.*;
import hec2.model.DataLocation;
import hec2.plugin.model.ComputeOptions;
import hec2.plugin.selfcontained.SelfContainedPluginAlt;
import org.jdom.Document;
import org.jdom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WatPowerUser
 */
public class GSDI_Alternative extends SelfContainedPluginAlt{
    //region Fields
    private String _pluginVersion;
    List<DataLocation> _inputDataLocations;
    List<DataLocation> _outputDataLocations ;
    String _timeStep;
    private static final String DocumentRoot = "GSDI_Alternative";
    private static final String OutputVariableElement = "OutputVariables";
    private static final String AlternativeNameAttribute = "Name";
    private static final String AlternativeDescriptionAttribute = "Desc";
    private static final String OutputDataLocationParentElement = "OutputDataLocations";
    private static final String AlternativeFilenameAttribute = "AlternativeFilename";
    private static final String OutputDataLocationsChildElement = "OutputDataLocation";
    private static final String DatabaseName = "ensembles.db";
    private ComputeOptions _computeOptions;
    private List<OutputVariable> _outputVariables;
    //endregion
    //region Constructors
    public GSDI_Alternative(){
        super();
    }
    public GSDI_Alternative(String name){
        this();
        setName(name);
    }
    //endregion
    //region Getters and Setters
    @Override
    public int getModelCount() {
        return 1;
    }
    public List<DataLocation> getInputDataLocations(){
        //construct input data locations.
        if(_inputDataLocations==null ||_inputDataLocations.isEmpty()){
            _inputDataLocations = defaultInputDataLocations();
        }
        return _inputDataLocations;
    }
    public List<DataLocation> getOutputDataLocations(){
        //construct input data locations.
        if(_outputDataLocations== null || _outputDataLocations.isEmpty()){
            _outputDataLocations = defaultOutputDataLocations();
        }
        List<DataLocation> outputAsDataLoc = new ArrayList<DataLocation>();
        for(DataLocation dl : _outputDataLocations){
            outputAsDataLoc.add(dl);
        }
        return outputAsDataLoc;
    }
    public List<OutputVariable> getOutputVariables(){
        return _outputVariables;
    }
    @Override
    public String getLogFile() {
        return null;
    }
    public void setComputeOptions(ComputeOptions opts){
        _computeOptions = opts;
    }
    public boolean hasOutputVariables(){
        return false;
        /*
        if (_outputVariables == null || _outputVariables.size() == 0){
            return false;
        }
        return true;
        */
    }
    //endregion
    //region Ignored Boilerplate
    @Override
    public boolean isComputable() {
        return true;
    }
    boolean computeOutputVariables(List<OutputVariable> list) { return true; }
    @Override
    public boolean cancelCompute() {
        return false;
    }
    //endregion
    @Override
    public boolean compute() {
        //this is where the magic happens.
        return true;
    }

    @Override
    public boolean saveData(RmaFile file){
        //SAve data does not work because the override toXML on the unique Data locations is not working. They end up being serialized like a typical data location, leaving out data.
        //saving has been disabled until a solution is found. There is no UI element to modify the alternative yet anyway. Once that exists, the rest will need to follow.
//        if(file!=null){
//            Element root = new Element(DocumentRoot);
//            root.setAttribute(AlternativeNameAttribute,getName());
//            root.setAttribute(AlternativeDescriptionAttribute,getDescription());
//            root.setAttribute(AlternativeFilenameAttribute,file.getAbsolutePath());
//            if(_inputDataLocations!=null) {
//                saveDataLocations(root, _inputDataLocations);}
//            if(_outputDataLocations!=null) {
//                saveOutputDataLocations(root, _outputDataLocations);}
//            Document doc = new Document(root);
//            return writeXMLFile(doc,file);
//        }
//        return false;
        System.out.println("Saving the GSDI Alternative is unsupported at this time. ");
        return true;
    }

    @Override
    protected void loadOutputDataLocations(Element root, List<DataLocation> outputDataLocations) {
        Element OutputDataLocationsEle = root.getChild(OutputDataLocationParentElement);
        for ( Object child: OutputDataLocationsEle.getChildren() ){
            Element outputEle = (Element)child;
            DataLocation dl = new DataLocation();
            dl.fromXML(outputEle);
            outputDataLocations.add(dl);
        }
    }

    @Override
    protected boolean loadDocument(org.jdom.Document dcmnt) {
        if(dcmnt!=null){
            org.jdom.Element ele = dcmnt.getRootElement();
            if(ele==null){
                System.out.println("No root element on the provided XML document.");
                return false;
            }
            if(ele.getName().equals(DocumentRoot)){
                setName(ele.getAttributeValue(AlternativeNameAttribute));
                setDescription(ele.getAttributeValue(AlternativeDescriptionAttribute));
                String val = ele.getAttributeValue(AlternativeFilenameAttribute);
                RmaFile file = new RmaFile(val);
                setFile(file);
            }else{
                System.out.println("XML document root was imporoperly named.");
                return false;
            }
            if(_inputDataLocations ==null){
                _inputDataLocations = new ArrayList<>();
            }
            _inputDataLocations.clear();
            loadDataLocations(ele, _inputDataLocations);

            if(_outputDataLocations ==null){
                _outputDataLocations = new ArrayList<>();
            }
            _outputDataLocations.clear();
            loadOutputDataLocations(ele, _outputDataLocations);

            setModified(false);
            return true;
        }else{
            System.out.println("XML document was null.");
            return false;
        }
    }

//These guys are just here for testing. We wouldn't really want default input and output data locations
    public List<DataLocation> defaultInputDataLocations() {
        List<DataLocation> dlList = new ArrayList<>();
        //create datalocations for each location of interest, so that it can be linked to output from other models.
        DataLocation Inflow = new DataLocation(this.getModelAlt(),"ADOC","FLOW");
        dlList.add(Inflow);
        return dlList;
    }
    public List<DataLocation> defaultOutputDataLocations() {
        List<DataLocation> dlList = new ArrayList<>();
        return dlList;
    }

    public void setInputDataLocations(List<DataLocation> locs) {
        _inputDataLocations.clear();
        _inputDataLocations.addAll(locs);
    }

    public void setOutputDataLocations(List<DataLocation> locs) {
        _outputDataLocations.clear();
        _outputDataLocations.addAll(locs);
    }

}
