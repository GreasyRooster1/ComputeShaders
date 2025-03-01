package main.Resources;

import java.io.File;
import java.util.HashMap;

public class ResourceLoader {
    private final String resourceLocation;
    private HashMap<String, Resource> resources = new HashMap<>();

    public ResourceLoader(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void loadAllResources(){
        loadResources("");
    }

    public void loadResources(String stub){
        File dir = new File(resourceLocation+"\\"+stub);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    if(stub.isEmpty()){
                        loadResources(child.getName());
                    }else {
                        loadResources(stub + "\\" + child.getName());
                    }
                }else{
                    String name = stub+"/"+child.getName();
                    resources.put(name.replace("\\","/"),parseResource(resourceLocation+"\\"+stub+"\\"+child.getName()));
                }
            }
        }
    }

    public Resource parseResource(String path){
        String type = path.substring(path.lastIndexOf(".")+1);
        if(type.equals("png")||type.equals("jpg")||type.equals("jpeg")){
            return new TextureResource(path);
        }
        if(type.equals("obj")){
            return new MeshResource(path);
        }
        return null;
    }

    public Resource getRawResource(String identifier){
        return resources.get(identifier);
    }

    public Object getResource(String identifier){
        return resources.get(identifier).get();
    }
}
